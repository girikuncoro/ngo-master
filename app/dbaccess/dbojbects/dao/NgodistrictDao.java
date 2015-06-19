package dbaccess.dbojbects.dao;

import dbaccess.dbojbects.pojo.Ngodistrict;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.helpers.MapResultAsBean;

import java.util.Iterator;

/**
 * This class is generated Twilio Common Services with jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public abstract class NgodistrictDao{
	/**
	 *  Creation of a new row
	 */
	@SqlUpdate("insert into NGODISTRICT(DISTRICT,UPDATEDATE,CREATEDATE) values (:DISTRICT,NOW(),NOW())")
	protected abstract void create(@Bind("DISTRICT") String DISTRICT);


	public Ngodistrict create(final Ngodistrict instance){
		create(instance.getDistrict());
		return getById(instance.getDistrict());
	}


	/**
	 *  Querying by the columns
	 */
	@SqlQuery("SELECT DISTRICT,UPDATEDATE,CREATEDATE FROM NGODISTRICT WHERE DISTRICT=:DISTRICT")
	@MapResultAsBean
	public abstract Ngodistrict getByDistrict(@Bind("DISTRICT") String District);

	public Ngodistrict getById(final String id){
		return getByDistrict(id);
	}

	@SqlQuery("SELECT DISTRICT,UPDATEDATE,CREATEDATE FROM NGODISTRICT WHERE CREATEDATE=:CREATEDATE")
	@MapResultAsBean
	public abstract Iterator<Ngodistrict> getByCreatedate(@Bind("CREATEDATE") java.sql.Timestamp Createdate);


    @SqlQuery("SELECT DISTRICT,UPDATEDATE,CREATEDATE FROM NGODISTRICT ORDER BY CREATEDATE DESC")
    @MapResultAsBean
    public abstract Iterator<Ngodistrict> getAllDistricts();


    @SqlQuery("SELECT DISTRICT,UPDATEDATE,CREATEDATE FROM NGODISTRICT WHERE UPDATEDATE=:UPDATEDATE")
	@MapResultAsBean
	public abstract Iterator<Ngodistrict> getByUpdatedate(@Bind("UPDATEDATE") java.sql.Timestamp Updatedate);


	/**
	 *  Providing Update capabilities
	 */
	@SqlUpdate("update NGODISTRICT SET UPDATEDATE = NOW()  WHERE DISTRICT = :DISTRICT")
	protected abstract void update(@Bind("DISTRICT") String DISTRICT);

	public Ngodistrict update(String uk, Ngodistrict instance){
		if(!(uk.equals(instance.getDistrict()))) throw new RuntimeException("Update/Delete key is invalid");
		update(instance.getDistrict());
		return getById(instance.getDistrict());
	}


	/**
	 *  Providing delete capabilities
	 */
	@SqlUpdate("delete From NGODISTRICT  WHERE DISTRICT=:DISTRICT AND UPDATEDATE=:UPDATEDATE AND CREATEDATE=:CREATEDATE")
	protected abstract void delete(@Bind("DISTRICT")
    String DISTRICT,@Bind("UPDATEDATE") java.sql.Timestamp UPDATEDATE,@Bind("CREATEDATE") java.sql.Timestamp CREATEDATE);

	public Ngodistrict delete(String uk,Ngodistrict instance){
		if(!(uk.equals(instance.getDistrict()))) throw new RuntimeException("Update/Delete key is invalid");

		delete(instance.getDistrict(),instance.getUpdatedate(),instance.getCreatedate());
		return instance;
	}
	public Ngodistrict delete(final String id){
		Ngodistrict instance = getById(id); 
		if(null==instance)return null;
		delete(instance.getDistrict(),instance);
		return instance;
	}
}
