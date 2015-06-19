package query;

import dbaccess.dbojbects.pojo.NgoMeeting;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.Iterator;

/**
 * Created by nhosgur on 1/6/15.
 */
public class MeetingUtility {
    private static final Logger itsLogger = Logger.getLogger(MeetingUtility.class);

    public static Iterator<NgoMeeting> getAllMeetings() throws ParseException {
        return DBIHandlerManager.getMeetingDAO().getAllMeetings();
    }

    public static Iterator<NgoMeeting> getMeetingsByCreator(final String craterPhoneNumber) throws ParseException {
        return DBIHandlerManager.getMeetingDAO().getMeetingsByCreator(craterPhoneNumber);
    }
    public static NgoMeeting getMeetingByMeetingId(final String meetingId) throws ParseException {
        return DBIHandlerManager.getMeetingDAO().getById(meetingId);
    }
}
