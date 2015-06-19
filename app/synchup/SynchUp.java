package synchup;

import core.meta.NGOColumn;
import core.meta.NGOMetaInfo;
import core.meta.NGOTable;
import org.apache.log4j.Logger;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.util.Calendar;

import static core.dbi.NGOConstants.*;
import static core.meta.NGOMetaInfo.*;

/**
 * Created by nhosgur on 8/8/14.
 */
//Do not make the class to be public that would cause some
// synchronization issues
class SynchUp {
    private static final Logger itsLogger = Logger.getLogger(SynchUp.class);
    //private static final Logger itsLogger = Logger.
    public static final String[] ORDERED_TABLE_NAMES =
            {NGO_DISTRICT_TABLE_NAME, NGO_CROP_TABLE_NAME, NGOREGISTRATION_TABLE_NAME, NGO_MEETING_TABLE_NAME, NGORERSVP_TABLE_NAME, NGOACCOUNT_TABLE_NAME};
    public static final String OPEN_BRACKED = "(";
    public static final String CLOSE_BRACKET = ")";


    public static final String CREATE_TABLE = "CREATE TABLE ";

    public static final String SELECT = " SELECT ";
    public static final String FROM = " FROM ";
    public static final String INSERT_INTO = "INSERT INTO ";

    public static final String FROM_CSV_READ = " FROM CSVREAD('";
    public static final String CALL_CSV_WRITE = "CALL CSVWRITE('";
    public static final String CLOSE_CSV_WRITE_SELECT1 = "', 'SELECT ";
    public static final String CLOSECSV_BRACKET = "');";
    public static final boolean isDBGenCompleted;
    private static final DBI dbi;
    private static final Handle handle;


    static {
        NGOMetaInfo.main();
        dbi = new DBI(DEF_DBI_PARAMS.getUrl(), DEF_DBI_PARAMS.getUser(), DEF_DBI_PARAMS.getPassword());
        handle = dbi.open();

        isDBGenCompleted = true;

        genTablesInDB();
        readFromCSVIntoTable();
        new SynchUpThread().start();

    }

    private static void genTablesInDB() {
        for (String tableName : ORDERED_TABLE_NAMES) {
            handle.execute(
                    CREATE_TABLE + " " + tableName + OPEN_BRACKED + buildColumnNameTypes(tableName) + CLOSE_BRACKET);
        }
        handle.commit();
    }

    private static String buildColumnNameTypes(String tn) {
        NGOTable tinfo = NGOMetaInfo.getTablesMetoInfo().get(tn);
        StringBuilder sb = new StringBuilder();
        for (NGOColumn colInfo : tinfo.getColumnList()) {
            sb.append(colInfo.getColumnName()).append(" ").append(colInfo.getColumnType()).append(",");
        }
        return sb.substring(0, sb.length() - ",".length()).toString();
    }

    /**
     * Build column names comma seperated.
     *
     * @param tn the tn
     * @return the string
     */
    public static String buildColumnNamesCommaSeperated(String tn) {
        NGOTable meta = getTablesMetoInfo().get(tn);
        if (null == meta)
            throw new IllegalArgumentException("Table with table name[" + tn + "]does not have meta data info");
        StringBuilder sb = new StringBuilder();
        for (NGOColumn cm : meta.getColumnList()) {
            sb.append(cm.getColumnName()).append(" ,");
        }
        return sb.substring(0, sb.length() - ",".length()).toString();
    }


    /**
     * Read from cSV into table.
     */
    public static void readFromCSVIntoTable() {
        try {
            for (String tn : ORDERED_TABLE_NAMES) {
                handle.execute(buildInsertIntoFromCSV(tn));
            }
        } catch (Throwable e) {
            itsLogger.error(e.getMessage(), e);
        }

    }

    private static String buildInsertIntoFromCSV(String tn) {
        String columns = buildColumnNamesCommaSeperated(tn);

        try {
            String resultToTest =
                    INSERT_INTO + " " + tn + " " + OPEN_BRACKED + " " + columns + CLOSE_BRACKET +
                            SELECT + columns + FROM_CSV_READ + ROOT + tn + CSV_EXTENXION + CLOSECSV_BRACKET;
            return resultToTest;
        } catch (Throwable e) {
            itsLogger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }


    }

    /**
     * Write into cSV from table.
     */
    public static void writeIntoCSVFromTable() {
        try {
            backItUp(ROOT);
            backItUp(ROOT + Calendar.getInstance().getTime() + "/");
        } catch (Throwable e) {
            itsLogger.error(e.getMessage(), e);
        }

    }

    private static void backItUp(String root) {
        try {
            for (String tn : ORDERED_TABLE_NAMES) {
                handle.execute(CALL_CSV_WRITE + root + tn + CSV_EXTENXION + CLOSE_CSV_WRITE_SELECT1
                        + buildColumnNamesCommaSeperated(tn) + FROM + " " + tn + CLOSECSV_BRACKET);
            }
        } catch (Throwable e) {
            itsLogger.error(e.getMessage(), e);
        }
    }

    /**
     * Do trigger.
     */
    public static void doTrigger() {

    }
}
