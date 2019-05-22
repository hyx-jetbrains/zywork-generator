package top.zywork.generator;

import org.apache.commons.lang3.StringUtils;
import top.zywork.bean.ColumnDetail;
import top.zywork.bean.Generator;
import top.zywork.bean.TableColumns;
import top.zywork.common.DateFormatUtils;
import top.zywork.common.GeneratorUtils;
import top.zywork.common.PropertyUtils;
import top.zywork.constant.GeneratorConstants;
import top.zywork.constant.TemplateConstants;
import top.zywork.enums.DatePatternEnum;

import java.sql.DatabaseMetaData;
import java.util.Calendar;
import java.util.Map;

/**
 * 实体类自动生成封装类<br/>
 * <p>
 * 创建于2018-03-12<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class BeanGenerator {

    public static final String DO_BEAN = "do";
    public static final String DTO_BEAN = "dto";
    public static final String VO_BEAN = "vo";
    public static final String QUERY_BEAN = "query";

    /**
     * 生成表对应的各个bean类
     *
     * @param generator   Generator实例
     * @param tableColumns 表数据
     * @param beanType bean类型
     */
    public static void generateBean(Generator generator, TableColumns tableColumns, String beanType) {
        String beanName = GeneratorUtils.tableNameToClassName(tableColumns.getTableName(), generator.getTablePrefix());
        String packagePath = beanPackage(generator, beanType);
        String fileContent = fileContent(generator, beanType);
        String beanSuffix = beanSuffix(generator, beanType);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.CLASS_SUFFIX, beanSuffix)
                .replace(TemplateConstants.CREATE_DATE, DateFormatUtils.format(Calendar.getInstance(), DatePatternEnum.DATE.getValue()))
                .replace(TemplateConstants.AUTHOR, generator.getAuthor())
                .replace(TemplateConstants.SERIAL_VERSION_ID, PropertyUtils.generateSerialVersionId() + "");
        fileContent = generateFields(fileContent, tableColumns, beanType);
        GeneratorUtils.writeFile(fileContent, packagePath, beanName + beanSuffix + ".java");
    }

    /**
     * 生成关联表的实体类
     * @param beanName 实体类名称
     * @param generator Generator对象
     * @param primaryTable 主体表名称
     * @param columns 所有选择的表字段
     * @param tableColumnsMap 所有表信息map
     * @param beanType bean类型
     */
    public static void generateJoinBean(String beanName, Generator generator, String primaryTable, String[] columns, Map<String, TableColumns> tableColumnsMap, String beanType) {
        String packagePath = beanPackage(generator, beanType);
        String fileContent = fileContent(generator, beanType);
        String beanSuffix = beanSuffix(generator, beanType);
        fileContent = fileContent.replace(TemplateConstants.CREATE_DATE,
                DateFormatUtils.format(Calendar.getInstance(), DatePatternEnum.DATE.getValue()))
                .replace(TemplateConstants.AUTHOR, generator.getAuthor())
                .replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.CLASS_SUFFIX, beanSuffix)
                .replace(TemplateConstants.SERIAL_VERSION_ID, PropertyUtils.generateSerialVersionId() + "");
        fileContent = generateJoinFields(generator, fileContent, primaryTable, columns, tableColumnsMap, beanType);
        GeneratorUtils.writeFile(fileContent, packagePath, beanName + beanSuffix + ".java");
    }

    /**
     * 根据bean类型生成不同的package
     * @param generator
     * @param beanType
     * @return
     */
    private static String beanPackage(Generator generator, String beanType) {
        switch (beanType) {
            case DO_BEAN: return GeneratorUtils.createPackage(generator, generator.getDoPackage());
            case DTO_BEAN: return GeneratorUtils.createPackage(generator, generator.getDtoPackage());
            case VO_BEAN: return GeneratorUtils.createPackage(generator, generator.getVoPackage());
            case QUERY_BEAN: return GeneratorUtils.createPackage(generator, generator.getQueryPackage());
            default: return "";
        }
    }

    /**
     * 根据bean类型获取模板文件内容
     * @param generator
     * @param beanType
     * @return
     */
    private static String fileContent(Generator generator, String beanType) {
        switch (beanType) {
            case DO_BEAN: return GeneratorUtils.readTemplate(generator, TemplateConstants.DO_TEMPLATE);
            case DTO_BEAN: return GeneratorUtils.readTemplate(generator, TemplateConstants.DTO_TEMPLATE);
            case VO_BEAN: return GeneratorUtils.readTemplate(generator, TemplateConstants.VO_TEMPLATE);
            case QUERY_BEAN: return GeneratorUtils.readTemplate(generator, TemplateConstants.QUERY_TEMPLATE);
            default: return "";
        }
    }

    /**
     * 根据bean类型后缀
     * @param generator
     * @param beanType
     * @return
     */
    private static String beanSuffix(Generator generator, String beanType) {
        switch (beanType) {
            case DO_BEAN: return generator.getDoSuffix();
            case DTO_BEAN: return generator.getDtoSuffix();
            case VO_BEAN: return generator.getVoSuffix();
            case QUERY_BEAN: return generator.getQuerySuffix();
            default: return "";
        }
    }

    /**
     * 生成表对应的所有属性
     *
     * @param fileContent 文件内容
     * @param tableColumns 表数据字段
     * @param beanType    bean类型
     * @return 添加了所有属性的文件内容
     */
    private static String generateFields(String fileContent, TableColumns tableColumns, String beanType) {
        StringBuilder fields = new StringBuilder();
        for (ColumnDetail columnDetail : tableColumns.getColumnDetailList()) {
            // 生成选择字段对应的属性
            fields.append(field("id", columnDetail.getComment(), columnDetail.getJavaTypeName(), columnDetail.getFieldName(), columnDetail.getNullable(), columnDetail.getColumnSize(), beanType));
        }
        return fileContent.replace(TemplateConstants.FIELDS, fields.toString());
    }

    /**
     * 生成关联表的所有属性
     *
     * @param generator        Generator实例
     * @param fileContent      文件内容
     * @param primaryTable     主表名称
     * @param columns          所选的字段
     * @param tableColumnsMap 所有表字段信息的列表
     * @param beanType         bean类型
     * @return
     */
    private static String generateJoinFields(Generator generator, String fileContent, String primaryTable, String[] columns, Map<String, TableColumns> tableColumnsMap, String beanType) {
        StringBuilder fields = new StringBuilder();
        String id = StringUtils.uncapitalize(GeneratorUtils.tableNameToClassName(primaryTable, generator.getTablePrefix())) + StringUtils.capitalize(PropertyUtils.columnToProperty("id"));
        String lastTableName = null;
        for (String tableAndColumn : columns) {
            String[] tableNameColumn = tableAndColumn.split(GeneratorConstants.TABLE_COLUMN_SEPARATOR);
            String tableName = tableNameColumn[0];
            String columnName = tableNameColumn[1];
            TableColumns tableColumns = tableColumnsMap.get(tableName);
            if (!tableName.equals(lastTableName)) {
                fields.append("/*\n").append("\t * ").append(tableName).append("表的字段对应的属性\n\t */\n\t");
                lastTableName = tableName;
            }
            ColumnDetail columnDetail = tableColumns.getColumnDetailMap().get(columnName);
            String fieldName = StringUtils.uncapitalize(GeneratorUtils.tableNameToClassName(tableName, generator.getTablePrefix())) + StringUtils.capitalize(columnDetail.getFieldName());
            fields.append(field(id, columnDetail.getComment(), columnDetail.getJavaTypeName(), fieldName, columnDetail.getNullable(), columnDetail.getColumnSize(), beanType));
        }
        return fileContent.replace(TemplateConstants.FIELDS, fields.toString());
    }

    /**
     * 生成属性如private String userId，并根据条件增加数据验证
     *
     * @param idField    id属性名
     * @param title      属性中文名
     * @param javaType   java类型
     * @param fieldName  属性英文名
     * @param nullable   是否可为空
     * @param columnSize 字段长度
     * @param beanType   bean类型
     * @return
     */
    private static String field(String idField, String title, String javaType, String fieldName, int nullable, int columnSize, String beanType) {
        if (DO_BEAN.equals(beanType) || DTO_BEAN.equals(beanType)) {
            return normalField(title, javaType, fieldName);
        } else if (VO_BEAN.equals(beanType)) {
            return voField(idField, title, javaType, fieldName, nullable, columnSize);
        } else if (QUERY_BEAN.equals(beanType)) {
            return queryField(title, javaType, fieldName);
        }
        return "";
    }

    /**
     * 生成do对象和dto对象属性，不需要增加数据验证等
     *
     * @param title     属性中文名
     * @param javaType  java类型
     * @param fieldName 属性英文名
     * @return
     */
    private static String normalField(String title, String javaType, String fieldName) {
        StringBuilder field = new StringBuilder();
        field.append("/**\n").append("\t * ").append(title).append("\n\t */\n");
        field.append("\tprivate ")
                .append(javaType)
                .append(" ")
                .append(fieldName)
                .append(";\n\t");
        return field.toString();
    }

    /**
     * 生成vo对象属性，需要增加数据验证
     *
     * @param idField    id属性名
     * @param title      属性中文名
     * @param javaType   java类型
     * @param fieldName  属性英文名
     * @param nullable   是否可为空
     * @param columnSize 字段长度
     * @return
     */
    private static String voField(String idField, String title, String javaType, String fieldName, int nullable, int columnSize) {
        StringBuilder field = new StringBuilder();
        field.append("/**\n").append("\t * ").append(title).append("\n\t */\n");
        if (!fieldName.equals(idField)) {
            // 需要对属性进行后台验证，但是不包括id属性
            if ("String".equals(javaType) && nullable == DatabaseMetaData.columnNoNulls) {
                // 字符串且不能为空
                field.append("\t@NotBlank(message = \"此项是必须项\")\n")
                        .append("\t@Size(min = 1, max = ")
                        .append(columnSize)
                        .append(", message = \"必须是1-")
                        .append(columnSize)
                        .append("个字符\")\n");
            } else if ("String".equals(javaType) && nullable == DatabaseMetaData.columnNullable) {
                // 字符串能为空，但是有最大限制
                field.append("\t@Size(min = 0, max = ")
                        .append(columnSize)
                        .append(", message = \"必须小于")
                        .append(columnSize)
                        .append("个字符\")\n");
            } else if (!"String".equals(javaType) && !"Date".equals(javaType) && nullable == DatabaseMetaData.columnNoNulls) {
                // 其他非字符串及时间类型且不能为空
                field.append("\t@NotNull(message = \"此项是必须项\")\n");
            } else if ("Date".equals(javaType) && nullable == DatabaseMetaData.columnNoNulls) {
                // 时间类型，且不能为空
                field.append("\t@NotNull(message = \"此项是必须项\")\n")
                        .append("\t@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")\n");
            } else if ("Date".equals(javaType) && nullable == DatabaseMetaData.columnNullable) {
                // 时间类型，能为空
                field.append("\t@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")\n");
            }
        }
        field.append("\tprivate ")
                .append(javaType)
                .append(" ")
                .append(fieldName)
                .append(";\n\t");
        return field.toString();
    }

    /**
     * 生成query对象属性，对非字符串的数据类型，需要添加范围搜索
     *
     * @param title     属性中文名
     * @param javaType  java类型
     * @param fieldName 属性英文名
     * @return
     */
    private static String queryField(String title, String javaType, String fieldName) {
        StringBuilder field = new StringBuilder();
        field.append("/**\n").append("\t * ").append(title).append("\n\t */\n");
        if ("Date".equals(javaType)) {
            // 把接收的字符串时间转化成Date
            field.append("\t@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")\n");
        }
        field.append("\tprivate ")
                .append(javaType)
                .append(" ")
                .append(fieldName)
                .append(";\n\t");
        if (!"String".equals(javaType)) {
            field.append("/**\n").append("\t * ").append(title).append("(最小值)").append("\n\t */\n");
            if ("Date".equals(javaType)) {
                field.append("\t@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")\n");
            }
            field.append("\tprivate ")
                    .append(javaType)
                    .append(" ")
                    .append(fieldName)
                    .append("Min")
                    .append(";\n\t");
            field.append("/**\n").append("\t * ").append(title).append("(最大值)").append("\n\t */\n");
            if ("Date".equals(javaType)) {
                field.append("\t@JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")\n");
            }
            field.append("\tprivate ")
                    .append(javaType)
                    .append(" ")
                    .append(fieldName)
                    .append("Max")
                    .append(";\n\t");
        }
        return field.toString();
    }

}
