package dbaccess.dbojbects.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is generated Twilio Common Services with jOOQ.
 */
@java.lang.SuppressWarnings({"all", "unchecked", "rawtypes"})
@XmlRootElement
public class Ngodistrict implements java.io.Serializable {

    private static final long serialVersionUID = 1057459878;

    @JsonProperty
    private String district;
    @JsonProperty
    private java.sql.Timestamp updatedate;
    @JsonProperty
    private java.sql.Timestamp createdate;

    public Ngodistrict() {
    }

    public Ngodistrict(
            @JsonProperty("district")
            String district,
            @JsonProperty("updatedate")
            java.sql.Timestamp updatedate,
            @JsonProperty("createdate")
            java.sql.Timestamp createdate
    ) {
        this.district = district;
        this.updatedate = updatedate;
        this.createdate = createdate;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public java.sql.Timestamp getUpdatedate() {
        return this.updatedate;
    }

    public void setUpdatedate(java.sql.Timestamp updatedate) {
        this.updatedate = updatedate;
    }

    public java.sql.Timestamp getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(java.sql.Timestamp createdate) {
        this.createdate = createdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ngodistrict)) {
            return false;
        }
        Ngodistrict other = (Ngodistrict) o;

        if (district != null ? !district.equals(other.district) : other.district != null) {
            return false;
        }
        if (updatedate != null ? !updatedate.equals(other.updatedate) : other.updatedate != null) {
            return false;
        }
        if (createdate != null ? !createdate.equals(other.createdate) : other.createdate != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = district != null ? district.hashCode() : 0;
        result = 31 * result + (updatedate != null ? updatedate.hashCode() : 0);
        result = 31 * result + (createdate != null ? createdate.hashCode() : 0);
        return result;
    }

}
