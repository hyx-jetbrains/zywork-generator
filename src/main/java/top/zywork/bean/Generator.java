package top.zywork.bean;

/**
 * Generator配置信息的封装类<br/>
 *
 * 创建于2018-03-22<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class Generator {

    // 字符编码
    private String charset;
    // Java作者名
    private String author;
    // 数据表前缀
    private String tablePrefix;
    // 生成代码保存根路径
    private String saveBaseDir;
    // Java代码保存路径
    private String javaSrcDir;
    // 资源保存路径
    private String resourceDir;
    // base包名称
    private String basePackage;
    // do包名称
    private String doPackage;
    // dto包名称
    private String dtoPackage;
    // vo包名称
    private String voPackage;
    // query包名称
    private String queryPackage;
    // dao包名称
    private String daoPackage;
    // service包名称
    private String servicePackage;
    // service实现包名称
    private String serviceImplPackage;
    // controller包名称
    private String controllerPackage;
    // 映射文件存储目录
    private String mapperDir;
    // do类后缀
    private String doSuffix;
    // dto类后缀
    private String dtoSuffix;
    // vo类后缀
    private String voSuffix;
    // query类后缀
    private String querySuffix;
    // dao接口后缀
    private String daoSuffix;
    // service接口后缀
    private String serviceSuffix;
    // service实现类后缀
    private String serviceImplSuffix;
    // controller类后缀
    private String controllerSuffix;
    // 映射文件后缀
    private String mapperSuffix;
    // js文件保存路径
    private String jsFileDir;
    // 视图文件保存路径
    private String viewFileDir;
    // 排除不需要添加和修改的字段
    private String exclusiveAddEditColumns;

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getSaveBaseDir() {
        return saveBaseDir;
    }

    public void setSaveBaseDir(String saveBaseDir) {
        this.saveBaseDir = saveBaseDir;
    }

    public String getJavaSrcDir() {
        return javaSrcDir;
    }

    public void setJavaSrcDir(String javaSrcDir) {
        this.javaSrcDir = javaSrcDir;
    }

    public String getResourceDir() {
        return resourceDir;
    }

    public void setResourceDir(String resourceDir) {
        this.resourceDir = resourceDir;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getDoPackage() {
        return doPackage;
    }

    public void setDoPackage(String doPackage) {
        this.doPackage = doPackage;
    }

    public String getDtoPackage() {
        return dtoPackage;
    }

    public void setDtoPackage(String dtoPackage) {
        this.dtoPackage = dtoPackage;
    }

    public String getVoPackage() {
        return voPackage;
    }

    public void setVoPackage(String voPackage) {
        this.voPackage = voPackage;
    }

    public String getQueryPackage() {
        return queryPackage;
    }

    public void setQueryPackage(String queryPackage) {
        this.queryPackage = queryPackage;
    }

    public String getDaoPackage() {
        return daoPackage;
    }

    public void setDaoPackage(String daoPackage) {
        this.daoPackage = daoPackage;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public String getServiceImplPackage() {
        return serviceImplPackage;
    }

    public void setServiceImplPackage(String serviceImplPackage) {
        this.serviceImplPackage = serviceImplPackage;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public void setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
    }

    public String getMapperDir() {
        return mapperDir;
    }

    public void setMapperDir(String mapperDir) {
        this.mapperDir = mapperDir;
    }

    public String getDoSuffix() {
        return doSuffix;
    }

    public void setDoSuffix(String doSuffix) {
        this.doSuffix = doSuffix;
    }

    public String getDtoSuffix() {
        return dtoSuffix;
    }

    public void setDtoSuffix(String dtoSuffix) {
        this.dtoSuffix = dtoSuffix;
    }

    public String getVoSuffix() {
        return voSuffix;
    }

    public void setVoSuffix(String voSuffix) {
        this.voSuffix = voSuffix;
    }

    public String getQuerySuffix() {
        return querySuffix;
    }

    public void setQuerySuffix(String querySuffix) {
        this.querySuffix = querySuffix;
    }

    public String getDaoSuffix() {
        return daoSuffix;
    }

    public void setDaoSuffix(String daoSuffix) {
        this.daoSuffix = daoSuffix;
    }

    public String getServiceSuffix() {
        return serviceSuffix;
    }

    public void setServiceSuffix(String serviceSuffix) {
        this.serviceSuffix = serviceSuffix;
    }

    public String getServiceImplSuffix() {
        return serviceImplSuffix;
    }

    public void setServiceImplSuffix(String serviceImplSuffix) {
        this.serviceImplSuffix = serviceImplSuffix;
    }

    public String getControllerSuffix() {
        return controllerSuffix;
    }

    public void setControllerSuffix(String controllerSuffix) {
        this.controllerSuffix = controllerSuffix;
    }

    public String getMapperSuffix() {
        return mapperSuffix;
    }

    public void setMapperSuffix(String mapperSuffix) {
        this.mapperSuffix = mapperSuffix;
    }

    public String getJsFileDir() {
        return jsFileDir;
    }

    public void setJsFileDir(String jsFileDir) {
        this.jsFileDir = jsFileDir;
    }

    public String getViewFileDir() {
        return viewFileDir;
    }

    public void setViewFileDir(String viewFileDir) {
        this.viewFileDir = viewFileDir;
    }

    public String getExclusiveAddEditColumns() {
        return exclusiveAddEditColumns;
    }

    public void setExclusiveAddEditColumns(String exclusiveAddEditColumns) {
        this.exclusiveAddEditColumns = exclusiveAddEditColumns;
    }

}
