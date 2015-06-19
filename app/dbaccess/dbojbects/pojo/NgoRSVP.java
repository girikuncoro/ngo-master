package dbaccess.dbojbects.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * This class is generated Twilio Common Services with jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
@XmlRootElement
public class NgoRSVP implements java.io.Serializable {

    private static final long serialVersionUID = -7345;

    @JsonProperty
    private String meetingid;
    @JsonProperty
    private String rsvnumber;
    @JsonProperty
    private String rsvresponse;
    @JsonProperty
    private Timestamp updatedate;
    @JsonProperty
    private Timestamp createdate;

    /**
     * Instantiates a new Ngo meeting rSVP.
     */
    public NgoRSVP() {

    }

    public String getMeetingid() {
        return meetingid;
    }

    public void setMeetingid(String meetingid) {
        this.meetingid = meetingid;
    }

    public String getRsvnumber() {
        return rsvnumber;
    }

    public void setRsvnumber(String rsvnumber) {
        this.rsvnumber = rsvnumber;
    }

    public String getRsvresponse() {
        return rsvresponse;
    }

    public void setRsvresponse(String rsvresponse) {
        this.rsvresponse = rsvresponse;
    }

    public Timestamp getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Timestamp updatedate) {
        this.updatedate = updatedate;
    }

    public Timestamp getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }
}
