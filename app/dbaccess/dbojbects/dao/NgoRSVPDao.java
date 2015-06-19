package dbaccess.dbojbects.dao;

import dbaccess.dbojbects.pojo.NgoRSVP;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.skife.jdbi.v2.sqlobject.helpers.MapResultAsBean;

import java.util.Iterator;

/**
 * This class is generated Twilio Common Services with jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public abstract class NgoRSVPDao {
    /**
     *  Creation of a new row
     */
    @Transaction
    @SqlUpdate("insert into NGORSVP(MEETINGID,RSVRESPONSE,RSVNUMBER,UPDATEDATE,CREATEDATE) " +
                             "values (:MEETINGID,:RSVRESPONSE,:RSVNUMBER,NOW(),NOW())")
    protected abstract void create(@Bind("MEETINGID") String meetingId,
                                   @Bind("RSVRESPONSE") String rsvpResponse,
                                   @Bind("RSVNUMBER") String rsvpNumber);

    public NgoRSVP create(final NgoRSVP rsvp){
        if(null==getByRSVPAndMeetingId(rsvp.getMeetingid(),rsvp.getRsvnumber())) {
            create(rsvp.getMeetingid(), rsvp.getRsvresponse(), rsvp.getRsvnumber());
        }else{
            updateRSVP(rsvp.getMeetingid(), rsvp.getRsvnumber(), rsvp.getRsvresponse());
        }
        return getByRSVPAndMeetingId(rsvp.getMeetingid(),rsvp.getRsvnumber());
    }


    @SqlQuery("SELECT MEETINGID,RSVRESPONSE,RSVNUMBER,UPDATEDATE,CREATEDATE FROM NGORSVP ORDER BY UPDATEDATE")
    @MapResultAsBean
    public abstract Iterator<NgoRSVP> getAllRSVPs();

    @SqlQuery("SELECT MEETINGID,RSVRESPONSE,RSVNUMBER,UPDATEDATE,CREATEDATE FROM NGORSVP WHERE MEETINGID = :MEETINGID")
    @MapResultAsBean
    public abstract Iterator<NgoRSVP> getByMeetingID(@Bind("MEETINGID")String meetingId);

    @SqlQuery("SELECT MEETINGID,RSVRESPONSE,RSVNUMBER,UPDATEDATE,CREATEDATE FROM NGORSVP WHERE MEETINGID = :MEETINGID and RSVNUMBER= :RSVNUMBER")
    @MapResultAsBean
    public abstract NgoRSVP getByRSVPAndMeetingId(@Bind("MEETINGID")String meetingId,@Bind("RSVNUMBER")String RSVNUMBER);


    @SqlUpdate("update NGORSVP SET RSVRESPONSE= :RSVRESPONSE, UPDATEDATE= NOW()  WHERE MEETINGID = :MEETINGID and RSVNUMBER= :RSVNUMBER")
    protected abstract void updateRSVP(@Bind("MEETINGID")String meetingId,@Bind("RSVNUMBER")String rsvnumber,@Bind("RSVRESPONSE")String rsvresponse);
}
