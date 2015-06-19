package dbaccess;

import dbaccess.dbojbects.pojo.Ngoregistration;

import java.sql.Timestamp;

/**
 * The type Ngoregistration builder.
 */
public class NgoregistrationBuilder {
    private String phonenumber;
    private String district;
    private Timestamp updatedate;
    private Timestamp createdate;

    /**
     * Sets phonenumber.
     *
     * @param phonenumber the phonenumber
     * @return the phonenumber
     */
    public NgoregistrationBuilder setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
        return this;
    }

    /**
     * Sets district.
     *
     * @param district the district
     * @return the district
     */
    public NgoregistrationBuilder setDistrict(String district) {
        this.district = district;
        return this;
    }

    /**
     * Sets updatedate.
     *
     * @param updatedate the updatedate
     * @return the updatedate
     */
    public NgoregistrationBuilder setUpdatedate(Timestamp updatedate) {
        this.updatedate = updatedate;
        return this;
    }

    /**
     * Sets createdate.
     *
     * @param createdate the createdate
     * @return the createdate
     */
    public NgoregistrationBuilder setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
        return this;
    }

    /**
     * Create ngoregistration.
     *
     * @return the ngoregistration
     */
    public Ngoregistration createNgoregistration() {
        return new Ngoregistration(district, phonenumber, updatedate, createdate);
    }
}
