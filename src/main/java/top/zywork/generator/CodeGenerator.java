package top.zywork.generator;

import top.zywork.bean.Generator;
import top.zywork.bean.TableColumns;
import top.zywork.common.GeneratorUtils;
import top.zywork.common.StringUtils;

import java.util.Map;

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
     * @param tableColumns 单个表的字段信息
     * @param codeTypes 代码类型
     */
    public static void generateCode(Generator generator, TableColumns tableColumns, String[] codeTypes) {
        if (StringUtils.isInArray(codeTypes, "bean")) {
            BeanGenerator.generateBean(generator, tableColumns, BeanGenerator.DO_BEAN);
            BeanGenerator.generateBean(generator, tableColumns, BeanGenerator.DTO_BEAN);
            BeanGenerator.generateBean(generator, tableColumns, BeanGenerator.VO_BEAN);
            BeanGenerator.generateBean(generator, tableColumns, BeanGenerator.QUERY_BEAN);
        }
        if (StringUtils.isInArray(codeTypes, "dao")) {
            DAOGenerator.generateDAO(generator, GeneratorUtils.tableNameToClassName(tableColumns.getTableName(), generator.getTablePrefix()));
        }
        if (StringUtils.isInArray(codeTypes, "mapper")) {
            MapperGenerator.generateMapper(generator, tableColumns);
        }
        if (StringUtils.isInArray(codeTypes, "service")) {
            ServiceGenerator.generateService(generator, GeneratorUtils.tableNameToClassName(tableColumns.getTableName(), generator.getTablePrefix()));
            ServiceGenerator.generateServiceImpl(generator, GeneratorUtils.tableNameToClassName(tableColumns.getTableName(), generator.getTablePrefix()));
        }
        if (StringUtils.isInArray(codeTypes, "controller")) {
            ControllerGenerator.generateController(generator,
                    GeneratorUtils.tableNameToClassName(tableColumns.getTableName(), generator.getTablePrefix()),
                    GeneratorUtils.getModuleName(tableColumns.getTableName(), generator.getTablePrefix()));
        }
        String beanName = GeneratorUtils.tableNameToClassName(tableColumns.getTableName(), generator.getTablePrefix());
        String mappingUrl = GeneratorUtils.getModuleName(tableColumns.getTableName(), generator.getTablePrefix());
        if (StringUtils.isInArray(codeTypes, "view")) {
            ViewGenerator.generateViewMain(generator, beanName, mappingUrl);
            ViewGenerator.generateViewTable(generator, beanName, mappingUrl, tableColumns);
            ViewGenerator.generateViewAddEdit(generator, beanName, mappingUrl, tableColumns);
            ViewGenerator.generateViewDetail(generator, beanName, mappingUrl, tableColumns);
            ViewGenerator.generateViewSearch(generator, beanName, mappingUrl, tableColumns);
        }
        if (StringUtils.isInArray(codeTypes, "selectView")) {
            ViewGenerator.generateViewMultiple(generator, beanName, mappingUrl);
            ViewGenerator.generateViewTableMultiple(generator, beanName, mappingUrl, tableColumns);
            ViewGenerator.generateViewSingle(generator, beanName, mappingUrl);
            ViewGenerator.generateViewTableSingle(generator, beanName, mappingUrl, tableColumns);
        }
        if (StringUtils.isInArray(codeTypes, "showView")) {
            ViewGenerator.generateViewShow(generator, beanName, mappingUrl);
            ViewGenerator.generateViewTableShow(generator, beanName, mappingUrl, tableColumns);
        }
    }

    /**
     * 生成关联表的代码
     * @param beanName bean名称
     * @param mappingUrl 控制器映射url
     * @param generator Generator实例
     * @param tables 选择的数据表
     * @param primaryTable 主表名称
     * @param columns 所选表字段
     * @param tableColumnsMap 所有数据表信息map
     * @param joinWhereClause 关联条件
     * @param codeTypes 代码类型
     */
    public static void generateJoinCode(String beanName, String mappingUrl, Generator generator, String[] tables, String primaryTable,
                                        String[] columns, Map<String, TableColumns> tableColumnsMap, String joinWhereClause, String[] codeTypes) {
        if (StringUtils.isInArray(codeTypes, "bean")) {
            BeanGenerator.generateJoinBean(beanName, generator, primaryTable, columns, tableColumnsMap, BeanGenerator.DO_BEAN);
            BeanGenerator.generateJoinBean(beanName, generator, primaryTable, columns, tableColumnsMap, BeanGenerator.DTO_BEAN);
            BeanGenerator.generateJoinBean(beanName, generator, primaryTable, columns, tableColumnsMap, BeanGenerator.VO_BEAN);
            BeanGenerator.generateJoinBean(beanName, generator, primaryTable, columns, tableColumnsMap, BeanGenerator.QUERY_BEAN);
        }
        if (StringUtils.isInArray(codeTypes, "dao")) {
            DAOGenerator.generateJoinDAO(generator, beanName);
        }
        if (StringUtils.isInArray(codeTypes, "mapper")) {
            MapperGenerator.generateJoinMapper(beanName, generator, tables, primaryTable, columns, joinWhereClause);
        }
        if (StringUtils.isInArray(codeTypes, "service")) {
            ServiceGenerator.generateJoinService(generator, beanName);
            ServiceGenerator.generateJoinServiceImpl(generator, beanName);
        }
        if (StringUtils.isInArray(codeTypes, "controller")) {
            ControllerGenerator.generateJoinController(generator, beanName, mappingUrl);
        }
        if (StringUtils.isInArray(codeTypes, "view")) {
            ViewGenerator.generateJoinViewMain(beanName, mappingUrl, generator);
            ViewGenerator.generateJoinViewTable(beanName, mappingUrl, generator, primaryTable, columns, tableColumnsMap);
            ViewGenerator.generateJoinViewSearch(beanName, mappingUrl, generator, primaryTable, columns, tableColumnsMap);
            ViewGenerator.generateJoinViewDetail(beanName, mappingUrl, generator, primaryTable, columns, tableColumnsMap);
        }
    }

}
