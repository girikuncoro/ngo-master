package dbaccess.dbojbects.dao;

import dbaccess.dbojbects.pojo.Ngoregistration;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.helpers.MapResultAsBean;

import java.util.Iterator;

/**
 * This class is generated Twilio Common Services with jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public abstract class NgoregistrationDao{
	/**
	 *  Creation of a new row
	 */
	@SqlUpdate("insert into NGOREGISTRATION(DISTRICT,PHONENUMBER,UPDATEDATE,CREATEDATE) values (:DISTRICT,:PHONENUMBER,NOW(),NOW())")
	protected abstract void create(@Bind("DISTRICT") String DISTRICT,@Bind("PHONENUMBER") String PHONENUMBER);

	public Ngoregistration create(final Ngoregistration instance){
		create(instance.getDistrict(),instance.getPhonenumber());
		return getById(instance.getPhonenumber());
	}


	/**
	 *  Querying by the columns
	 */
	@SqlQuery("SELECT DISTRICT,PHONENUMBER,UPDATEDATE,CREATEDATE FROM NGOREGISTRATION WHERE CREATEDATE=:CREATEDATE")
	@MapResultAsBean
	public abstract Iterator<Ngoregistration> getByCreatedate(@Bind("CREATEDATE") java.sql.Timestamp Createdate);

	@SqlQuery("SELECT DISTRICT,PHONENUMBER,UPDATEDATE,CREATEDATE FROM NGOREGISTRATION WHERE UPDATEDATE=:UPDATEDATE")
	@MapResultAsBean
	public abstract Iterator<Ngoregistration> getByUpdatedate(@Bind("UPDATEDATE") java.sql.Timestamp Updatedate);

	@SqlQuery("SELECT DISTRICT,PHONENUMBER,UPDATEDATE,CREATEDATE FROM NGOREGISTRATION WHERE PHONENUMBER=:PHONENUMBER")
	@MapResultAsBean
	public abstract Ngoregistration getByPhonenumber(@Bind("PHONENUMBER") String Phonenumber);

	public Ngoregistration getById(final String id){
		return getByPhonenumber(id);
	}

	@SqlQuery("SELECT DISTRICT,PHONENUMBER,UPDATEDATE,CREATEDATE FROM NGOREGISTRATION WHERE DISTRICT=:DISTRICT")
	@MapResultAsBean
	public abstract Iterator<Ngoregistration> getByDistrict(@Bind("DISTRICT") String District);


	@SqlQuery("SELECT DISTRICT,PHONENUMBER,UPDATEDATE,CREATEDATE FROM NGOREGISTRATION")
	@MapResultAsBean
	public abstract Iterator<Ngoregistration> getAll();

	/**
	 *  Providing Update capabilities
	 */
	@SqlUpdate("update NGOREGISTRATION SET DISTRICT = :DISTRICT,UPDATEDATE = NOW()  WHERE PHONENUMBER = :PHONENUMBER")
	protected abstract void update(@Bind("DISTRICT") String DISTRICT,@Bind("PHONENUMBER") String PHONENUMBER);

	public Ngoregistration update(String uk, Ngoregistration instance){
		if(!(uk.equals(instance.getPhonenumber()))) throw new RuntimeException("Update/Delete key is invalid");
		update(instance.getDistrict(),instance.getPhonenumber());
		return getById(instance.getPhonenumber());
	}


	/**
	 *  Providing delete capabilities
	 */
	@SqlUpdate("delete From NGOREGISTRATION  WHERE DISTRICT=:DISTRICT AND PHONENUMBER=:PHONENUMBER AND UPDATEDATE=:UPDATEDATE AND CREATEDATE=:CREATEDATE")
	protected abstract void delete(@Bind("DISTRICT") String DISTRICT,@Bind("PHONENUMBER") String PHONENUMBER,@Bind("UPDATEDATE") java.sql.Timestamp UPDATEDATE,@Bind("CREATEDATE") java.sql.Timestamp CREATEDATE);

	public Ngoregistration delete(String uk,Ngoregistration instance){
		if(!(uk.equals(instance.getPhonenumber()))) throw new RuntimeException("Update/Delete key is invalid");

		delete(instance.getDistrict(),instance.getPhonenumber(),instance.getUpdatedate(),instance.getCreatedate());
		return instance;
	}
	public Ngoregistration delete(final String id){
		Ngoregistration instance = getById(id); 
		if(null==instance)return null;
		delete(instance.getPhonenumber(),instance);
		return instance;
	}
}
