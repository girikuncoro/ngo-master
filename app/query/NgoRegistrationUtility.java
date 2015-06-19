package query;

import dbaccess.dbojbects.pojo.Ngoregistration;

import java.util.Iterator;

/**
 * Created by nhosgur on 1/5/15.
 */
public class NgoRegistrationUtility {
    /**
     * Gets all.
     *
     * @return all
     */
    public static Iterator<Ngoregistration> getAll() {
        return DBIHandlerManager.getRegistrationDAO().getAll();
    }

    /**
     * Get by phone number.
     *
     * @param phoneNumber the phone number
     * @return the ngoregistration
     */
    public static Ngoregistration getByPhoneNumber(final String phoneNumber) {
        return DBIHandlerManager.getRegistrationDAO().getByPhonenumber(phoneNumber);
    }

    /**
     * Gets by district.
     *
     * @param districtName the district name
     * @return the by district
     */
    public static Iterator<Ngoregistration> getByDistrict(final String districtName) {
        return DBIHandlerManager.getRegistrationDAO().getByDistrict(districtName);
    }

    /**
     * Create ngoregistration.
     *
     * @param ngoregistration the ngoregistration
     * @return the ngoregistration
     */
    public static Ngoregistration create(final Ngoregistration ngoregistration) {
        return DBIHandlerManager.getRegistrationDAO().create(ngoregistration);
    }

    /**
     * Update ngoregistration.
     *
     * @param ngoregistration the ngoregistration
     * @return the ngoregistration
     */
    public static Ngoregistration update(final String phoneNumber,final Ngoregistration ngoregistration) {
        return DBIHandlerManager.getRegistrationDAO().update(phoneNumber, ngoregistration);
    }
}
