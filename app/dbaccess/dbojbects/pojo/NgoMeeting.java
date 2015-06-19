package dbaccess.dbojbects.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * This class is generated Twilio Common Services with jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
@XmlRootElement
public class NgoMeeting implements java.io.Serializable {

    private static final long serialVersionUID = -6992;



    @JsonProperty
    private String meetingid;
    @JsonProperty
    private String note;
    @JsonProperty
    private String createdby;
    @JsonProperty
    private String district;
    @JsonProperty
    private java.sql.Timestamp meetingdate;
    @JsonProperty
    private String status;
    @JsonProperty
    private java.sql.Timestamp updatedate;
    @JsonProperty
    private java.sql.Timestamp createdate;
    @JsonProperty
    private Double duration;

    /**
     * Instantiates a new Ngocrop.
     */
    public NgoMeeting() {
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getMeetingid() {
        return meetingid;
    }

    public void setMeetingid(String meetingid) {
        this.meetingid = meetingid;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Timestamp getMeetingdate() {
        return meetingdate;
    }

    public void setMeetingdate(Timestamp meetingdate) {
        this.meetingdate = meetingdate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NgoMeeting)) return false;

        NgoMeeting meeting = (NgoMeeting) o;

        if (createdate != null ? !createdate.equals(meeting.createdate) : meeting.createdate != null) return false;
        if (createdby != null ? !createdby.equals(meeting.createdby) : meeting.createdby != null) return false;
        if (district != null ? !district.equals(meeting.district) : meeting.district != null) return false;
        if (duration != null ? !duration.equals(meeting.duration) : meeting.duration != null) return false;
        if (meetingdate != null ? !meetingdate.equals(meeting.meetingdate) : meeting.meetingdate != null) return false;
        if (meetingid != null ? !meetingid.equals(meeting.meetingid) : meeting.meetingid != null) return false;
        if (note != null ? !note.equals(meeting.note) : meeting.note != null) return false;
        if (status != null ? !status.equals(meeting.status) : meeting.status != null) return false;
        if (updatedate != null ? !updatedate.equals(meeting.updatedate) : meeting.updatedate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = meetingid != null ? meetingid.hashCode() : 0;
        result = 31 * result + (note != null ? note.hashCode() : 0);
        result = 31 * result + (createdby != null ? createdby.hashCode() : 0);
        result = 31 * result + (district != null ? district.hashCode() : 0);
        result = 31 * result + (meetingdate != null ? meetingdate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (updatedate != null ? updatedate.hashCode() : 0);
        result = 31 * result + (createdate != null ? createdate.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NgoMeeting{");
        sb.append("meetingid='").append(meetingid).append('\'');
        sb.append(", note='").append(note).append('\'');
        sb.append(", createdby='").append(createdby).append('\'');
        sb.append(", district='").append(district).append('\'');
        sb.append(", meetingdate=").append(meetingdate);
        sb.append(", status='").append(status).append('\'');
        sb.append(", updatedate=").append(updatedate);
        sb.append(", createdate=").append(createdate);
        sb.append(", duration=").append(duration);
        sb.append('}');
        return sb.toString();
    }
}
