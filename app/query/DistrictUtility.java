package query;

import dbaccess.dbojbects.pojo.Ngodistrict;

import java.util.Iterator;

/**
 * Created by nhosgur on 1/5/15.
 */
public class DistrictUtility {
    /**
     * Is there a districte named.
     *
     * @param district the district
     * @return the boolean
     */
    public static boolean isThereADistricteNamed(final String district) {
        return null != fetchDistrictByName(district);
    }

    /**
     * Fetch district by name.
     *
     * @param district the district
     * @return the ngodistrict
     */
    public static Ngodistrict fetchDistrictByName(final String district) {
        return DBIHandlerManager.getDistrictDAO().getByDistrict(district);
    }

    /**
     * Gets districts.
     *
     * @return the districts
     */
    public static Iterator<Ngodistrict> getDistricts() {
        return DBIHandlerManager.getDistrictDAO().getAllDistricts();
    }
    public static Ngodistrict getDistrictsByName(final String name) {
        return DBIHandlerManager.getDistrictDAO().getByDistrict(name);
    }

    public static Ngodistrict createDistrict(final Ngodistrict distObj){
        return DBIHandlerManager.getDistrictDAO().create(distObj);
    }
}
