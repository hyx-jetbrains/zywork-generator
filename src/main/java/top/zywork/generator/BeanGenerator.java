package top.zywork.generator;

import org.springframework.util.StringUtils;
import top.zywork.bean.ColumnDetail;
import top.zywork.bean.FieldDetail;
import top.zywork.bean.Generator;
import top.zywork.bean.TableColumn;
import top.zywork.common.DateFormatUtils;
import top.zywork.common.GeneratorUtils;
import top.zywork.common.PropertyUtils;
import top.zywork.constant.TemplateConstants;
import top.zywork.enums.DatePatternEnum;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
     * 用于存储表字段对应的属性信息
     */
    private static List<FieldDetail> fieldDetailList = new ArrayList<>();

    /**
     * 生成表对应的各个bean类
     *
     * @param generator   Generator实例
     * @param tableColumn 表数据
     */
    public static void generateBean(Generator generator, TableColumn tableColumn, String beanType) {
        String beanName = GeneratorUtils.tableNameToClassName(tableColumn.getTableName(), generator.getTablePrefix());
        String packagePath = beanPackage(generator, beanType);
        String fileContent = fileContent(generator, beanType);
        String beanSuffix = beanSuffix(generator, beanType);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.CLASS_SUFFIX, beanSuffix)
                .replace(TemplateConstants.CREATE_DATE, DateFormatUtils.format(Calendar.getInstance(), DatePatternEnum.DATE.getValue()))
                .replace(TemplateConstants.AUTHOR, generator.getAuthor())
                .replace(TemplateConstants.SERIAL_VERSION_ID, PropertyUtils.generateSerialVersionId() + "");
        fileContent = generateFields(fileContent, tableColumn, beanType);
        fileContent = generatorConstructorParams(fileContent);
        fileContent = generatorConstructor(fileContent);
        fileContent = generateGetterSetters(fileContent);
        fileContent = generateToString(fileContent);
        GeneratorUtils.writeFile(fileContent, packagePath, beanName + beanSuffix + ".java");
    }

    /**
     * 根据bean类型产生不同的package
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
     * 生成关联表DO
     *
     * @param beanName         bean名称
     * @param generator        Generator实例
     * @param primaryTable     主表名称
     * @param columns          所选的字段
     * @param tableColumnsList 所有表字段信息列表
     */
    public static void generateJoinDO(String beanName, Generator generator, String primaryTable, String[] columns, List<TableColumn> tableColumnsList) {
        String packagePath = GeneratorUtils.createPackage(generator, generator.getDoPackage());
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.DO_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.CREATE_DATE,
                DateFormatUtils.format(Calendar.getInstance(), DatePatternEnum.DATE.getValue()))
                .replace(TemplateConstants.AUTHOR, generator.getAuthor())
                .replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.SERIAL_VERSION_ID, PropertyUtils.generateSerialVersionId() + "");
        fileContent = generateJoinFields(generator, fileContent, primaryTable, columns, tableColumnsList, DO_BEAN);
        fileContent = generatorJoinConstructorParams(fileContent);
        fileContent = generatorJoinConstructor(fileContent);
        fileContent = generateJoinGetterSetters(fileContent);
        fileContent = generateJoinToString(fileContent);
        GeneratorUtils.writeFile(fileContent, packagePath, beanName + generator.getDoSuffix());
    }

    /**
     * 生成关联表DTO对象
     *
     * @param beanName         bean名称
     * @param generator        Generator实例
     * @param primaryTable     主表名称
     * @param columns          所选的字段
     * @param tableColumnsList 所有表的字段信息列表
     */
    public static void generateJoinDTO(String beanName, Generator generator, String primaryTable, String[] columns, List<TableColumn> tableColumnsList) {
        String packagePath = GeneratorUtils.createPackage(generator, generator.getDtoPackage());
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.DTO_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.CREATE_DATE,
                DateFormatUtils.format(Calendar.getInstance(), DatePatternEnum.DATE.getValue()))
                .replace(TemplateConstants.AUTHOR, generator.getAuthor())
                .replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.SERIAL_VERSION_ID, PropertyUtils.generateSerialVersionId() + "");
        fileContent = generateJoinFields(generator, fileContent, primaryTable, columns, tableColumnsList, DTO_BEAN);
        fileContent = generatorJoinConstructorParams(fileContent);
        fileContent = generatorJoinConstructor(fileContent);
        fileContent = generateJoinGetterSetters(fileContent);
        fileContent = generateJoinToString(fileContent);
        GeneratorUtils.writeFile(fileContent, packagePath, beanName + generator.getDtoSuffix());
    }

    /**
     * 生成关联表的VO对象
     *
     * @param beanName         bean名称
     * @param generator        Generator实例
     * @param primaryTable     主表名称
     * @param columns          所选的字段
     * @param tableColumnsList 所有表字段信息列表
     */
    public static void generateJoinVO(String beanName, Generator generator, String primaryTable, String[] columns, List<TableColumn> tableColumnsList) {
        String packagePath = GeneratorUtils.createPackage(generator, generator.getVoPackage());
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VO_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.CREATE_DATE,
                DateFormatUtils.format(Calendar.getInstance(), DatePatternEnum.DATE.getValue()))
                .replace(TemplateConstants.AUTHOR, generator.getAuthor())
                .replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.SERIAL_VERSION_ID, PropertyUtils.generateSerialVersionId() + "");
        fileContent = generateJoinFields(generator, fileContent, primaryTable, columns, tableColumnsList, VO_BEAN);
        fileContent = generatorJoinConstructorParams(fileContent);
        fileContent = generatorJoinConstructor(fileContent);
        fileContent = generateJoinGetterSetters(fileContent);
        fileContent = generateJoinToString(fileContent);
        GeneratorUtils.writeFile(fileContent, packagePath, beanName + generator.getVoSuffix());
    }

    /**
     * 生成关联表的Query对象
     *
     * @param beanName         bean名称
     * @param generator        Generator实例
     * @param primaryTable     主表名称
     * @param columns          所选的字段
     * @param tableColumnsList 所有表字段信息的列表
     */
    public static void generateJoinQuery(String beanName, Generator generator, String primaryTable, String[] columns, List<TableColumn> tableColumnsList) {
        String packagePath = GeneratorUtils.createPackage(generator, generator.getQueryPackage());
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.QUERY_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.CREATE_DATE,
                DateFormatUtils.format(Calendar.getInstance(), DatePatternEnum.DATE.getValue()))
                .replace(TemplateConstants.AUTHOR, generator.getAuthor())
                .replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.SERIAL_VERSION_ID, PropertyUtils.generateSerialVersionId() + "");
        fileContent = generateJoinFields(generator, fileContent, primaryTable, columns, tableColumnsList, QUERY_BEAN);
        fileContent = generatorJoinConstructorParams(fileContent);
        fileContent = generatorJoinConstructor(fileContent);
        fileContent = generateJoinGetterSetters(fileContent);
        fileContent = generateJoinToString(fileContent);
        fileContent = fileContent.replace(TemplateConstants.TO_STRING, "");
        GeneratorUtils.writeFile(fileContent, packagePath, beanName + generator.getQuerySuffix());
    }

    /**
     * 生成表对应的所有属性
     *
     * @param fileContent 文件内容
     * @param tableColumn 表数据
     * @param beanType    bean类型
     * @return 添加了所有属性的文件内容
     */
    private static String generateFields(String fileContent, TableColumn tableColumn, String beanType) {
        fieldDetailList.clear();
        List<ColumnDetail> columnDetailList = tableColumn.getColumnDetails();
        StringBuilder fields = new StringBuilder("");
        for (ColumnDetail columnDetail : columnDetailList) {
            String field = columnDetail.getFieldName();
            String javaType = columnDetail.getJavaTypeName();
            String comment = columnDetail.getComment();
            // 生成所有bean属性
            fields.append(field("id", comment, javaType, field, columnDetail.getNullable(), columnDetail.getColumnSize(), beanType));
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
     * @param tableColumnsList 所有表字段信息的列表
     * @param beanType         bean类型
     * @return
     */
    private static String generateJoinFields(Generator generator, String fileContent, String primaryTable, String[] columns, List<TableColumn> tableColumnsList, String beanType) {
        fieldDetailList.clear();
        StringBuilder fields = new StringBuilder("");
        String id = StringUtils.uncapitalize(GeneratorUtils.tableNameToClassName(primaryTable,
                generator.getTablePrefix())) + StringUtils.capitalize(PropertyUtils.columnToProperty("id"));
        String lastTableName = null;
        for (String column : columns) {
            String[] tableNameAndColumn = column.split("-");
            String tableName = tableNameAndColumn[0];
            String columnName = tableNameAndColumn[1];
            // 对所表字段进行循环
            for (TableColumn tableColumns : tableColumnsList) {
                // 如果是指定的表
                if (tableColumns.getTableName().equals(tableName)) {
                    if (!tableName.equals(lastTableName)) {
                        fields.append("//")
                                .append(tableName)
                                .append("表的字段对应的属性\n\t");
                        lastTableName = tableName;
                    }
                    // 获取指定表的所有字段信息
                    List<ColumnDetail> columnDetailList = tableColumns.getColumnDetails();
                    for (ColumnDetail columnDetail : columnDetailList) {
                        // 如果是想要查询的字段
                        if (columnDetail.getName().equals(columnName)) {
                            String field = StringUtils.uncapitalize(GeneratorUtils.tableNameToClassName(tableName, generator.getTablePrefix()))
                                    + StringUtils.capitalize(columnDetail.getFieldName());
                            String javaType = columnDetail.getJavaTypeName();
                            String comment = columnDetail.getComment();
                            fields.append(field(id, comment, javaType, field, columnDetail.getNullable(), columnDetail.getColumnSize(), null));
                        }
                    }
                }
            }
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
        fieldDetailList.add(new FieldDetail(fieldName, javaType, title));
        if (DO_BEAN.equals(beanType) || DTO_BEAN.equals(beanType)) {
            return normalField(idField, title, javaType, fieldName);
        } else if (VO_BEAN.equals(beanType)) {
            return voField(idField, title, javaType, fieldName, nullable, columnSize);
        } else if (QUERY_BEAN.equals(beanType)) {
            return queryField(idField, title, javaType, fieldName);
        }
        return "";
    }

    /**
     * 生成do对象和dto对象属性，不需要增加数据验证等
     *
     * @param idField   id属性名
     * @param title     属性中文名
     * @param javaType  java类型
     * @param fieldName 属性英文名
     * @return
     */
    private static String normalField(String idField, String title, String javaType, String fieldName) {
        StringBuilder field = new StringBuilder();
        field.append("// ")
                .append(title)
                .append("\n");
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
        field.append("// ")
                .append(title)
                .append("\n");
        if (!fieldName.equals(idField)) {
            // 需要对属性进行后台验证，但是不包括id属性
            if (javaType.equals("String") && nullable == DatabaseMetaData.columnNoNulls) {
                // 字符串且不能为空
                field.append("\t@NotBlank(message = \"此项是必须项\")\n")
                        .append("\t@Size(min = 1, max = ")
                        .append(columnSize)
                        .append(", message = \"必须是1-")
                        .append(columnSize)
                        .append("个字符\")\n");
            } else if (javaType.equals("String") && nullable == DatabaseMetaData.columnNullable) {
                // 字符串能为空，但是有最大限制
                field.append("\t@Size(min = 0, max = ")
                        .append(columnSize)
                        .append(", message = \"必须小于")
                        .append(columnSize)
                        .append("个字符\")\n");
            } else if (!javaType.equals("Date") && nullable == DatabaseMetaData.columnNoNulls) {
                // 其他类型（不包括Date类型），但是不能为空
                field.append("\t@NotNull(message = \"此项是必须项\")\n");
            } else if (javaType.equals("Date") && nullable == DatabaseMetaData.columnNoNulls) {
                // 时间类型，且不能为空
                field.append("\t@NotNull(message = \"此项是必须项\")\n")
                        .append("\t@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\n");
            } else if (javaType.equals("Date") && nullable == DatabaseMetaData.columnNullable) {
                // 时间类型，能为空
                field.append("\t@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\n");
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
     * @param idField   id属性名
     * @param title     属性中文名
     * @param javaType  java类型
     * @param fieldName 属性英文名
     * @return
     */
    private static String queryField(String idField, String title, String javaType, String fieldName) {
        StringBuilder field = new StringBuilder();
        field.append("// ")
                .append(title)
                .append("\n");
        if (javaType.equals("Date")) {
            // 把接收的字符串时间转化成Date
            field.append("\t@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\n");
        }
        field.append("\tprivate ")
                .append(javaType)
                .append(" ")
                .append(fieldName)
                .append(";\n\t");
        if (!javaType.equals("String") && !fieldName.equals(idField)) {
            fieldDetailList.add(new FieldDetail(fieldName + "Min", javaType, title + "（最小值）"));
            fieldDetailList.add(new FieldDetail(fieldName + "Max", javaType, title + "（最大值）"));
            field.append("// ")
                    .append(title)
                    .append("（最小值）")
                    .append("\n");
            if (javaType.equals("Date")) {
                field.append("\t@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\n");
            }
            field.append("\tprivate ")
                    .append(javaType)
                    .append(" ")
                    .append(fieldName)
                    .append("Min")
                    .append(";\n\t");
            field.append("// ")
                    .append(title)
                    .append("（最大值）")
                    .append("\n");
            if (javaType.equals("Date")) {
                field.append("\t@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\n");
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

    /**
     * 生成有参构造方法的所有参数
     *
     * @param fileContent 文件内容
     * @return 添加了有参构造方法的参数的文件内容
     */
    private static String generatorConstructorParams(String fileContent) {
        StringBuilder constructorParams = new StringBuilder("");
        // 直接使用之前生成的所有属性列表fieldDetailList
        for (FieldDetail fieldDetail : fieldDetailList) {
            constructorParams.append(", ")
                    .append(fieldDetail.getJavaType())
                    .append(" ")
                    .append(fieldDetail.getName());
        }
        return fileContent.replace(TemplateConstants.CONSTRUCTOR_PARAMS, constructorParams.toString().replaceFirst(", ", ""));
    }

    /**
     * 生成关联表的构造方法的所有参数
     *
     * @param fileContent 文件内容
     * @return
     */
    private static String generatorJoinConstructorParams(String fileContent) {
        return generatorConstructorParams(fileContent);
    }

    /**
     * 生成有参构造方法体
     *
     * @param fileContent 文件内容
     * @return 添加了有参构造方法体的文件内容
     */
    private static String generatorConstructor(String fileContent) {
        StringBuilder constructor = new StringBuilder("");
        // 直接使用之前生成的所有属性列表fieldDetailList
        for (FieldDetail fieldDetail : fieldDetailList) {
            String field = fieldDetail.getName();
            constructor.append("this.")
                    .append(field)
                    .append(" = ").append(field).append(";\n\t\t");
        }
        return fileContent.replace(TemplateConstants.CONSTRUCTOR, constructor.toString());
    }

    /**
     * 生成关联表对象的构造方法
     *
     * @param fileContent 文件内容
     * @return
     */
    private static String generatorJoinConstructor(String fileContent) {
        return generatorConstructor(fileContent);
    }

    /**
     * 生成所有属性的getter/setter方法
     *
     * @param fileContent 文件内容
     * @return 添加了所有属性的getter/setter方法的文件内容
     */
    private static String generateGetterSetters(String fileContent) {
        StringBuilder getterSetters = new StringBuilder("");
        // 直接使用之前生成的所有属性列表fieldDetailList
        for (FieldDetail fieldDetail : fieldDetailList) {
            String field = fieldDetail.getName();
            String javaType = fieldDetail.getJavaType();
            getterSetters.append("public ")
                    .append(javaType)
                    .append(" ")
                    .append(PropertyUtils.getter(field))
                    .append("()")
                    .append(" {\n")
                    .append("\t\treturn ")
                    .append(field)
                    .append(";\n")
                    .append("\t}\n\n")
                    .append("\tpublic void ")
                    .append(PropertyUtils.setter(field))
                    .append("(")
                    .append(javaType)
                    .append(" ")
                    .append(field)
                    .append(") {\n")
                    .append("\t\tthis.")
                    .append(field)
                    .append(" = ")
                    .append(field)
                    .append(";\n")
                    .append("\t}\n\n\t");
        }
        return fileContent.replace(TemplateConstants.FIELDS_GETTER_SETTER, getterSetters.toString());
    }

    /**
     * 生成关联表对象的getter/setter
     *
     * @param fileContent 文件内容
     * @return
     */
    private static String generateJoinGetterSetters(String fileContent) {
        return generateGetterSetters(fileContent);
    }

    /**
     * 生成toString方法
     *
     * @param fileContent 文件内容
     * @return 添加了toString方法的文件内容
     */
    private static String generateToString(String fileContent) {
        StringBuilder toString = new StringBuilder("");
        for (FieldDetail fieldDetail : fieldDetailList) {
            String field = fieldDetail.getName();
            toString.append("\", ").append(field).append(" = \" + ").append(field).append(" + ").append("\n\t\t\t\t");
        }
        return fileContent.replace(TemplateConstants.TO_STRING, toString.toString().replaceFirst(", ", ""));
    }

    /**
     * 生成关联表对象的toString方法
     *
     * @param fileContent 文件内容
     * @return
     */
    private static String generateJoinToString(String fileContent) {
        return generateToString(fileContent);
    }

}
