package top.zywork.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 关联表代码生成时所需要的信息<br/>
 *
 * 创建于2018-09-28<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinInfo {

    /**
     * bean名称
     */
    private String beanName;
    /**
     * request url mapping
     */
    private String requestMapping;
    /**
     * 选中的数据表名称
     */
    private String[] tables;
    /**
     * 选择的主表名称
     */
    private String primaryTable;
    /**
     * where 条件关联语句
     */
    private String whereClause;
    /**
     * 选中的数据表字段，组合方式为:表名称::字段名称，如t_user::user_id
     */
    private String[] columns;
    /**
     * 需要生成的代码类型
     */
    private String[] codeTypes;

    /**
     * 覆盖已经生成的代码
     */
    private String overrideCodes;

}
