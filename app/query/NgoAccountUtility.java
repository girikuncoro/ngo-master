package query;

import dbaccess.dbojbects.pojo.NgoAccount;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.util.Iterator;

/**
 * Created by nhosgur on 1/6/15.
 */
public class NgoAccountUtility {
    private static final Logger itsLogger = Logger.getLogger(NgoAccountUtility.class);

    public static Iterator<NgoAccount> getAllNgoAccounts() throws ParseException {
        return DBIHandlerManager.getNgoAccountDAO().getAllAccounts();
    }

    public static NgoAccount getNgoAccountByEmail(final String emailAddress) throws ParseException {
        return DBIHandlerManager.getNgoAccountDAO().getAccountByEmail(emailAddress);
    }

    public static NgoAccount createNgoAccount(final NgoAccount account) throws ParseException {
        return DBIHandlerManager.getNgoAccountDAO().create(account);
    }

    public static NgoAccount getNgoAccountByPhone(final String phoneNumber) throws ParseException {
        return DBIHandlerManager.getNgoAccountDAO().getAccountByPhoneNumber(phoneNumber);
    }

}
