package top.zywork.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 属性信息的封装类<br/>
 *
 * 创建于2018-03-22<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FieldDetail {

    /**
     * 属性英文名
     */
    private String name;
    /**
     * 属性Java类型名称
     */
    private String javaType;
    /**
     * 属性中文名
     */
    private String nameCN;

}
