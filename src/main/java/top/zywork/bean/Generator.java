package top.zywork.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generator配置信息的封装类<br/>
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
public class Generator {

    /**
     * 字符编码
     */
    private String charset;
    /**
     * Java作者名
     */
    private String author;
    /**
     * 数据表前缀
     */
    private String tablePrefix;
    /**
     * 生成代码保存根路径
     */
    private String saveBaseDir;
    /**
     * Java代码保存路径
     */
    private String javaSrcDir;
    /**
     * 资源保存路径
     */
    private String resourceDir;
    /**
     * base包名称
     */
    private String basePackage;
    /**
     * do包名称
     */
    private String doPackage;
    /**
     * dto包名称
     */
    private String dtoPackage;
    /**
     * vo包名称
     */
    private String voPackage;
    /**
     * query包名称
     */
    private String queryPackage;
    /**
     * dao包名称
     */
    private String daoPackage;
    /**
     * service包名称
     */
    private String servicePackage;
    /**
     * service实现包名称
     */
    private String serviceImplPackage;
    /**
     * controller包名称
     */
    private String controllerPackage;
    /**
     * mapper映射文件存储目录
     */
    private String mapperDir;
    /**
     * do类后缀
     */
    private String doSuffix;
    /**
     * dto类后缀
     */
    private String dtoSuffix;
    /**
     * vo类后缀
     */
    private String voSuffix;
    /**
     * query类后缀
     */
    private String querySuffix;
    /**
     * dao接口后缀
     */
    private String daoSuffix;
    /**
     * service接口后缀
     */
    private String serviceSuffix;
    /**
     * service实现类后缀
     */
    private String serviceImplSuffix;
    /**
     * controller类后缀
     */
    private String controllerSuffix;
    /**
     * 映射文件后缀
     */
    private String mapperSuffix;
    /**
     * js文件保存路径
     */
    private String jsFileDir;
    /**
     * 视图文件保存路径
     */
    private String viewFileDir;
    /**
     * 排除不需要添加和修改的字段
     */
    private String exclusiveAddEditColumns;

}
