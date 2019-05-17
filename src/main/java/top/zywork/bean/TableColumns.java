package top.zywork.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * 数据表与数据表所有字段的封装类<br/>
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
public class TableColumns {

    /**
     * 表名称
     */
    private String tableName;
    /**
     * 字段名与ColumnDetail的map
     */
    private Map<String, ColumnDetail> columnDetailMap;

    /**
     * 表所有的字段
     */
    private List<ColumnDetail> columnDetailList;

}
