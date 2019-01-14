package top.zywork.common;

import top.zywork.bean.Generator;
import top.zywork.constant.TemplateConstants;

import java.io.File;

/**
 * 代码自动生成工具类<br/>
 *
 * 创建于2018-03-12<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class GeneratorUtils {

    /**
     * 读取代码模板文件
     * @param templateFile 模板文件路径
     * @return 模板文件的字符串内容
     */
    public static String readTemplate(Generator generator, String templateFile) {
        return IOUtils.getText(FileUtils.getResourcePath(TemplateConstants.TEMPLATE_FILE_DIR + templateFile), generator.getCharset());
    }

    /**
     * 在代码保存目录中创建Java代码的基础package
     */
    public static String createBasePackage(Generator generator) {
        String packagePath = generator.getSaveBaseDir() + File.separator + generator.getJavaSrcDir()
                + File.separator + generator.getBasePackage();
        return FileUtils.mkdirs(packagePath.replace(".", File.separator));
    }

    /**
     * 在基础package中创建指定的包
     * @param packageName 包名称，可以是.分割的包名称
     * @return 创建好的包对应的目录的绝对路径
     */
    public static String createPackage(Generator generator, String packageName) {
        return FileUtils.mkdirs(createBasePackage(generator), packageName.replace(".", File.separator));
    }

    /**
     * 在资源保存目录中创建子目录
     * @param resDirName 资源目录名称
     * @return 创建的资源目录的绝对路径
     */
    public static String createResDir(Generator generator, String resDirName) {
        return FileUtils.mkdirs(generator.getSaveBaseDir() + File.separator
                + generator.getResourceDir() + File.separator + resDirName);
    }

    /**
     * 写出文件
     * @param fileContent 文件内容，字符串
     * @param path 文件路径
     * @param fileName 文件名
     */
    public static void writeFile(String fileContent, String path, String fileName) {
        IOUtils.writeText(fileContent, path + File.separator + fileName);
    }

    /**
     * 通过表名称获取与表对应实体类的类名称
     * @param tableName 表名称
     * @param prefix 表前缀
     * @return 表对应的实体类的类名称
     */
    public static String tableNameToClassName(String tableName, String prefix) {
        StringBuilder className = new StringBuilder("");
        String[] strArray = tableName.split("_");
        int startIndex = prefix == null ? 0 : 1;
        for (int i = startIndex, len = strArray.length; i < len; i++) {
            className.append(org.springframework.util.StringUtils.capitalize(strArray[i]));
        }
        return className.toString();
    }

    /**
     * 通过表名称获取Controller中的模块名，如user_social_type对应于user-social-type
     * @param tableName 表名称
     * @param prefix 表前缀
     * @return 表名称对应的Controller模块名
     */
    public static String getModuleName(String tableName, String prefix) {
        return tableName.replace(prefix, "").replace("_", "-");
    }

}
