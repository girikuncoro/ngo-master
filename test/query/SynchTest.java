package query;

import dbaccess.dbojbects.dao.NgodistrictDao;
import dbaccess.dbojbects.dao.NgoregistrationDao;
import dbaccess.dbojbects.pojo.Ngodistrict;
import dbaccess.dbojbects.pojo.Ngoregistration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * Created by nhosgur on 8/11/14.
 */
@RunWith(MockitoJUnitRunner.class)
public class SynchTest {
    @Test
    public void doSomeTest(){

    }


    public static void main(String... args) {
        listTheTables();
    }
    public static void listTheTables(){
        Set<String> districts = new HashSet(Arrays.asList(new String[]{"sinjar","barru","pangkep","maros","takalar"}));

        NgodistrictDao dao = DBIHandlerManager.getDistrictDAO();
        for(String d:districts){
            Ngodistrict it = DistrictUtility.fetchDistrictByName(d);
            System.out.println(it);

        }
        NgoregistrationDao rdo = DBIHandlerManager.getRegistrationDAO();
        int n = 0;
        for(String d:districts){
            Iterator<Ngoregistration> it = NgoRegistrationUtility.getByDistrict(d);
            while(it.hasNext()){
                Ngoregistration f = it.next();
                assertTrue(districts.contains(f.getDistrict()));
            }
        }

    }

}
