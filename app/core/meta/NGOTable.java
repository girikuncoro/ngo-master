package core.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * The type NGO table.
 */
public class NGOTable {
    private List<NGOColumn> columnList;
    private List<String> constraints;

    /**
     * Instantiates a new NGO table.
     *
     * @param columnList  the column list
     * @param constraints the constraints
     */
    public NGOTable(List<NGOColumn> columnList, List<String> constraints) {
        this.columnList = columnList;
        this.constraints = constraints;
    }

    /**
     * Instantiates a new NGO table.
     */
    public NGOTable() {
        this.columnList = new ArrayList();
        this.constraints = new ArrayList();

    }

    /**
     * Gets column list.
     *
     * @return the column list
     */
    public List<NGOColumn> getColumnList() {
        return columnList;
    }

    /**
     * Sets column list.
     *
     * @param columnList the column list
     */
    public void setColumnList(List<NGOColumn> columnList) {
        this.columnList = columnList;
    }

    /**
     * Gets constraints.
     *
     * @return the constraints
     */
    public List<String> getConstraints() {
        return constraints;
    }

    /**
     * Sets constraints.
     *
     * @param constraints the constraints
     */
    public void setConstraints(List<String> constraints) {
        this.constraints = constraints;
    }


}
