package top.zywork.generator;

import org.springframework.util.StringUtils;
import top.zywork.bean.Generator;
import top.zywork.bean.TableColumn;
import top.zywork.common.DateFormatUtils;
import top.zywork.common.GeneratorUtils;
import top.zywork.constant.TemplateConstants;
import top.zywork.enums.DatePatternEnum;

import java.util.Calendar;

/**
 * Service自动生成代码封装类<br/>
 *
 * 创建于2018-03-12<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class ServiceGenerator {

    /**
     * 生成Service接口
     * @param generator
     * @param tableColumn 表数据
     */
    public static void generateService(Generator generator, TableColumn tableColumn) {
        String beanName = GeneratorUtils.tableNameToClassName(tableColumn.getTableName(), generator.getTablePrefix());
        generateJoinService(beanName, generator);
    }

    /**
     * 生成关联表的Service接口
     * @param beanName bean名称
     * @param generator Generator实例
     */
    public static void generateJoinService(String beanName, Generator generator) {
        String packagePath = GeneratorUtils.createPackage(generator, generator.getServicePackage());
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.SERVICE_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.CLASS_SUFFIX, generator.getServiceSuffix())
                .replace(TemplateConstants.CREATE_DATE, DateFormatUtils.format(Calendar.getInstance(), DatePatternEnum.DATE.getValue()))
                .replace(TemplateConstants.AUTHOR, generator.getAuthor());
        GeneratorUtils.writeFile(fileContent, packagePath, beanName + generator.getServiceSuffix()  + ".java");
    }

    /**
     * 生成Service接口实现类
     * @param generator
     * @param tableColumn 表数据
     */
    public static void generateServiceImpl(Generator generator, TableColumn tableColumn) {
        String beanName = GeneratorUtils.tableNameToClassName(tableColumn.getTableName(), generator.getTablePrefix());
        generateJoinServiceImpl(beanName, generator);
    }

    /**
     * 生成关联表的Service接口实现类
     * @param beanName bean名称
     * @param generator Generator实例
     */
    public static void generateJoinServiceImpl(String beanName, Generator generator) {
        String packagePath = GeneratorUtils.createPackage(generator, generator.getServiceImplPackage());
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.SERVICE_IMPL_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.CLASS_SUFFIX, generator.getServiceImplSuffix())
                .replace(TemplateConstants.SERVICE_SUFFIX, generator.getServiceSuffix())
                .replace(TemplateConstants.DAO_SUFFIX, generator.getDaoSuffix())
                .replace(TemplateConstants.DO_SUFFIX, generator.getDoSuffix())
                .replace(TemplateConstants.DTO_SUFFIX, generator.getDtoSuffix())
                .replace(TemplateConstants.BEAN_NAME_LOWER_CASE, StringUtils.uncapitalize(beanName))
                .replace(TemplateConstants.CREATE_DATE, DateFormatUtils.format(Calendar.getInstance(), DatePatternEnum.DATE.getValue()))
                .replace(TemplateConstants.AUTHOR, generator.getAuthor());

        GeneratorUtils.writeFile(fileContent, packagePath, beanName + generator.getServiceImplSuffix()  + ".java");
    }

}
