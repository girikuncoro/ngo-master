package dbaccess.dbojbects.dao;

import dbaccess.dbojbects.pojo.Ngocrop;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.skife.jdbi.v2.sqlobject.helpers.MapResultAsBean;

import java.util.Iterator;

/**
 * This class is generated Twilio Common Services with jOOQ.
 */
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public abstract class NgocropDao{
    /**
     *  Creation of a new row
     */
    @Transaction
    @SqlUpdate("insert into NGOCROP(DISTRICT,CROPNAME,ATTRIBUTE,QUANTITY,UPDATEDATE,CREATEDATE) values (:DISTRICT,:CROPNAME,:ATTRIBUTE,:QUANTITY,NOW(),NOW())")
    protected abstract void create(@Bind("DISTRICT") String DISTRICT,@Bind("CROPNAME") String CROPNAME,@Bind("ATTRIBUTE") String ATTRIBUTE,@Bind("QUANTITY") Double QUANTITY);

    public Ngocrop create(final Ngocrop instance){
        create(instance.getDistrict(),instance.getCropname(),instance.getAttribute(),instance.getQuantity());
        return getById(new Ngocrop.NgocropPK(instance.getDistrict(),instance.getCropname(),instance.getAttribute()));
    }
    /**
     *  Querying by the columns
     */

    @SqlQuery("SELECT DISTRICT,CROPNAME,ATTRIBUTE,QUANTITY,UPDATEDATE,CREATEDATE FROM NGOCROP WHERE ATTRIBUTE=:ATTRIBUTE")
    @MapResultAsBean
    public abstract Iterator<Ngocrop> getByAttribute(@Bind("ATTRIBUTE") String Attribute);

    @SqlQuery("SELECT DISTRICT,CROPNAME,ATTRIBUTE,QUANTITY,UPDATEDATE,CREATEDATE FROM NGOCROP")
    @MapResultAsBean
    public abstract Iterator<Ngocrop> getAll();

    @SqlQuery("SELECT DISTRICT,CROPNAME,ATTRIBUTE,QUANTITY,UPDATEDATE,CREATEDATE FROM NGOCROP WHERE DISTRICT=:DISTRICT")
    @MapResultAsBean
    public abstract Iterator<Ngocrop> getByDistrict(@Bind("DISTRICT") String District);

    @SqlQuery("SELECT DISTRICT,CROPNAME,ATTRIBUTE,QUANTITY,UPDATEDATE,CREATEDATE FROM NGOCROP WHERE UPDATEDATE=:UPDATEDATE")
    @MapResultAsBean
    public abstract Iterator<Ngocrop> getByUpdatedate(@Bind("UPDATEDATE") java.sql.Timestamp Updatedate);

    @SqlQuery("SELECT DISTRICT,CROPNAME,ATTRIBUTE,QUANTITY,UPDATEDATE,CREATEDATE FROM NGOCROP WHERE CREATEDATE=:CREATEDATE")
    @MapResultAsBean
    public abstract Iterator<Ngocrop> getByCreatedate(@Bind("CREATEDATE") java.sql.Timestamp Createdate);

    @SqlQuery("SELECT DISTRICT,CROPNAME,ATTRIBUTE,QUANTITY,UPDATEDATE,CREATEDATE FROM NGOCROP WHERE QUANTITY=:QUANTITY")
    @MapResultAsBean
    public abstract Iterator<Ngocrop> getByQuantity(@Bind("QUANTITY") Double Quantity);

    @SqlQuery("SELECT DISTRICT,CROPNAME,ATTRIBUTE,QUANTITY,UPDATEDATE,CREATEDATE FROM NGOCROP WHERE CROPNAME=:CROPNAME")
    @MapResultAsBean
    public abstract Iterator<Ngocrop> getByCropname(@Bind("CROPNAME") String Cropname);

    @SqlQuery("SELECT DISTRICT,CROPNAME,ATTRIBUTE,QUANTITY,UPDATEDATE,CREATEDATE FROM NGOCROP WHERE DISTRICT = :DISTRICT AND CROPNAME = :CROPNAME AND ATTRIBUTE = :ATTRIBUTE")
    @MapResultAsBean
    public abstract Ngocrop getByTPK(@Bind("DISTRICT") String DISTRICT,@Bind("CROPNAME") String CROPNAME,@Bind("ATTRIBUTE") String ATTRIBUTE);

    public Ngocrop getById(Ngocrop.NgocropPK id){
        return getByTPK(id.getDistrict(),id.getCropname(),id.getAttribute());
    }
    /**
     *  Providing Update capabilities
     */
    @SqlUpdate("update NGOCROP SET QUANTITY = :QUANTITY,UPDATEDATE = NOW()  WHERE DISTRICT = :DISTRICT AND CROPNAME = :CROPNAME AND ATTRIBUTE = :ATTRIBUTE")
    protected abstract void update(@Bind("DISTRICT") String DISTRICT,@Bind("CROPNAME") String CROPNAME,@Bind("ATTRIBUTE") String ATTRIBUTE,@Bind("QUANTITY") Double QUANTITY);

    public Ngocrop update(Ngocrop.NgocropPK uk, Ngocrop instance){
        if(!(uk.equals(instance))) throw new RuntimeException("Update/Delete key is invalid");
        update(instance.getDistrict(),instance.getCropname(),instance.getAttribute(),instance.getQuantity());
        return getById(new Ngocrop.NgocropPK(instance.getDistrict(),instance.getCropname(),instance.getAttribute()));
    }


    /**
     *  Providing delete capabilities
     */
    @SqlUpdate("delete From NGOCROP  WHERE DISTRICT=:DISTRICT AND CROPNAME=:CROPNAME AND ATTRIBUTE=:ATTRIBUTE AND QUANTITY=:QUANTITY AND UPDATEDATE=:UPDATEDATE AND CREATEDATE=:CREATEDATE")
    protected abstract void delete(@Bind("DISTRICT") String DISTRICT,@Bind("CROPNAME") String CROPNAME,@Bind("ATTRIBUTE") String ATTRIBUTE,@Bind("QUANTITY") Double QUANTITY,@Bind("UPDATEDATE") java.sql.Timestamp UPDATEDATE,@Bind("CREATEDATE") java.sql.Timestamp CREATEDATE);

    public Ngocrop delete(Ngocrop.NgocropPK uk,Ngocrop instance){
        if(!(uk.equals(instance))) throw new RuntimeException("Update/Delete key is invalid");

        delete(instance.getDistrict(),instance.getCropname(),instance.getAttribute(),instance.getQuantity(),instance.getUpdatedate(),instance.getCreatedate());
        return instance;
    }

    public Ngocrop delete(final Ngocrop.NgocropPK id){
        Ngocrop instance = getById(id);
        if(null==instance)return null;
        delete(new Ngocrop.NgocropPK(instance.getDistrict(),instance.getCropname(),instance.getAttribute()),instance);
        return instance;
    }
}
