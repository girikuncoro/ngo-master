package dbaccess.dbojbects.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class is generated Twilio Common Services with jOOQ.
 */
@java.lang.SuppressWarnings({"all", "unchecked", "rawtypes"})
@XmlRootElement
public class Ngocrop implements java.io.Serializable {

    private static final long serialVersionUID = -699212862;


    /**
     * The type Ngocrop pK.
     */
    public static class NgocropPK implements java.io.Serializable {
        @JsonProperty
        private String district;
        @JsonProperty
        private String cropname;
        @JsonProperty
        private String attribute;

        /**
         * Instantiates a new Ngocrop pK.
         */
        public NgocropPK() {
        }

        /**
         * Instantiates a new Ngocrop pK.
         *
         * @param district  the district
         * @param cropname  the cropname
         * @param attribute the attribute
         */
        public NgocropPK(@JsonProperty("district") String district, @JsonProperty("cropname") String cropname,
                @JsonProperty("attribute") String attribute) {
            this.district = district;
            this.cropname = cropname;
            this.attribute = attribute;
        }

        /**
         * Gets district.
         *
         * @return the district
         */
        public String getDistrict() {
            return this.district;
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
         * Gets cropname.
         *
         * @return the cropname
         */
        public String getCropname() {
            return this.cropname;
        }

        /**
         * Sets cropname.
         *
         * @param cropname the cropname
         */
        public void setCropname(String cropname) {
            this.cropname = cropname;
        }

        /**
         * Gets attribute.
         *
         * @return the attribute
         */
        public String getAttribute() {
            return this.attribute;
        }

        /**
         * Sets attribute.
         *
         * @param attribute the attribute
         */
        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Ngocrop)) {
                return false;
            }
            Ngocrop other = (Ngocrop) o;

            if (district != null ? !district.equals(other.district) : other.district != null) {
                return false;
            }
            if (cropname != null ? !cropname.equals(other.cropname) : other.cropname != null) {
                return false;
            }
            if (attribute != null ? !attribute.equals(other.attribute) : other.attribute != null) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            int result = district != null ? district.hashCode() : 0;
            result = 31 * result + (cropname != null ? cropname.hashCode() : 0);
            result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
            return result;
        }
    }


    @JsonProperty
    private String district;
    @JsonProperty
    private String cropname;
    @JsonProperty
    private String attribute;
    @JsonProperty
    private Double quantity;
    @JsonProperty
    private java.sql.Timestamp updatedate;
    @JsonProperty
    private java.sql.Timestamp createdate;

    /**
     * Instantiates a new Ngocrop.
     */
    public Ngocrop() {
    }

    /**
     * Instantiates a new Ngocrop.
     *
     * @param district   the district
     * @param cropname   the cropname
     * @param attribute  the attribute
     * @param quantity   the quantity
     * @param updatedate the updatedate
     * @param createdate the createdate
     */
    public Ngocrop(
            @JsonProperty("district")
            String district,
            @JsonProperty("cropname")
            String cropname,
            @JsonProperty("attribute")
            String attribute,
            @JsonProperty("quantity")
            Double quantity,
            @JsonProperty("updatedate")
            java.sql.Timestamp updatedate,
            @JsonProperty("createdate")
            java.sql.Timestamp createdate
    ) {
        this.district = district;
        this.cropname = cropname;
        this.attribute = attribute;
        this.quantity = quantity;
        this.updatedate = updatedate;
        this.createdate = createdate;
    }

    /**
     * Gets district.
     *
     * @return the district
     */
    public String getDistrict() {
        return this.district;
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
     * Gets cropname.
     *
     * @return the cropname
     */
    public String getCropname() {
        return this.cropname;
    }

    /**
     * Sets cropname.
     *
     * @param cropname the cropname
     */
    public void setCropname(String cropname) {
        this.cropname = cropname;
    }

    /**
     * Gets attribute.
     *
     * @return the attribute
     */
    public String getAttribute() {
        return this.attribute;
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
        return this.quantity;
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
     * Gets updatedate.
     *
     * @return the updatedate
     */
    public java.sql.Timestamp getUpdatedate() {
        return this.updatedate;
    }

    /**
     * Sets updatedate.
     *
     * @param updatedate the updatedate
     */
    public void setUpdatedate(java.sql.Timestamp updatedate) {
        this.updatedate = updatedate;
    }

    /**
     * Gets createdate.
     *
     * @return the createdate
     */
    public java.sql.Timestamp getCreatedate() {
        return this.createdate;
    }

    /**
     * Sets createdate.
     *
     * @param createdate the createdate
     */
    public void setCreatedate(java.sql.Timestamp createdate) {
        this.createdate = createdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ngocrop)) {
            return false;
        }
        Ngocrop other = (Ngocrop) o;

        if (district != null ? !district.equals(other.district) : other.district != null) {
            return false;
        }
        if (cropname != null ? !cropname.equals(other.cropname) : other.cropname != null) {
            return false;
        }
        if (attribute != null ? !attribute.equals(other.attribute) : other.attribute != null) {
            return false;
        }
        if (quantity != null ? !quantity.equals(other.quantity) : other.quantity != null) {
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
        result = 31 * result + (cropname != null ? cropname.hashCode() : 0);
        result = 31 * result + (attribute != null ? attribute.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        result = 31 * result + (updatedate != null ? updatedate.hashCode() : 0);
        result = 31 * result + (createdate != null ? createdate.hashCode() : 0);
        return result;
    }

}
