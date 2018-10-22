package top.zywork.bean;

import java.util.List;

/**
 * 数据表与数据表所有字段的封装类<br/>
 *
 * 创建于2018-03-12<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class TableColumn {

    private String tableName;
    private List<ColumnDetail> columnDetails;

    public TableColumn() {}

    public TableColumn(String tableName, List<ColumnDetail> columnDetails) {
        this.tableName = tableName;
        this.columnDetails = columnDetails;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<ColumnDetail> getColumnDetails() {
        return columnDetails;
    }

    public void setColumnDetails(List<ColumnDetail> columnDetails) {
        this.columnDetails = columnDetails;
    }
}
