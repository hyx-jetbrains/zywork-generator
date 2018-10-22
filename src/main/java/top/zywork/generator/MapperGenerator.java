package top.zywork.generator;


import org.springframework.util.StringUtils;
import top.zywork.bean.ColumnDetail;
import top.zywork.bean.Generator;
import top.zywork.bean.TableColumn;
import top.zywork.common.GeneratorUtils;
import top.zywork.common.PropertyUtils;
import top.zywork.constant.TemplateConstants;

import java.util.List;

/**
 * Mapper映射自动生成代码封装类<br/>
 *
 * 创建于2018-03-12<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class MapperGenerator {

    /**
     * 生成Mapper映射xml文件
     * @param generator
     * @param tableColumn 表数据
     */
    public static void generateMapper(Generator generator, TableColumn tableColumn) {
        String beanName = GeneratorUtils.tableNameToClassName(tableColumn.getTableName(), generator.getTablePrefix());
        String resDir = GeneratorUtils.createResDir(generator, generator.getMapperDir());
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.MAPPER_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.BEAN_NAME_LOWER_CASE, StringUtils.uncapitalize(beanName))
                .replace(TemplateConstants.DAO_SUFFIX, generator.getDaoSuffix())
                .replace(TemplateConstants.DO_SUFFIX, generator.getDoSuffix())
                .replace(TemplateConstants.TABLE_NAME, tableColumn.getTableName());
        fileContent = generateInsertColumns(fileContent, tableColumn, false);
        fileContent = generateInsertColumns(fileContent, tableColumn, true);
        fileContent = generateInsertValues(fileContent, tableColumn, false);
        fileContent = generateInsertValues(fileContent, tableColumn, true);
        fileContent = generateSetClause(fileContent, tableColumn, false);
        fileContent = generateSetClause(fileContent, tableColumn, true);
        fileContent = generateSelectColumns(fileContent, tableColumn);
        fileContent = generateQueryWhereClause(fileContent, tableColumn);
        GeneratorUtils.writeFile(fileContent, resDir, beanName + generator.getMapperSuffix() + ".xml");
    }

    /**
     * 生成关联表的Mapper映射xml文件
     * @param beanName bean名称
     * @param generator Generator实例
     * @param primaryTable 主表名称
     * @param columns 所选的字段
     * @param joinWhereClause 关联条件
     */
    public static void generateJoinMapper(String beanName, Generator generator, String primaryTable, String[] columns, String joinWhereClause) {
        StringBuilder tableName = new StringBuilder();
        for (String column : columns) {
            String table = column.split("-")[0];
            // user与user_social，如果不变成[user]和[user_social]，则无法正确获取到所有的表名称
            if (!tableName.toString().contains("[" + table + "]")) {
                tableName.append(", [").append(table).append("]");
            }
        }
        String resDir = GeneratorUtils.createResDir(generator, generator.getMapperDir());
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.JOIN_MAPPER_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.BEAN_NAME_LOWER_CASE, StringUtils.uncapitalize(beanName))
                .replace(TemplateConstants.DAO_SUFFIX, generator.getDaoSuffix())
                .replace(TemplateConstants.DO_SUFFIX, generator.getDoSuffix())
                .replace(TemplateConstants.TABLE_NAME, tableName.toString().substring(2).replace("[", "").replace("]", ""))
                .replace(TemplateConstants.PRIMARY_TABLE, primaryTable);
        fileContent = generateJoinSelectColumns(fileContent, generator, columns);
        fileContent = generateJoinQueryWhereClause(generator, fileContent, columns);
        fileContent = generateJoinWhereClause(fileContent, joinWhereClause);
        GeneratorUtils.writeFile(fileContent, resDir, beanName + generator.getMapperSuffix() + ".xml");
    }

    /**
     * 生成Mapper映射文件insert语句中的column部分
     * @param fileContent 文件内容
     * @param tableColumn 表数据
     * @param isBatch 是否批量插入
     * @return 添加了insert语句的文件内容
     */
    private static String generateInsertColumns(String fileContent, TableColumn tableColumn, boolean isBatch) {
        List<ColumnDetail> columnDetails = tableColumn.getColumnDetails();
        StringBuilder insertColumns = new StringBuilder("");
        for (ColumnDetail columnDetail : columnDetails) {
            if (!columnDetail.getName().equals("id")) {
                insertColumns.append(insertColumn(columnDetail.getFieldName(), columnDetail.getName(), isBatch));
            }
        }
        return fileContent.replace(isBatch ? TemplateConstants.INSERT_BATCH_COLUMNS : TemplateConstants.INSERT_COLUMNS, insertColumns.toString());
    }

    /**
     * 生成插入语句字段部分
     * @param field 属性
     * @param column 字段名
     * @param isBatch 是否批量插入
     * @return
     */
    private static String insertColumn(String field, String column, boolean isBatch) {
        StringBuilder insertColumn = new StringBuilder();
        insertColumn.append("<if test=\"");
        if (isBatch) {
            insertColumn.append("item.");
        }
        insertColumn.append(field)
                .append(" != null\">\n\t\t\t\t")
                .append(column)
                .append(",\n\t\t\t</if>\n\t\t\t");
        return insertColumn.toString();
    }

    /**
     * 生成Mapper映射文件insert语句中的值部分
     * @param fileContent 文件内容
     * @param tableColumn 表数据
     * @return 添加了insert语句的文件内容
     */
    private static String generateInsertValues(String fileContent, TableColumn tableColumn, boolean isBatch){
        List<ColumnDetail> columnDetails = tableColumn.getColumnDetails();
        StringBuilder insertValues = new StringBuilder("");
        for (ColumnDetail columnDetail : columnDetails) {
            if (!columnDetail.getName().equals("id")) {
                insertValues.append(insertValue(columnDetail.getFieldName(), isBatch));
            }
        }
        return fileContent.replace(isBatch ? TemplateConstants.INSERT_BATCH_VALUES : TemplateConstants.INSERT_VALUES, insertValues.toString());
    }

    /**
     * 生成insert语句value部分
     * @param field 属性名
     * @param isBatch 是否批量插入
     * @return
     */
    private static String insertValue(String field, boolean isBatch) {
        StringBuilder insertValue = new StringBuilder();
        insertValue.append("<if test=\"");
        if (isBatch) {
            insertValue.append("item.");
        }
        insertValue.append(field)
                .append(" != null\">\n\t\t\t\t")
                .append("#{");
        if (isBatch) {
            insertValue.append("item.");
        }
        insertValue.append(field)
                .append("}")
                .append(",\n\t\t\t</if>\n\t\t\t");
        return insertValue.toString();
    }

    /**
     * 生成Mapper映射文件update语句的set部分
     * @param fileContent 文件内容
     * @param tableColumn 表数据
     * @param isBatch 是否批量更新
     * @return 添加了update语句的文件内容
     */
    private static String generateSetClause(String fileContent, TableColumn tableColumn, boolean isBatch){
        List<ColumnDetail> columnDetails = tableColumn.getColumnDetails();
        StringBuilder setClause = new StringBuilder("");
        for (ColumnDetail columnDetail : columnDetails) {
            String column = columnDetail.getName();
            if (!column.equals("id")) {
                setClause.append(setValue(columnDetail.getFieldName(), column, isBatch));
            }
        }
        return fileContent.replace(isBatch ? TemplateConstants.SET_BATCH_CLAUSE : TemplateConstants.SET_CLAUSE, setClause.toString());
    }

    /**
     * 生成update语句set的值部分
     * @param field 属性史
     * @param column 字段名
     * @param isBatch 是否批量update
     * @return
     */
    private static String setValue(String field, String column, boolean isBatch) {
        StringBuilder setValue = new StringBuilder();
        setValue.append("<if test=\"");
        if (isBatch) {
            setValue.append("item.");
        }
        setValue.append(field)
                .append(" != null\">\n\t\t\t\t")
                .append(column)
                .append(" = ")
                .append("#{");
        if (isBatch) {
            setValue.append("item.");
        }
        setValue.append(field)
                .append("}")
                .append(",\n\t\t\t</if>\n\t\t\t");
        return setValue.toString();
    }

    /**
     * 生成Mapper映射文件select字段部分
     * @param fileContent 文件内容
     * @param tableColumn 表数据
     * @return 添加了select通用字段部分的文件内容
     */
    private static String generateSelectColumns(String fileContent, TableColumn tableColumn){
        List<ColumnDetail> columnDetails = tableColumn.getColumnDetails();
        StringBuilder selectColumns = new StringBuilder("");
        for (ColumnDetail columnDetail : columnDetails) {
            String columnName = columnDetail.getName();
            selectColumns.append(", ")
                    .append(columnName)
                    .append(" as ")
                    .append(PropertyUtils.columnToProperty(columnName));
        }
        return fileContent.replace(TemplateConstants.SELECT_COLUMNS, selectColumns.toString().replaceFirst(", ", ""));
    }

    /**
     * 生成关联表查询的select字段部分
     * @param fileContent 文件内容
     * @param columns 所选的字段
     * @return
     */
    private static String generateJoinSelectColumns(String fileContent, Generator generator, String[] columns){
        StringBuilder selectColumns = new StringBuilder("");
        for (String column : columns) {
            String[] tableNameAndColumn = column.split("-");
            String tableName = tableNameAndColumn[0];
            String columnName = tableNameAndColumn[1];
            selectColumns.append(", ")
                    .append(tableName + "." + columnName)
                    .append(" as ")
                    .append(StringUtils.uncapitalize(GeneratorUtils.tableNameToClassName(tableName, generator.getTablePrefix()))
                    + StringUtils.capitalize(PropertyUtils.columnToProperty(columnName)));
        }
        return fileContent.replace(TemplateConstants.SELECT_COLUMNS, selectColumns.toString().replaceFirst(", ", ""));
    }

    /**
     * 生成Mapper映射文件查询语句where部分的内容
     * @param fileContent 文件内容
     * @param tableColumn 表数据
     * @return 添加了查询语句where部分内容的文件内容
     */
    private static String generateQueryWhereClause(String fileContent, TableColumn tableColumn){
        List<ColumnDetail> columnDetails = tableColumn.getColumnDetails();
        StringBuilder whereClause = new StringBuilder("");
        for (int i = 0, size = columnDetails.size(); i < size; i++) {
            ColumnDetail columnDetail = columnDetails.get(i);
            whereClause(whereClause, i, columnDetail.getFieldName(), columnDetail.getName(), columnDetail.getJavaTypeName());
        }
        return fileContent.replace(TemplateConstants.QUERY_WHERE_CLAUSE, whereClause.toString());
    }

    /**
     * 生成关联表的查询条件部分
     * @param generator Generator实例
     * @param fileContent 文件内容
     * @param columns 所选的字段
     * @return
     */
    private static String generateJoinQueryWhereClause(Generator generator, String fileContent, String[] columns){
        StringBuilder whereClause = new StringBuilder("");
        for (int i = 0, len = columns.length; i < len; i++) {
            String[] tableNameAndColumn = columns[i].split("-");
            String tableName = tableNameAndColumn[0];
            String columnName = tableNameAndColumn[1];
            String javaType = tableNameAndColumn[2];
            String fullColumnName = tableName + "." + columnName;
            String field = StringUtils.uncapitalize(GeneratorUtils.tableNameToClassName(tableName, generator.getTablePrefix()))
                    + StringUtils.capitalize(PropertyUtils.columnToProperty(columnName));
            whereClause(whereClause, i, field, fullColumnName, javaType);
        }
        return fileContent.replace(TemplateConstants.QUERY_WHERE_CLAUSE, whereClause.toString());
    }

    /**
     * 生成查询语句的where条件部分
     * @param whereClause
     * @param columnIndex
     * @param field
     * @param column
     * @param javaType
     */
    private static void whereClause(StringBuilder whereClause, int columnIndex, String field, String column, String javaType) {
        if (javaType.equals("String")) {
            // 字符串的模糊搜索
            whereClause.append("<if test=\"query != null and query.")
                    .append(field).append(" != null and query.").append(field).append(" != ''\">\n\t\t\t");
            if (columnIndex != 0) {
                whereClause.append("and ");
            }
            whereClause.append(column)
                    .append(" like concat('%', ")
                    .append("#{query.")
                    .append(field)
                    .append("}, '%')")
                    .append("\n\t\t</if>\n\t\t");
        } else {
            // 非字符串类型的 == 比较查询
            whereClause.append("<if test=\"query != null and query.").append(field).append(" != null\">\n\t\t\t");
            if (columnIndex != 0) {
                whereClause.append("and ");
            }
            whereClause.append(column)
                    .append(" = ")
                    .append("#{query.")
                    .append(field)
                    .append("}")
                    .append("\n\t\t</if>\n\t\t");
            // 非字符串类型的 >= Min 比较查询
            whereClause.append("<if test=\"query != null and query.")
                    .append(field).append("Min != null and query.").append(field).append("Max == null").append("\">\n\t\t\t");
            if (columnIndex != 0) {
                whereClause.append("and ");
            }
            whereClause.append(column)
                    .append(" <![CDATA[ >= ]]> ")
                    .append("#{query.")
                    .append(field).append("Min")
                    .append("}")
                    .append("\n\t\t</if>\n\t\t");
            // 非字符串类型的 <= Max 比较查询
            whereClause.append("<if test=\"query != null and query.")
                    .append(field).append("Min == null and query.").append(field).append("Max != null").append("\">\n\t\t\t");
            if (columnIndex != 0) {
                whereClause.append("and ");
            }
            whereClause.append(column)
                    .append(" <![CDATA[ <= ]]> ")
                    .append("#{query.")
                    .append(field).append("Max")
                    .append("}")
                    .append("\n\t\t</if>\n\t\t");
            // 非字符串类型的 >= Min 且 <= Max 比较查询
            whereClause.append("<if test=\"query != null and query.")
                    .append(field).append("Min != null and query.").append(field).append("Max != null").append("\">\n\t\t\t");
            if (columnIndex != 0) {
                whereClause.append("and ");
            }
            whereClause.append(column)
                    .append(" <![CDATA[ >= ]]> ")
                    .append("#{query.")
                    .append(field).append("Min")
                    .append("}")
                    .append(" and ")
                    .append(column)
                    .append(" <![CDATA[ <= ]]> ")
                    .append("#{query.")
                    .append(field).append("Max")
                    .append("}")
                    .append("\n\t\t</if>\n\t\t");
        }
    }

    /**
     * 生成关联表的关联条件部分，此关联条件由开发者指定
     * @param fileContent 文件内容
     * @param joinWhereClause 关联条件，由开发者在前端界面中指定
     * @return
     */
    private static String generateJoinWhereClause(String fileContent, String joinWhereClause){
        return fileContent.replace(TemplateConstants.JOIN_WHERE_CLAUSE, joinWhereClause);
    }

}
