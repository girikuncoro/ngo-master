package dbaccess.dbojbects.dao;

import dbaccess.dbojbects.pojo.NgoAccount;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.skife.jdbi.v2.sqlobject.helpers.MapResultAsBean;

import java.util.Iterator;

/**
 * This class is generated Twilio Common Services with jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public abstract class NgoAccountDao {
    /**
     * Creation of a new row
     */
    @Transaction
    @SqlUpdate("insert into NGOACCOUNT(emailAddress,phoneNumber,firstName,lastName,username,digest,salt,UPDATEDATE,CREATEDATE) " +
            "values (:emailAddress,:phoneNumber,:firstName,:lastName,:username,:digest,:salt,NOW(),NOW())")
    protected abstract void create(@Bind("emailAddress") String emailAddress,
                                   @Bind("phoneNumber") String phoneNumber,
                                   @Bind("firstName") String firstName,
                                   @Bind("lastName") String lastName,
                                   @Bind("username") String username,
                                   @Bind("digest") String digest,
                                   @Bind("salt") String salt);

    public NgoAccount create(final NgoAccount ngoAccount) {
        create(ngoAccount.getEmailAddress(), ngoAccount.getPhoneNumber(), ngoAccount.getFirstName(),
                ngoAccount.getLastName(), ngoAccount.getUsername(), ngoAccount.getDigest(), ngoAccount.getSalt());
        return ngoAccount;
    }
    /**
     * Querying by the columns
     */

    @SqlQuery("SELECT emailAddress,phoneNumber,firstName,lastName,username,digest,salt,UPDATEDATE,CREATEDATE FROM NGOACCOUNT ORDER BY UPDATEDATE")
    @MapResultAsBean
    public abstract Iterator<NgoAccount> getAllAccounts();

    @SqlQuery("SELECT emailAddress,phoneNumber,firstName,lastName,username,digest,salt,UPDATEDATE,CREATEDATE FROM NGOACCOUNT WHERE phoneNumber= :phoneNumber ")
    @MapResultAsBean
    public abstract NgoAccount getAccountByPhoneNumber(@Bind("phoneNumber") String phoneNumber);

    @SqlQuery("SELECT emailAddress,phoneNumber,firstName,lastName,username,digest,salt,UPDATEDATE,CREATEDATE FROM NGOACCOUNT WHERE emailAddress= :emailAddress ")
    @MapResultAsBean
    public abstract NgoAccount getAccountByEmail(@Bind("emailAddress") String emailAddress);

    @SqlQuery("SELECT emailAddress,phoneNumber,firstName,lastName,username,digest,salt,UPDATEDATE,CREATEDATE FROM NGOACCOUNT WHERE username= :username ")
    @MapResultAsBean
    public abstract NgoAccount getAccountByUserName(@Bind("username") String username);


}
