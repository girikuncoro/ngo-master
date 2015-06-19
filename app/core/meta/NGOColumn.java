package core.meta;

public class NGOColumn {
    private String columnName;
    private String columnType;
    private int csvType;

    public NGOColumn(String columnName, String columnType, int csvType) {
        this.columnName = columnName;
        this.columnType = columnType;
        this.csvType = csvType;
    }

    public NGOColumn() {

    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public int getCsvType() {
        return csvType;
    }

    public void setCsvType(int csvType) {
        this.csvType = csvType;
    }
}
