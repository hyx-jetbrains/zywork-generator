package top.zywork.common;

import lombok.extern.slf4j.Slf4j;
import top.zywork.bean.ColumnDetail;
import top.zywork.bean.TableColumns;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JDBC元数据工具类<br/>
 *
 * 创建于2018-03-12<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
@Slf4j
public class JDBCUtils {

    private Connection connection;

    /**
     * 连接数据库
     * @param driverClassName 驱动程序
     * @param url 连接url
     * @param username 用户名
     * @param password 密码
     * @return 连接成功返回true，连接失败返回false
     */
    public boolean connect(String driverClassName, String url, String username, String password) {
        try {
            Class.forName(driverClassName);
            this.connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    /**
     * 获取所有表的表名和字段信息
     * @return 表的表名和字段信息组成的对象的Map集合，key为表名称
     */
    public Map<String, TableColumns> getTableColumns() {
        Map<String, TableColumns> tableColumnsMap = new HashMap<>(10);
        try {
            // 获取连接元信息
            DatabaseMetaData metaData = connection.getMetaData();
            try (
                    // 根据元信息获取所有数据表结果集
                    ResultSet tableResultSet = metaData.getTables(connection.getCatalog(), null, null, new String[] {"TABLE"})
            ) {
                // 对所有数据表结果集进行循环，获取每一个表中的所有字段信息
                while (tableResultSet.next()) {
                    TableColumns tableColumns = new TableColumns();
                    tableColumns.setTableName(tableResultSet.getString("TABLE_NAME"));
                    getColumnDetails(metaData, tableColumns.getTableName(), tableColumns);
                    tableColumnsMap.put(tableColumns.getTableName(), tableColumns);
                }
            }
        } catch (SQLException e) {
            log.error("get table data error, {}", e.getMessage());
        }
        return tableColumnsMap;
    }

    /**
     * 获取所有表的表名称
     * @return 所有表的表名称组成的List集合
     */
    public List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            try (ResultSet tableResultSet = metaData.getTables(connection.getCatalog(), null, null, new String[] {"TABLE"})) {
                while (tableResultSet.next()) {
                    tableNames.add(tableResultSet.getString("TABLE_NAME"));
                }
            }
        } catch (SQLException e) {
            log.error("get table data error, {}", e.getMessage());
        }
        return tableNames;
    }

    /**
     * 从指定的数据表获取所有的列
     * @param metaData 数据库元数据
     * @param table 表名
     * @return 表的字段封装的信息
     */
    public void getColumnDetails(DatabaseMetaData metaData, String table, TableColumns tableColumns) {
        Map<String, ColumnDetail> columnDetailMap = new HashMap<>(10);
        List<ColumnDetail>  columnDetailList = new ArrayList<>();
        try (ResultSet columnResultSet = metaData.getColumns(connection.getCatalog(), getSchema(), table, "%")) {
            while (columnResultSet.next()) {
                ColumnDetail columnDetail = new ColumnDetail();
                columnDetail.setName(columnResultSet.getString("COLUMN_NAME"));
                columnDetail.setType(columnResultSet.getInt("DATA_TYPE"));
                columnDetail.setJdbcTypeName(columnResultSet.getString("TYPE_NAME"));
                columnDetail.setJavaTypeName(getJavaType(columnDetail.getJdbcTypeName()));
                columnDetail.setComment(columnResultSet.getString("REMARKS"));
                columnDetail.setFieldName(PropertyUtils.columnToProperty(columnDetail.getName()));
                columnDetail.setColumnSize(columnResultSet.getInt("COLUMN_SIZE"));
                columnDetail.setNullable(columnResultSet.getInt("NULLABLE"));
                columnDetailMap.put(columnDetail.getName(), columnDetail);
                columnDetailList.add(columnDetail);
            }
            tableColumns.setColumnDetailMap(columnDetailMap);
            tableColumns.setColumnDetailList(columnDetailList);
        } catch (SQLException e) {
            log.error("get columns error: {}", e.getMessage());
        }
    }

    /**
     * 获取schema
     * @return schema
     */
    public String getSchema() {
        try {
            return connection.getMetaData().getUserName();
        } catch (SQLException e) {
            log.error(e.getMessage());
            return null;
        }
    }

    /**
     * 获取字段对应的Java类型的名称
     * @param jdbcType
     * @return
     */
    public String getJavaType(String jdbcType) {
        switch(jdbcType){
            case "VARCHAR":
            case "VARCHAR2":
            case "CHAR":
            case "TEXT":
                return "String";
            case "NUMBER":
            case "DECIMAL":
                return "BigDecimal";
            case "INT":
            case "INTEGER":
                return "Integer";
            case "SMALLINT":
                return "Short";
            case "TINYINT":
                return "Byte";
            case "BIGINT":
                return "Long";
            case "DOUBLE":
                return "Double";
            case "FLOAT":
                return "Float";
            case "DATETIME":
            case "TIMESTAMP":
            case "DATE":
            case "TIME":
            case "YEAR":
                return "Date";
            default:
                return "String";
        }
    }

}
