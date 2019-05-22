package top.zywork.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 列相关信息的封装类<br/>
 *
 * 创建于2018-03-12<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ColumnDetail {

    /**
     * 字段名称
     */
    private String name;
    /**
     * 字段对应的属性名称
     */
    private String fieldName;
    /**
     * 字段注释
     */
    private String comment;
    /**
     * 字段注释详细说明
     */
    private String commentDetail;
    /**
     * 字段的SQL类型，int
     */
    private Integer type;
    /**
     * 字段的JDBC类型名称
     */
    private String jdbcTypeName;
    /**
     * 字段对应属性的Java类型名称
     */
    private String javaTypeName;
    /**
     * 字段的size
     */
    private Integer columnSize;
    /**
     * 字段是否可为空，直接参考DatabaseMetaData中的常量值
     */
    private Integer nullable;

}
