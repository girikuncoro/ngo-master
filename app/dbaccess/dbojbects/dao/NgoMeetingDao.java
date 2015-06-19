package dbaccess.dbojbects.dao;

import dbaccess.dbojbects.pojo.NgoMeeting;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.skife.jdbi.v2.sqlobject.helpers.MapResultAsBean;

import java.sql.Timestamp;
import java.util.Iterator;

/**
 * This class is generated Twilio Common Services with jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public abstract class NgoMeetingDao{
    /**
     *  Creation of a new row
     */
    @Transaction
    @SqlUpdate("insert into NGOMEETING(MEETINGID,NOTE,CREATEDBY,DISTRICT,MEETINGDATE,STATUS,UPDATEDATE,CREATEDATE) " +
                             "values (:MEETINGID,:NOTE,:CREATEDBY,:DISTRICT,:MEETINGDATE,:STATUS,NOW(),NOW())")
    protected abstract void create(@Bind("MEETINGID") String meetingId,
                                   @Bind("NOTE") String note,
                                   @Bind("CREATEDBY") String createdBy,
                                   @Bind("DISTRICT") String district,
                                   @Bind("MEETINGDATE") Timestamp meetingDate,
                                   @Bind("STATUS") String status);

    public NgoMeeting create(final NgoMeeting meeting){
        create(meeting.getMeetingid(), meeting.getNote(), meeting.getCreatedby(), meeting.getDistrict(), meeting.getMeetingdate(), null==meeting.getStatus()?"Scheduled":meeting.getStatus());
        return getById(meeting.getMeetingid());
    }

    /**
     *  Querying by the columns
     */

    @SqlQuery("SELECT MEETINGID,NOTE,CREATEDBY,DISTRICT,MEETINGDATE,STATUS,UPDATEDATE,CREATEDATE FROM NGOMEETING WHERE MEETINGID = :MEETINGID")
    @MapResultAsBean
    public abstract NgoMeeting getById(@Bind("MEETINGID") String MEETINGID);

    @SqlQuery("SELECT MEETINGID,NOTE,CREATEDBY,DISTRICT,MEETINGDATE,STATUS,UPDATEDATE,CREATEDATE FROM NGOMEETING ORDER BY MEETINGDATE")
    @MapResultAsBean
    public abstract Iterator<NgoMeeting> getAllMeetings();

    @SqlQuery("SELECT MEETINGID,NOTE,CREATEDBY,DISTRICT,MEETINGDATE,STATUS,UPDATEDATE,CREATEDATE FROM NGOMEETING WHERE CREATEDBY = :CREATEDBY ORDER BY MEETINGDATE")
    @MapResultAsBean
    public abstract Iterator<NgoMeeting> getMeetingsByCreator(@Bind("CREATEDBY")String createdBy);

    /**
     *  Providing Update capabilities
     */
    @SqlUpdate("update NGOCROP SET QUANTITY = :QUANTITY,UPDATEDATE = NOW()  WHERE DISTRICT = :DISTRICT AND CROPNAME = :CROPNAME AND ATTRIBUTE = :ATTRIBUTE")
    protected abstract void update(@Bind("DISTRICT") String DISTRICT,@Bind("CROPNAME") String CROPNAME,@Bind("ATTRIBUTE") String ATTRIBUTE,@Bind("QUANTITY") Double QUANTITY);


    /**
     *  Providing delete capabilities
     */
    //update NGOCROP SET QUANTITY = :QUANTITY,UPDATEDATE = NOW()  WHERE DISTRICT = :DISTRICT AND CROPNAME = :CROPNAME AND ATTRIBUTE = :ATTRIBUTE
    @SqlUpdate("update NGOMEETING  SET STATUS= :STATUS, UPDATEDATE = NOW() WHERE MEETINGID=:MEETINGID and CREATEDBY = :CREATEDBY and DISTRICT= :DISTRICT")
    protected abstract void updateMeetingStatus(@Bind("MEETINGID") String meetingId,@Bind("CREATEDBY") String CREATEDBY,@Bind("DISTRICT") String DISTRICT,@Bind("STATUS") String STATUS);

    /**
     *  Providing delete capabilities
     */
    public void delete(final NgoMeeting meeting){
        updateMeetingStatus(meeting.getMeetingid(), meeting.getCreatedby(), meeting.getDistrict(), "Cancelled");
    }
}
