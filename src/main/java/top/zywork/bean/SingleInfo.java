package top.zywork.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 单表代码生成时所需要的信息<br/>
 *
 * 创建于2018-10-18<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SingleInfo {

    /**
     * 选中的表名称
     */
    private String[] tables;
    /**
     * 需要生成的代码类型
     */
    private String[] codeTypes;

}
