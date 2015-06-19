package dbaccess;

import java.sql.Timestamp;

/**
 * Created by nhosgur on 8/8/14.
 */
public class NgoQuery {
    private String district;
    private String name;
    private String attribute;
    private Double quantity;
    private java.sql.Timestamp updatedateFrom;
    private java.sql.Timestamp updatedateTo;
    private boolean andOrOr;

    /**
     * Instantiates a new Ngo query.
     */
    public NgoQuery() {
    }

    /**
     * Instantiates a new Ngo query.
     *
     * @param district       the district
     * @param name           the name
     * @param attribute      the attribute
     * @param quantity       the quantity
     * @param updatedateFrom the updatedate from
     * @param updatedateTo   the updatedate to
     * @param andOrOr        the and or or
     */
    public NgoQuery(String district, String name, String attribute, Double quantity, Timestamp updatedateFrom,
            Timestamp updatedateTo, boolean andOrOr) {
        this.district = district;
        this.name = name;
        this.attribute = attribute;
        this.quantity = quantity;
        this.updatedateFrom = updatedateFrom;
        this.updatedateTo = updatedateTo;
        this.andOrOr = andOrOr;
    }

    /**
     * Gets district.
     *
     * @return the district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Sets district.
     *
     * @param district the district
     */
    public void setDistrict(String district) {
        this.district = district;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets attribute.
     *
     * @return the attribute
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * Sets attribute.
     *
     * @param attribute the attribute
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * Gets quantity.
     *
     * @return the quantity
     */
    public Double getQuantity() {
        return quantity;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     */
    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets updatedate from.
     *
     * @return the updatedate from
     */
    public Timestamp getUpdatedateFrom() {
        return updatedateFrom;
    }

    /**
     * Sets updatedate from.
     *
     * @param updatedateFrom the updatedate from
     */
    public void setUpdatedateFrom(Timestamp updatedateFrom) {
        this.updatedateFrom = updatedateFrom;
    }

    /**
     * Gets updatedate to.
     *
     * @return the updatedate to
     */
    public Timestamp getUpdatedateTo() {
        return updatedateTo;
    }

    /**
     * Sets updatedate to.
     *
     * @param updatedateTo the updatedate to
     */
    public void setUpdatedateTo(Timestamp updatedateTo) {
        this.updatedateTo = updatedateTo;
    }

    /**
     * Is and or or.
     *
     * @return the boolean
     */
    public boolean isAndOrOr() {
        return andOrOr;
    }

    /**
     * Sets and or or.
     *
     * @param andOrOr the and or or
     */
    public void setAndOrOr(boolean andOrOr) {
        this.andOrOr = andOrOr;
    }
}
