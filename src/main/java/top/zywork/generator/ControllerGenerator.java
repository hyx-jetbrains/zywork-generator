package top.zywork.generator;

import org.apache.commons.lang3.StringUtils;
import top.zywork.bean.Generator;
import top.zywork.bean.TableColumns;
import top.zywork.common.DateFormatUtils;
import top.zywork.common.GeneratorUtils;
import top.zywork.constant.TemplateConstants;
import top.zywork.enums.DatePatternEnum;

import java.util.Calendar;

/**
 * 控制器自动生成代码封装类<br/>
 *
 * 创建于2018-03-12<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class ControllerGenerator {

    /**
     * 生成Controller类文件
     * @param generator
     * @param beanName
     * @param moduleName
     */
    public static void generateController(Generator generator, String beanName, String moduleName) {
        String packagePath = GeneratorUtils.createPackage(generator, generator.getControllerPackage());
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.CONTROLLER_TEMPLATE);
        generateController(generator, fileContent, packagePath, beanName, moduleName);
    }

    /**
     * 生成关联表的Controller
     * @param generator Generator实例
     * @param beanName bean名称
     * @param mappingUrl 控制器映射url
     */
    public static void generateJoinController(Generator generator, String beanName, String mappingUrl) {
        String packagePath = GeneratorUtils.createPackage(generator, generator.getControllerPackage());
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.JOIN_CONTROLLER_TEMPLATE);
        generateController(generator, fileContent, packagePath, beanName, mappingUrl);
    }

    private static void generateController(Generator generator, String fileContent, String packagePath, String beanName, String mappingUrl) {
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName).replace(TemplateConstants.MODULE_NAME, mappingUrl)
                .replace(TemplateConstants.BEAN_NAME_LOWER_CASE, StringUtils.uncapitalize(beanName))
                .replace(TemplateConstants.DTO_SUFFIX, generator.getDtoSuffix())
                .replace(TemplateConstants.VO_SUFFIX, generator.getVoSuffix())
                .replace(TemplateConstants.SERVICE_SUFFIX, generator.getServiceSuffix())
                .replace(TemplateConstants.QUERY_SUFFIX, generator.getQuerySuffix())
                .replace(TemplateConstants.CLASS_SUFFIX, generator.getControllerSuffix())
                .replace(TemplateConstants.CREATE_DATE, DateFormatUtils.format(Calendar.getInstance(), DatePatternEnum.DATE.getValue()))
                .replace(TemplateConstants.AUTHOR, generator.getAuthor());
        GeneratorUtils.writeFile(fileContent, packagePath, beanName + generator.getControllerSuffix()  + ".java");
    }

}
