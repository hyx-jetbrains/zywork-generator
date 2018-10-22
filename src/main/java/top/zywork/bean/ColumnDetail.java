package top.zywork.bean;

/**
 * 列相关信息的封装类<br/>
 *
 * 创建于2018-03-12<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class ColumnDetail {

    // 字段名称
    private String name;
    // 字段对应的属性名称
    private String fieldName;
    // 字段注释
    private String comment;
    // 字段的SQL类型，int
    private Integer type;
    // 字段的JDBC类型名称
    private String jdbcTypeName;
    // 字段对应属性的Java类型名称
    private String javaTypeName;
    // 字段的size
    private Integer columnSize;
    // 字段是否可为空，直接参考DatabaseMetaData中的常量值
    private Integer nullable;

    public ColumnDetail() {}

    public ColumnDetail(String name, String comment, Integer type, String jdbcTypeName, String javaTypeName, Integer columnSize, Integer nullable) {
        this.name = name;
        this.comment = comment;
        this.type = type;
        this.jdbcTypeName = jdbcTypeName;
        this.javaTypeName = javaTypeName;
        this.columnSize = columnSize;
        this.nullable = nullable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getJdbcTypeName() {
        return jdbcTypeName;
    }

    public void setJdbcTypeName(String jdbcTypeName) {
        this.jdbcTypeName = jdbcTypeName;
    }

    public String getJavaTypeName() {
        return javaTypeName;
    }

    public void setJavaTypeName(String javaTypeName) {
        this.javaTypeName = javaTypeName;
    }

    public Integer getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(Integer columnSize) {
        this.columnSize = columnSize;
    }

    public void setNullable(Integer nullable) {
        this.nullable = nullable;
    }

    public Integer getNullable() {
        return nullable;
    }
}
