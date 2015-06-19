package core.meta;

import java.sql.Types;
import java.util.*;

/**
 * Created by nhosgur on 8/9/14.
 */
public class NGOMetaInfo {
    public static final String   NGO_DISTRICT_TABLE_NAME        = "ngodistrict";
    public static final String   NGO_CROP_TABLE_NAME            = "ngocrop";
    public static  final String  NGOREGISTRATION_TABLE_NAME     = "ngoregistration";
    public static final String   NGO_MEETING_TABLE_NAME         = "ngomeeting";
    public static  final String  NGORERSVP_TABLE_NAME           = "ngorsvp";
    public static  final String  NGOACCOUNT_TABLE_NAME          = "ngoaccount";




    private static final String[][] NGO_DISTRICT_TABLE_COLUMNS = {
            {"DISTRICT","varchar(255) PRIMARY KEY",""+Types.VARCHAR},
            {"CREATEDATE","TIMESTAMP",""+Types.TIMESTAMP},
            {"UPDATEDATE","TIMESTAMP",""+Types.TIMESTAMP}
    };

    private static final String[][] NGO_CROP_TABLE_COLUMNS = {
            {"DISTRICT","varchar(255)",""+Types.VARCHAR},
            {"CROPNAME","varchar(255)",""+Types.VARCHAR},
            {"ATTRIBUTE","varchar(255)",""+Types.VARCHAR},
            {"QUANTITY","DOUBLE",""+Types.DOUBLE},
            {"CREATEDATE","TIMESTAMP",""+Types.TIMESTAMP},
            {"UPDATEDATE","TIMESTAMP,PRIMARY KEY (DISTRICT,CROPNAME,ATTRIBUTE)," +
                    "FOREIGN KEY (DISTRICT) REFERENCES ngodistrict(DISTRICT)",""+Types.TIMESTAMP}
    };


    private static final String[][] NGOREGISTRATION_TABLE_COLUMNS ={
            {"DISTRICT","varchar(256)", ""+Types.VARCHAR},
            {"PHONENUMBER","varchar(32) primary key", ""+Types.VARCHAR},
            {"UPDATEDATE","TIMESTAMP",""+Types.TIMESTAMP},
            {"CREATEDATE","TIMESTAMP, " +
                    "FOREIGN KEY (DISTRICT) REFERENCES ngodistrict(DISTRICT)",""+Types.TIMESTAMP}
    };


    private static final String[][] NGO_MEETING_TABLE_COLUMNS ={
            {"MEETINGID","varchar(10) primary key", ""+Types.VARCHAR},
            {"NOTE","varchar(500)", ""+Types.VARCHAR},
            {"CREATEDBY","varchar(500)",""+Types.VARCHAR},
            {"DISTRICT","varchar(32)", ""+Types.VARCHAR},
            {"MEETINGDATE","TIMESTAMP",""+Types.TIMESTAMP},
            {"DURATION","DOUBLE",""+Types.DOUBLE},
            {"STATUS","varchar(50)", ""+Types.VARCHAR},
            {"UPDATEDATE","TIMESTAMP",""+Types.TIMESTAMP},
            {"CREATEDATE","TIMESTAMP, " +
                    "FOREIGN KEY (DISTRICT) REFERENCES ngodistrict(DISTRICT)",""+Types.TIMESTAMP}
    };

    private static final String[][] NGORERSVP_TABLE_COLUMNS ={
            {"MEETINGID","varchar(10) ", ""+Types.VARCHAR},
            {"RSVRESPONSE","varchar(50)", ""+Types.VARCHAR},
            {"RSVNUMBER","varchar(50)", ""+Types.VARCHAR},
            {"UPDATEDATE","TIMESTAMP",""+Types.TIMESTAMP},
            {"CREATEDATE","TIMESTAMP, " +
                    "FOREIGN KEY (MEETINGID) REFERENCES ngomeeting(MEETINGID), " +
                    "PRIMARY KEY (MEETINGID, RSVNUMBER)",""+Types.TIMESTAMP}
    };

    private static final String[][] NGOACCOUNT_TABLE_COLUMNS ={
            {"EMAILADDRESS","varchar(128) primary key", ""+Types.VARCHAR},
            {"PHONENUMBER","varchar(50) NOT NULL", ""+Types.VARCHAR},
            {"USERNAME","varchar(128)", ""+Types.VARCHAR},
            {"LASTNAME","varchar(128)", ""+Types.VARCHAR},
            {"FIRSTNAME","varchar(128)", ""+Types.VARCHAR},
            {"SALT","varchar(512)", ""+Types.VARCHAR},
            {"DIGEST","varchar(512)", ""+Types.VARCHAR},
            {"UPDATEDATE","TIMESTAMP",""+Types.TIMESTAMP},
            {"CREATEDATE","TIMESTAMP, " +
                    "CONSTRAINT PHONE_NUMBER UNIQUE (PHONENUMBER)",""+Types.TIMESTAMP}
    };



    private static final List<String> NGO_DISTRICT_TABLE_CONSTRAINTS = Arrays.asList(new String[]{});
    private static final List<String> NGO_CROP_TABLE_CONSTRAINTS = Arrays.asList(new String[]{});

    private static final  List<String> NGOREGISTRATION_TABLE_CONSTRAINTS = Arrays.asList(new String[]{});


    private static final Map<String,NGOTable> tablesMetaInfo;
    private static final Map<String,String[][]> nameToColumns;
    private static final Map<String,List<String>> nameToConstraints;

    public static void main(String... args) {
    }


    static{
        nameToColumns = new HashMap<>();
        nameToConstraints = new HashMap();

        nameToColumns.put(NGO_DISTRICT_TABLE_NAME, NGO_DISTRICT_TABLE_COLUMNS);
        nameToColumns.put(NGO_CROP_TABLE_NAME, NGO_CROP_TABLE_COLUMNS);
        nameToColumns.put(NGOREGISTRATION_TABLE_NAME,NGOREGISTRATION_TABLE_COLUMNS);
        nameToColumns.put(NGO_MEETING_TABLE_NAME, NGO_MEETING_TABLE_COLUMNS);
        nameToColumns.put(NGORERSVP_TABLE_NAME,NGORERSVP_TABLE_COLUMNS);
        nameToColumns.put(NGOACCOUNT_TABLE_NAME,NGOACCOUNT_TABLE_COLUMNS);

        nameToConstraints.put(NGO_DISTRICT_TABLE_NAME, NGO_DISTRICT_TABLE_CONSTRAINTS);
        nameToConstraints.put(NGO_CROP_TABLE_NAME,NGO_CROP_TABLE_CONSTRAINTS);
        nameToConstraints.put(NGOREGISTRATION_TABLE_NAME,NGOREGISTRATION_TABLE_CONSTRAINTS);

        tablesMetaInfo = genTableSchemaInfos();

    }


    private static  Map<String,NGOTable> genTableSchemaInfos() {
        Map<String,NGOTable> tables = new TreeMap();
        for(String tn:nameToColumns.keySet()){
            List<NGOColumn> columns = new ArrayList();
            NGOTable t = new NGOTable();

            for(String[] col:nameToColumns.get(tn)){
                NGOColumn c = new NGOColumn();
                c.setColumnName(col[0]);
                c.setColumnType(col[1]);
                c.setCsvType(Integer.valueOf(col[2]));
                columns.add(c);
            }

            t.setColumnList(columns);
            t.setConstraints(nameToConstraints.get(tn));

            tables.put(tn,t);
        }

        return tables;
    }

    public static Map<String,NGOTable> getTablesMetoInfo(){
        return tablesMetaInfo;
    }


}
