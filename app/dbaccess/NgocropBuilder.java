package dbaccess;

import dbaccess.dbojbects.pojo.Ngocrop;

import java.sql.Timestamp;

/**
 * The type Ngocrop builder.
 */
public class NgocropBuilder {
    private String district;
    private String cropname;
    private String attribute;
    private Double quantity;
    private Timestamp updatedate;
    private Timestamp createdate;

    /**
     * Sets district.
     *
     * @param district the district
     * @return the district
     */
    public NgocropBuilder setDistrict(String district) {
        this.district = district;
        return this;
    }

    /**
     * Sets cropname.
     *
     * @param cropname the cropname
     * @return the cropname
     */
    public NgocropBuilder setCropname(String cropname) {
        this.cropname = cropname;
        return this;
    }

    /**
     * Sets attribute.
     *
     * @param attribute the attribute
     * @return the attribute
     */
    public NgocropBuilder setAttribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     * @return the quantity
     */
    public NgocropBuilder setQuantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Sets updatedate.
     *
     * @param updatedate the updatedate
     * @return the updatedate
     */
    public NgocropBuilder setUpdatedate(Timestamp updatedate) {
        this.updatedate = updatedate;
        return this;
    }

    /**
     * Sets createdate.
     *
     * @param createdate the createdate
     * @return the createdate
     */
    public NgocropBuilder setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
        return this;
    }

    /**
     * Create ngocrop.
     *
     * @return the ngocrop
     */
    public Ngocrop createNgocrop() {
        return new Ngocrop(district, cropname, attribute, quantity, updatedate, createdate);
    }
}
