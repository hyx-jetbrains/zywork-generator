package top.zywork.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 与iview的select对应的类<br/>
 * 创建于2018-07-13<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IViewSelect {

    private String value;
    private String label;
    private Boolean disabled;

}
