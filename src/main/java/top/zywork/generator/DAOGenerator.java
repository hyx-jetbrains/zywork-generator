package top.zywork.generator;

import top.zywork.bean.Generator;
import top.zywork.bean.TableColumns;
import top.zywork.common.DateFormatUtils;
import top.zywork.common.GeneratorUtils;
import top.zywork.constant.TemplateConstants;
import top.zywork.enums.DatePatternEnum;

import java.util.Calendar;

/**
 * DAO自动生成代码封装类<br/>
 *
 * 创建于2018-03-12<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class DAOGenerator {

    /**
     * 生成DAO接口
     * @param generator
     * @param beanName
     */
    public static void generateDAO(Generator generator, String beanName) {
        generateJoinDAO(generator, beanName);
    }

    /**
     * 生成关联表的DAO接口
     * @param generator Generator实例
     * @param beanName bean名称
     */
    public static void generateJoinDAO(Generator generator, String beanName) {
        String packagePath = GeneratorUtils.createPackage(generator, generator.getDaoPackage());
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.DAO_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.CLASS_SUFFIX, generator.getDaoSuffix())
                .replace(TemplateConstants.CREATE_DATE, DateFormatUtils.format(Calendar.getInstance(), DatePatternEnum.DATE.getValue()))
                .replace(TemplateConstants.AUTHOR, generator.getAuthor());
        GeneratorUtils.writeFile(fileContent, packagePath, beanName + generator.getDaoSuffix() + ".java");
    }

}
