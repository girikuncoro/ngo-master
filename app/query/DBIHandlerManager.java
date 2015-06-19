package query;

import dbaccess.dbojbects.dao.*;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.tweak.TransactionHandler;

import static core.dbi.NGOConstants.DEF_DBI_PARAMS;

/**
 * Created by nhosgur on 8/9/14.
 */
class DBIHandlerManager {
    private static DBI dbi;
    private static Handle handle;

    static {
        dbi = new DBI(DEF_DBI_PARAMS.getUrl(), DEF_DBI_PARAMS.getUser(), DEF_DBI_PARAMS.getPassword());
        TransactionHandler th = dbi.getTransactionHandler();
        handle = dbi.open();
    }

    /**
     * Get district dAO.
     *
     * @return the ngodistrict dao
     */
    public static NgodistrictDao getDistrictDAO() {
        return dbi.open(NgodistrictDao.class);
    }

    /**
     * Get registration dAO.
     *
     * @return the ngoregistration dao
     */
    public static NgoregistrationDao getRegistrationDAO() {
        return dbi.open(NgoregistrationDao.class);
    }

    /**
     * Get crop dAO.
     *
     * @return the ngocrop dao
     */
    public static NgocropDao getCropDAO() {
        return dbi.open(NgocropDao.class);
    }

    /**
     * Get meeting DAO.
     *
     * @return the ngomeeting dao
     */
    public static NgoMeetingDao getMeetingDAO() {
        return dbi.open(NgoMeetingDao.class);
    }

    /**
     * Gets rSVDAO.
     *
     * @return the rSVDAO
     */
    public static NgoRSVPDao getRSVDAO() {
        return dbi.open(NgoRSVPDao.class);
    }

    /**
     * Gets ngo account dAO.
     *
     * @return the ngo account dAO
     */
    public static NgoAccountDao getNgoAccountDAO() {
        return dbi.open(NgoAccountDao.class);
    }


}
