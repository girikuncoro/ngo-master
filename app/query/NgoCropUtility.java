package query;

import dbaccess.dbojbects.pojo.Ngocrop;

import java.util.Iterator;

/**
 * Created by nhosgur on 1/6/15.
 */
public class NgoCropUtility {
    public static Iterator<Ngocrop> getByDistrict(String districtName){
        return DBIHandlerManager.getCropDAO().getByDistrict(districtName);
    }

    public static Iterator<Ngocrop> getAll(){
        return DBIHandlerManager.getCropDAO().getAll();
    }
}
