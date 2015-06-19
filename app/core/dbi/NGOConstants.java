package core.dbi;

import config.NGOConfig;

/**
 * Created by nhosgur on 8/8/14.
 */
public class NGOConstants {
    public static final Long SECOND = 1_000L;
    public static final Long MINUTE = 60 * SECOND;
    public static final Long TEN_MINUTES = 10 * MINUTE;
    public static final Long THIRTY_MINUTES = 30 * MINUTE;
    public static final Long HOUR = 60 * MINUTE;
    public static final Long DAY = 24 * HOUR;


    public static final String COMPOST = "compost";
    public static final String ROOT;

    static {
        String res = null;
        try {
            res = NGOConfig.NGO_ROOT_DIRECTORY();
        } catch (Throwable e) {
            e.printStackTrace();
            System.out.println(e.getCause());
        }
        if (null == res || res.trim().isEmpty()) {
            ROOT = System.getProperty("user.home") + "/NGORepo/";
        } else {
            ROOT = res;
        }

    }

    public static final String CSV_EXTENXION = ".csv";
    public static final String DISTRICT_NAME = "DISTRICT_NAME";
    public static final String NGO_QUERY = "NGO_QUERY";

    public static final DBIParams DEF_DBI_PARAMS = new DBIParams("jdbc:h2:mem:test", "username", "password");

    public static void main(String... aregs) {

    }

}
