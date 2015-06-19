package dbaccess;

import java.sql.Timestamp;

/**
 * The type Ngo query builder.
 */
public class NgoQueryBuilder {
    private String district;
    private String name;
    private String attribute;
    private Double quantity;
    private Timestamp updatedateFrom;
    private Timestamp updatedateTo;
    private boolean andOrOr;

    /**
     * Sets district.
     *
     * @param district the district
     * @return the district
     */
    public NgoQueryBuilder setDistrict(String district) {
        this.district = district;
        return this;
    }

    /**
     * Sets name.
     *
     * @param name the name
     * @return the name
     */
    public NgoQueryBuilder setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Sets attribute.
     *
     * @param attribute the attribute
     * @return the attribute
     */
    public NgoQueryBuilder setAttribute(String attribute) {
        this.attribute = attribute;
        return this;
    }

    /**
     * Sets quantity.
     *
     * @param quantity the quantity
     * @return the quantity
     */
    public NgoQueryBuilder setQuantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    /**
     * Sets updatedate from.
     *
     * @param updatedateFrom the updatedate from
     * @return the updatedate from
     */
    public NgoQueryBuilder setUpdatedateFrom(Timestamp updatedateFrom) {
        this.updatedateFrom = updatedateFrom;
        return this;
    }

    /**
     * Sets updatedate to.
     *
     * @param updatedateTo the updatedate to
     * @return the updatedate to
     */
    public NgoQueryBuilder setUpdatedateTo(Timestamp updatedateTo) {
        this.updatedateTo = updatedateTo;
        return this;
    }

    /**
     * Sets and or or.
     *
     * @param andOrOr the and or or
     * @return the and or or
     */
    public NgoQueryBuilder setAndOrOr(boolean andOrOr) {
        this.andOrOr = andOrOr;
        return this;
    }

    /**
     * Create ngo query.
     *
     * @return the ngo query
     */
    public NgoQuery createNgoQuery() {
        return new NgoQuery(district, name, attribute, quantity, updatedateFrom, updatedateTo, andOrOr);
    }
}
