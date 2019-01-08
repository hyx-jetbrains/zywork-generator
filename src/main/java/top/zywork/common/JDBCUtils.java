package top.zywork.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zywork.bean.ColumnDetail;
import top.zywork.bean.TableColumn;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC元数据工具类<br/>
 *
 * 创建于2018-03-12<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class JDBCUtils {

    private static final Logger logger = LoggerFactory.getLogger(JDBCUtils.class);

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
            logger.error(e.getMessage());
            return false;
        }
    }

    /**
     * 获取所有表的表名和字段信息
     * @return 表的表名和字段信息组成的对象的List集合
     */
    public List<TableColumn> getTableColumns() {
        List<TableColumn> tableColumnList = new ArrayList<>();
        ResultSet tableResultSet = null;
        try {
            // 获取连接元信息
            DatabaseMetaData metaData = connection.getMetaData();
            // 根据元信息获取所有数据表
            tableResultSet = metaData.getTables(connection.getCatalog(), null, null, new String[] {"TABLE"});
            // 对所有数据表进行循环，获取每一个表中的所有字段信息
            while (tableResultSet.next()) {
                TableColumn tableColumn = new TableColumn();
                tableColumn.setTableName(tableResultSet.getString("TABLE_NAME"));
                List<ColumnDetail> columnDetails = getColumnDetails(tableColumn.getTableName());
                tableColumn.setColumnDetails(columnDetails);
                tableColumnList.add(tableColumn);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (tableResultSet != null && !tableResultSet.isClosed()) {
                    tableResultSet.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }

        }
        return tableColumnList;
    }

    /**
     * 获取所有表的表名称
     * @return 所有表的表名称组成的List集合
     */
    public List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        ResultSet tableResultSet = null;
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            tableResultSet = metaData.getTables(connection.getCatalog(), null, null, new String[] {"TABLE"});
            while (tableResultSet.next()) {
                tableNames.add(tableResultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (tableResultSet != null && !tableResultSet.isClosed()) {
                    tableResultSet.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return tableNames;
    }

    /**
     * 从指定的数据表获取所有的列
     * @param table 表名
     * @return 表的字段封装的信息
     */
    public List<ColumnDetail> getColumnDetails(String table) {
        List<ColumnDetail> columnDetails = new ArrayList<>();
        ResultSet columnResultSet = null;
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            columnResultSet = metaData.getColumns(connection.getCatalog(), getSchema(), table, "%");
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
                columnDetails.add(columnDetail);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (columnResultSet != null && !columnResultSet.isClosed()) {
                    columnResultSet.close();
                }
            } catch (SQLException e) {
                logger.error(e.getMessage());
            }
        }
        return columnDetails;
    }

    /**
     * 获取schema
     * @return schema
     */
    public String getSchema() {
        try {
            return connection.getMetaData().getUserName();
        } catch (SQLException e) {
            logger.error(e.getMessage());
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
