package top.zywork.generator;

import top.zywork.bean.Generator;
import top.zywork.bean.TableColumn;
import top.zywork.common.StringUtils;

import java.util.List;

/**
 * 实体类，DAO, Mapper, Service, Controller全部代码自动生成封装类<br/>
 *
 * 创建于2018-03-12<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class CodeGenerator {

    /**
     * 根据Generator和单表的字段信息生成指定的一张表的代码
     * @param generator Generator实例
     * @param tableColumn 单个表的字段信息
     */
    public static void generateCode(Generator generator, TableColumn tableColumn, String[] codeTypes) {
        if (StringUtils.isInArray(codeTypes, "bean")) {
            BeanGenerator.generateBean(generator, tableColumn, BeanGenerator.DO_BEAN);
            BeanGenerator.generateBean(generator, tableColumn, BeanGenerator.DTO_BEAN);
            BeanGenerator.generateBean(generator, tableColumn, BeanGenerator.VO_BEAN);
            BeanGenerator.generateBean(generator, tableColumn, BeanGenerator.QUERY_BEAN);
        }
        if (StringUtils.isInArray(codeTypes, "dao")) {
            DAOGenerator.generateDAO(generator, tableColumn);
        }
        if (StringUtils.isInArray(codeTypes, "mapper")) {
            MapperGenerator.generateMapper(generator, tableColumn);
        }

        if (StringUtils.isInArray(codeTypes, "service")) {
            ServiceGenerator.generateService(generator, tableColumn);
            ServiceGenerator.generateServiceImpl(generator, tableColumn);
        }

        if (StringUtils.isInArray(codeTypes, "controller")) {
            ControllerGenerator.generateController(generator, tableColumn);
        }

        if (StringUtils.isInArray(codeTypes, "view")) {
            ViewGenerator.generateView(generator, tableColumn);
        }

        if (StringUtils.isInArray(codeTypes, "selectView")) {
            ViewGenerator.generateSelectView(generator, tableColumn);
        }
    }

    /**
     * 生成关联表的代码
     * @param beanName bean名称
     * @param mappingUrl 控制器映射url
     * @param generator Generator实例
     * @param primaryTable 主表名称
     * @param columns 所选表字段
     * @param joinWhereClause 关联条件
     */
    public static void generateJoinCode(String beanName, String mappingUrl, Generator generator, String[] tables, String primaryTable,
                                        String[] columns, List<TableColumn> tableColumnList, String joinWhereClause, String[] codeTypes) {
        if (StringUtils.isInArray(codeTypes, "bean")) {
            BeanGenerator.generateJoinBean(beanName, generator, primaryTable, columns, tableColumnList, BeanGenerator.DO_BEAN);
            BeanGenerator.generateJoinBean(beanName, generator, primaryTable, columns, tableColumnList, BeanGenerator.DTO_BEAN);
            BeanGenerator.generateJoinBean(beanName, generator, primaryTable, columns, tableColumnList, BeanGenerator.VO_BEAN);
            BeanGenerator.generateJoinBean(beanName, generator, primaryTable, columns, tableColumnList, BeanGenerator.QUERY_BEAN);
        }

        if (StringUtils.isInArray(codeTypes, "dao")) {
            DAOGenerator.generateJoinDAO(beanName, generator);
        }
        if (StringUtils.isInArray(codeTypes, "mapper")) {
            MapperGenerator.generateJoinMapper(beanName, generator, tables, primaryTable, columns, joinWhereClause);
        }

        if (StringUtils.isInArray(codeTypes, "service")) {
            ServiceGenerator.generateJoinService(beanName, generator);
            ServiceGenerator.generateJoinServiceImpl(beanName, generator);
        }
        if (StringUtils.isInArray(codeTypes, "controller")) {
            ControllerGenerator.generateJoinController(beanName, mappingUrl, generator);
        }
        if (StringUtils.isInArray(codeTypes, "view")) {
            ViewGenerator.generateJoinView(beanName, mappingUrl, generator, primaryTable, columns, tableColumnList);
        }
    }

}
