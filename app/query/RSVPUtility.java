package query;

import core.twilio.SMSMessageManager;
import dbaccess.dbojbects.dao.NgoMeetingDao;
import dbaccess.dbojbects.dao.NgoRSVPDao;
import dbaccess.dbojbects.pojo.NgoMeeting;
import dbaccess.dbojbects.pojo.NgoRSVP;
import org.apache.log4j.Logger;
import play.mvc.Http;

import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by nhosgur on 1/6/15.
 */
public class RSVPUtility {
    private static final Logger itsLogger = Logger.getLogger(RSVPUtility.class);

    public static String rsvp(final Http.Request request) throws ParseException {
        NgoMeetingDao meetingDao = DBIHandlerManager.getMeetingDAO();
        NgoRSVPDao rsvDao = DBIHandlerManager.getRSVDAO();
        return rsvp(request, meetingDao, rsvDao);
    }

    public static String rsvp(final Http.Request request, final NgoMeetingDao dbi, final NgoRSVPDao rsvDao) throws ParseException {
        Map<String, String[]> qs = request.queryString();

        String body = qs.get("Body")[0];

        String[] splitted = body.split(":");
        if (splitted.length != 3)
            return "Make sure to have Meeting ID ,and Your RSVP response. Keep in mind meeting id is case sensitive";

        String meetingId = splitted[1];
        NgoMeeting meeting = dbi.getById(meetingId.trim());
        if (null == meeting)
            return "Meeting with ID: [" + meetingId + "] does not exist.. Keep in mind that meeting id is case sensitive";


        StringBuilder sb = new StringBuilder();
        NgoRSVP rsvp = new NgoRSVP();
        String rsvpNumber = qs.get("From")[0];
        rsvp.setRsvnumber(rsvpNumber);
        try {
            rsvp.setRsvresponse(splitted[2]);
            rsvp.setMeetingid(meetingId);
            DBIHandlerManager.getRSVDAO().create(rsvp);
            SMSMessageManager.handleAck(rsvp,meeting);
        } catch (Throwable e) {
            itsLogger.error(e.getMessage(), e);
            return sb.append("Failed to create the rsvp").toString();
        }

        return sb.append("Registered your RSVP answer of ["+rsvp.getRsvresponse()+"] for meeting ID: [" + rsvp.getMeetingid() + "]").toString();
    }


    public static String lrsvp(Http.Request request) {
        Map<String, String[]> qs = request.queryString();

        String body = qs.get("Body")[0];

        String[] splitted = body.split(":");
        if (splitted.length != 2)
            return "Make sure to have Meeting ID";

        String meetingId = splitted[1];
        return fetchTheRSVPs(meetingId);
    }

    public static String fetchTheRSVPs(final String meetingId){
        NgoMeetingDao dbi = DBIHandlerManager.getMeetingDAO();
        NgoMeeting meeting = dbi.getById(meetingId.trim());
        if (null == meeting) {
            return "Meeting with ID: [" + meetingId + "] does not exist. Keep in mind that meeting ID is case sensitive.";
        }
        StringBuilder sb = new StringBuilder();
        NgoRSVP rsvp = new NgoRSVP();
        try {
            Iterator<NgoRSVP> it = DBIHandlerManager.getRSVDAO().getByMeetingID(meeting.getMeetingid());
            if(null==it||!it.hasNext()){
                return sb.append("No RSVPs yet for meeting ID: ["+meeting.getMeetingid()+"]").toString();
            }
            sb.append("RSVPs for the meeting ID: [").append(meeting.getMeetingid()).append("]");
            while(it.hasNext()){
                NgoRSVP res = it.next();
                sb.append("[").append(res.getRsvnumber()).append(":").append(res.getRsvresponse()).append("]");
            }
            return sb.toString();
        } catch (Throwable e) {
            itsLogger.error(e.getMessage(), e);
            return sb.append("Failed to fetch the RSVPs for meeting ID: [").append(meetingId).append("]").toString();
        }


    }
}
