package query;

import core.twilio.SMSMessageManager;
import dbaccess.dbojbects.dao.NgoMeetingDao;
import dbaccess.dbojbects.pojo.NgoMeeting;
import org.apache.log4j.Logger;
import play.mvc.Http;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by nhosgur on 1/6/15.
 */
public class MeetingSchedulerUtility {
    private static final Logger itsLogger = Logger.getLogger(MeetingSchedulerUtility.class);
    private static volatile int MEETING_ID_LENGTH=2;
    private static volatile int MEETING_ID_LENGTH_OVER=50*25;

    private static synchronized String genUniqueMeetingIDSynch(final NgoMeetingDao meetingDao) {
        int i = 0;
        String meetingId = RandomGenCodeGenerator.generateItemCode(MEETING_ID_LENGTH);
        while (null != meetingDao.getById(meetingId) && i++ < MEETING_ID_LENGTH_OVER) {
            meetingId = RandomGenCodeGenerator.generateItemCode(MEETING_ID_LENGTH);
        }
        if (null != meetingId) return meetingId;

        MEETING_ID_LENGTH++;
        MEETING_ID_LENGTH_OVER <<= 1;
        itsLogger.error("We run out of digits on Meeting IDs so will increase the size to be ["+MEETING_ID_LENGTH+"] and will try max of["+MEETING_ID_LENGTH_OVER);

        throw new RuntimeException("Failed to generate a unique ID try again please");
    }

    private static String genUniqueMeetingID(final NgoMeetingDao meetingDao) {
        String meetingId = RandomGenCodeGenerator.generateItemCode(MEETING_ID_LENGTH);

        int i = 0;
        while (null != meetingDao.getById(meetingId) && i++ < 100) {
            meetingId = RandomGenCodeGenerator.generateItemCode(MEETING_ID_LENGTH);
        }
        if (null != meetingId) return meetingId;

        while (null != meetingDao.getById(meetingId) && i++ < MEETING_ID_LENGTH_OVER) {
            meetingId = RandomGenCodeGenerator.generateItemCode(MEETING_ID_LENGTH);
        }
        if (null != meetingId) return meetingId;

        return genUniqueMeetingIDSynch(meetingDao);
    }

    public static String schedule(final Http.Request request) throws ParseException {
        NgoMeetingDao dbi = DBIHandlerManager.getMeetingDAO();
        return schedule(request,dbi);
    }
    public static String schedule(final Http.Request request,final NgoMeetingDao dbi) throws ParseException {
        Map<String, String[]> qs = request.queryString();


        String body = qs.get("Body")[0].toLowerCase();

        String[] splitted = body.split(":");
        if (splitted.length != 3 && splitted.length != 4)
            return "Make sure to have District name,time and optionally a note";
        String createdBy = qs.get("From")[0];
        String district = splitted[1];
        String timeInString = splitted[2];
        String note = splitted.length>3?splitted[3]:null;

        return createTheMeeting(dbi,district,timeInString,note,createdBy);
    }

    public static String createTheMeeting(final NgoMeeting meeting){
        return createTheMeeting(DBIHandlerManager.getMeetingDAO(),meeting.getDistrict(),meeting.getMeetingdate().toString(),meeting.getNote(),meeting.getCreatedby());
    }

    public static String createTheMeeting(final String district,final String timeInString, final String note,final String createdBy){
        return createTheMeeting(DBIHandlerManager.getMeetingDAO(),district,timeInString,note,createdBy);
    }

    public static String createTheMeeting(final NgoMeetingDao dbi,final String district,final String timeInString, final String note,final String createdBy ){
        String[] duration = timeInString.split("X");
        String startingTime = duration[0];
        if (!DistrictUtility.isThereADistricteNamed(district))
            return "District[" + district + "]does not exist";

        StringBuilder sb = new StringBuilder();
        NgoMeeting m = new NgoMeeting();
        m.setMeetingid(genUniqueMeetingID(dbi));

        try {
            DateFormat format =startingTime.contains("-")
                    ?new SimpleDateFormat("MMMM d, yyyy, hh-mm")
                    :new SimpleDateFormat("MMMM d, yyyy, hh");

            Date date = format.parse(startingTime);
            Timestamp meetingDate = new Timestamp(date.getTime());
            Double dur = duration.length== 2 ?Double.parseDouble(duration[1]):null;


            m.setDistrict(district);
            m.setMeetingdate(meetingDate);
            m.setDuration(dur);
            m.setNote(note);
            m.setCreatedby(createdBy);

            dbi.create(m);
            SMSMessageManager.handleNewMeeting(m);

        }catch (Throwable e){
            itsLogger.error(e.getMessage(),e);
            return sb.append("Failed to create the meeting").toString();
        }

        return sb.append("Created the meeting with meeting id["+m.getMeetingid()+"]").toString();

    }

    public static String delete(final Http.Request request) throws ParseException {
        NgoMeetingDao dbi = DBIHandlerManager.getMeetingDAO();
        return delete(request,dbi);
    }

    public static String delete(final Http.Request request,final NgoMeetingDao dbi) throws ParseException {
        Map<String, String[]> qs = request.queryString();

        String body = qs.get("Body")[0];

        String[] splitted = body.split(":");
        if (splitted.length != 2 )
            return "Make sure to have meeting ID";

        String meetingId = splitted[1];
        String createdBy = qs.get("From")[0];

        return deleteMeeting(dbi, meetingId, createdBy);
    }

    public static String deleteMeeting(String meetingId, String createdBy) throws ParseException {
        return deleteMeeting(DBIHandlerManager.getMeetingDAO(),meetingId,createdBy);
    }
    public static String deleteMeeting(NgoMeetingDao dbi, String meetingId, String createdBy) throws ParseException {
        NgoMeeting meeting = MeetingUtility.getMeetingByMeetingId(meetingId);

        if (null==meeting)
            return "Meeting with meetingId of[" + meetingId + "]does not exist";
        if (!meeting.getCreatedby().equals(createdBy))
            return "Meeting is not owned by the requester";

        StringBuilder sb = new StringBuilder();


        try {
            dbi.delete(meeting);
            SMSMessageManager.meetingDelete(meeting);

        }catch (Throwable e){
            itsLogger.error(e.getMessage(),e);
            return sb.append("Failed to delete the meeting with meeting id["+meeting.getMeetingid()+"]").toString();
        }

        return sb.append("Deleted the meeting with meeting id["+meeting.getMeetingid()+"]").toString();
    }
}
