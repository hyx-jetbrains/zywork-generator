package top.zywork.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * JDBC配置信息的封装类<br/>
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
public class JDBC {

    /**
     * 驱动程序名称
     */
    private String driverClassName;
    /**
     * 连接url
     */
    private String url;
    /**
     * 数据库用户名
     */
    private String username;
    /**
     * 数据库登录密码
     */
    private String password;

}
