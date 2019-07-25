package top.zywork.generator;

import top.zywork.bean.ColumnDetail;
import top.zywork.bean.Generator;
import top.zywork.bean.TableColumns;
import top.zywork.common.GeneratorUtils;
import top.zywork.common.StringUtils;
import top.zywork.constant.GeneratorConstants;
import top.zywork.constant.TemplateConstants;

import java.io.File;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * View视图自动生成代码封装类<br/>
 * <p>
 * 创建于2018-03-12<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class ViewGenerator {

    /**
     * 生成单表的主视图文件
     * @param generator Generator对象
     * @param beanName
     * @param moduleName
     */
    public static void generateViewMain(Generator generator, String beanName, String moduleName) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + moduleName);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_MAIN_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.MAPPING_URL, moduleName);
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "Main.vue");
    }

    /**
     * 生成单表的表格视图文件
     * @param generator Generator对象
     * @param beanName
     * @param moduleName
     * @param tableColumns 单表的所有字段组成的对象
     */
    public static void generateViewTable(Generator generator, String beanName, String moduleName, TableColumns tableColumns) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + moduleName);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_TABLE_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.MAPPING_URL, moduleName)
                .replace(TemplateConstants.TABLE_COLUMNS, generateTableColumns(tableColumns));
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "TableMain.vue");
    }

    /**
     * 生成单表的添加修改视图文件
     * @param generator Generator对象
     * @param beanName
     * @param moduleName
     * @param tableColumns 单表的所有字段组成的对象
     */
    public static void generateViewAddEdit(Generator generator, String beanName, String moduleName, TableColumns tableColumns) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + moduleName);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_ADDEDIT_MODAL_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.FORM_ITEMS, generateFormItems(generator, tableColumns))
                .replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.MAPPING_URL, moduleName)
                .replace(TemplateConstants.FORM_FIELDS, generateFormFields(generator, tableColumns, true))
                .replace(TemplateConstants.FORM_VALIDATE_RULES, generateValidateRules(generator, tableColumns));
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "AddEditModal.vue");
    }

    /**
     * 生成单表的搜索视图文件
     * @param generator Generator对象
     * @param beanName
     * @param moduleName
     * @param tableColumns 单表的所有字段组成的对象
     */
    public static void generateViewSearch(Generator generator, String beanName, String moduleName, TableColumns tableColumns) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + moduleName);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_SEARCH_MODAL_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.SEARCH_FORM_ITEMS, generateSearchFormItems(tableColumns))
                .replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.MAPPING_URL, moduleName)
                .replace(TemplateConstants.SEARCH_FORM_FIELDS, generateSearchFormFields(tableColumns));
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "SearchModal.vue");
    }

    /**
     * 生成单表的详情视图文件
     * @param generator Generator对象
     * @param beanName
     * @param moduleName
     * @param tableColumns 单表的所有字段组成的对象
     */
    public static void generateViewDetail(Generator generator, String beanName, String moduleName, TableColumns tableColumns) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + moduleName);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_DETAIL_MODAL_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.FORM_ITEMS, generateFormItems(generator, tableColumns))
                .replace(TemplateConstants.DETAIL_ITEMS, generateDetailItems(tableColumns))
                .replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.FORM_FIELDS, generateFormFields(generator, tableColumns, false));
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "DetailModal.vue");
    }

    /**
     * 生成单表的供多选的主视图文件
     * @param generator Generator对象
     * @param beanName
     * @param moduleName
     */
    public static void generateViewMultiple(Generator generator, String beanName, String moduleName) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + moduleName);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_MAIN_MULTIPLE_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName);
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "MainMultiple.vue");
    }

    /**
     * 生成单表的供多选的表格视图文件
     * @param generator Generator对象
     * @param beanName
     * @param moduleName
     * @param tableColumns 单表的所有字段组成的对象
     */
    public static void generateViewTableMultiple(Generator generator, String beanName, String moduleName, TableColumns tableColumns) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + moduleName);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_TABLE_MULTIPLE_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.MAPPING_URL, moduleName)
                .replace(TemplateConstants.TABLE_COLUMNS, generateTableColumns(tableColumns));
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "TableMultiple.vue");
    }

    /**
     * 生成单表的供单选的主视图文件
     * @param generator Generator对象
     * @param beanName
     * @param moduleName
     */
    public static void generateViewSingle(Generator generator, String beanName, String moduleName) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + moduleName);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_MAIN_SINGLE_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName);
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "MainSingle.vue");
    }

    /**
     * 生成单表的供单选的表格视图文件
     * @param generator Generator对象
     * @param beanName
     * @param moduleName
     * @param tableColumns 单表的所有字段组成的对象
     */
    public static void generateViewTableSingle(Generator generator, String beanName, String moduleName, TableColumns tableColumns) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + moduleName);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_TABLE_SINGLE_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.MAPPING_URL, moduleName)
                .replace(TemplateConstants.TABLE_COLUMNS, generateTableColumns(tableColumns));
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "TableSingle.vue");
    }

    /**
     * 生成单表的供单选的主视图文件
     * @param generator Generator对象
     * @param beanName
     * @param moduleName
     */
    public static void generateViewShow(Generator generator, String beanName, String moduleName) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + moduleName);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_MAIN_SHOW_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName);
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "MainShow.vue");
    }

    /**
     * 生成单表的供展示的表格视图文件
     * @param generator Generator对象
     * @param beanName
     * @param moduleName
     * @param tableColumns 单表的所有字段组成的对象
     */
    public static void generateViewTableShow(Generator generator, String beanName, String moduleName, TableColumns tableColumns) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + moduleName);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_TABLE_SHOW_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.MAPPING_URL, moduleName)
                .replace(TemplateConstants.TABLE_COLUMNS, generateTableColumns(tableColumns));
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "TableShow.vue");
    }

    /**
     * 生成多表关联的主视图文件
     * @param beanName 实体类名称
     * @param mappingUrl 映射url
     * @param generator Generator对象
     */
    public static void generateJoinViewMain(String beanName, String mappingUrl, Generator generator) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + mappingUrl);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.JOIN_VIEW_MAIN_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName);
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "Main.vue");
    }

    /**
     * 生成多表关联的表视图文件
     * @param beanName 实体类名称
     * @param mappingUrl 映射url
     * @param generator Generator对象
     * @param primaryTable 主表名称
     * @param columns 所有选择的字段
     * @param tableColumnsMap 数据库中所有的表信息map
     */
    public static void generateJoinViewTable(String beanName, String mappingUrl, Generator generator, String primaryTable, String[] columns, Map<String, TableColumns> tableColumnsMap) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + mappingUrl);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.JOIN_VIEW_TABLE_TEMPLATE);
        TableColumns tableColumns = columnsToTableColumn(generator, primaryTable, columns, tableColumnsMap);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.MAPPING_URL, mappingUrl)
                .replace(TemplateConstants.TABLE_COLUMNS, generateTableColumns(tableColumns));
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "Table.vue");
    }

    /**
     * 生成多表关联的搜索视图文件
     * @param beanName 实体类名称
     * @param mappingUrl 映射url
     * @param generator Generator对象
     * @param primaryTable 主表名称
     * @param columns 所有选择的字段
     * @param tableColumnsMap 数据库中所有的表信息map
     */
    public static void generateJoinViewSearch(String beanName, String mappingUrl, Generator generator, String primaryTable, String[] columns, Map<String, TableColumns> tableColumnsMap) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + mappingUrl);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_SEARCH_MODAL_TEMPLATE);
        TableColumns tableColumns = columnsToTableColumn(generator, primaryTable, columns, tableColumnsMap);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.MAPPING_URL, mappingUrl)
                .replace(TemplateConstants.SEARCH_FORM_ITEMS, generateSearchFormItems(tableColumns))
                .replace(TemplateConstants.SEARCH_FORM_FIELDS, generateSearchFormFields(tableColumns));
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "SearchModal.vue");
    }

    /**
     * 生成多表关联的视图文件
     * @param beanName 实体类名称
     * @param mappingUrl 映射url
     * @param generator Generator对象
     * @param primaryTable 主表名称
     * @param columns 所有选择的字段
     * @param tableColumnsMap 数据库中所有的表信息map
     */
    public static void generateJoinViewDetail(String beanName, String mappingUrl, Generator generator, String primaryTable, String[] columns, Map<String, TableColumns> tableColumnsMap) {
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir() + File.separator + mappingUrl);
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_DETAIL_MODAL_TEMPLATE);
        TableColumns tableColumns = columnsToTableColumn(generator, primaryTable, columns, tableColumnsMap);
        fileContent = fileContent.replace(TemplateConstants.BEAN_NAME, beanName)
                .replace(TemplateConstants.FORM_ITEMS, generateFormItems(generator, tableColumns))
                .replace(TemplateConstants.DETAIL_ITEMS, generateDetailItems(tableColumns))
                .replace(TemplateConstants.FORM_FIELDS, generateFormFields(generator, tableColumns, false));
        GeneratorUtils.writeFile(fileContent, resDir, beanName + "DetailModal.vue");
    }

    /**
     * 生成单表addForm和editForm部分的formItems
     * @param generator Generator对象
     * @param tableColumns 单表的所有字段组成的对象
     * @return
     */
    private static String generateFormItems(Generator generator, TableColumns tableColumns) {
        StringBuilder formItems = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumns.getColumnDetailList();
        int index = 0;
        for (ColumnDetail columnDetail : columnDetailList) {
            if (!generator.getExclusiveAddEditColumns().contains(columnDetail.getName())) {
                formItems.append(formItem(columnDetail.getComment(), columnDetail.getFieldName(), columnDetail.getJavaTypeName(), index));
                index++;
            }
        }
        if (!formItems.toString().endsWith("</Row>\n")) {
            formItems.append("\n</Row>\n");
        }
        return formItems.toString();
    }

    /**
     * 生成每一个FormItem
     * @param title 标题
     * @param fieldName 属性名
     * @param javaTypeName java类型
     * @return
     */
    private static String formItem(String title, String fieldName, String javaTypeName, int index) {
        StringBuilder formItem = new StringBuilder();
        if (index % 2 == 0) {
            formItem.append("<Row>\n\t");
        }
        formItem.append("<i-col span=\"12\">\n\t")
                .append("<FormItem label=\"")
                .append(title)
                .append("\" prop=\"")
                .append(fieldName)
                .append("\">");
        switch (javaTypeName) {
            case "String":
                formItem.append("\n\t<Input v-model=\"")
                        .append("form.")
                        .append(fieldName)
                        .append("\" placeholder=\"请输入")
                        .append(title)
                        .append("\"/>");
                break;
            case "Date":
                formItem.append("\n\t<DatePicker @on-change=\"form.")
                        .append(fieldName)
                        .append("=$event\" ")
                        .append(":value=\"")
                        .append("form.")
                        .append(fieldName)
                        .append("\" placeholder=\"请输入")
                        .append(title)
                        .append("\" type=\"datetime\" format=\"yyyy-MM-dd HH:mm:ss\" style=\"width: 100%;\">")
                        .append("</DatePicker>");
                break;
            default:
                formItem.append("\n\t<InputNumber v-model=\"")
                        .append("form.")
                        .append(fieldName)
                        .append("\" placeholder=\"请输入")
                        .append(title)
                        .append("\" style=\"width: 100%;\"/>");
                break;
        }
        formItem.append("\n</FormItem>\n\t")
                .append("</i-col>");
        if (index % 2 == 1) {
            formItem.append("\n</Row>\n");
        }
        return formItem.toString();
    }

    /**
     * 生成searchForm的formItems
     * @param tableColumns 所有的字段信息组成的对象
     * @return
     */
    private static String generateSearchFormItems(TableColumns tableColumns) {
        StringBuilder formItems = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumns.getColumnDetailList();
        int index = 0;
        for (ColumnDetail columnDetail : columnDetailList) {
            String fieldName = columnDetail.getFieldName();
            String javaType = columnDetail.getJavaTypeName();
            if (index % 2 == 0) {
                formItems.append("<Row>\n\t");
            }
            formItems.append("<i-col span=\"12\">\n\t");
            switch (javaType) {
                case "String":
                    formItems.append("<FormItem label=\"")
                            .append(columnDetail.getComment())
                            .append("\" prop=\"")
                            .append(fieldName)
                            .append("\">")
                            .append("\n\t<Input v-model=\"")
                            .append("searchForm.")
                            .append(fieldName)
                            .append("\" placeholder=\"请输入")
                            .append(columnDetail.getComment())
                            .append("\"/>");
                    break;
                case "Date":
                    formItems.append("<FormItem label=\"")
                            .append(columnDetail.getComment())
                            .append("\">")
                            .append("<Row>")
                            .append("\n\t<i-col span=\"11\">")
                            .append("\n\t<FormItem prop=\"")
                            .append(fieldName)
                            .append("Min")
                            .append("\">")
                            .append("\n\t<DatePicker @on-change=\"searchForm.")
                            .append(fieldName)
                            .append("Min")
                            .append("=$event\" ")
                            .append(":value=\"")
                            .append("searchForm.")
                            .append(fieldName)
                            .append("Min")
                            .append("\" placeholder=\"请输入开始")
                            .append(columnDetail.getComment())
                            .append("\" type=\"datetime\" format=\"yyyy-MM-dd HH:mm:ss\" style=\"width: 100%;\">")
                            .append("</DatePicker>")
                            .append("\n</FormItem>")
                            .append("\n</i-col>")
                            .append("\n\t<i-col span=\"2\" style=\"text-align: center\">-</i-col>")
                            .append("\n\t<i-col span=\"11\">")
                            .append("\n\t<FormItem prop=\"")
                            .append(fieldName)
                            .append("Max")
                            .append("\">")
                            .append("\n\t<DatePicker @on-change=\"searchForm.")
                            .append(fieldName)
                            .append("Max")
                            .append("=$event\" ")
                            .append(":value=\"")
                            .append("searchForm.")
                            .append(fieldName)
                            .append("Max")
                            .append("\" placeholder=\"请输入结束")
                            .append(columnDetail.getComment())
                            .append("\" type=\"datetime\" format=\"yyyy-MM-dd HH:mm:ss\" style=\"width: 100%;\">")
                            .append("</DatePicker>")
                            .append("\n</FormItem>")
                            .append("\n</i-col>")
                            .append("\n</Row>");
                    break;
                default:
                    formItems.append("<FormItem label=\"")
                            .append(columnDetail.getComment())
                            .append("\">")
                            .append("<Row>")
                            .append("\n\t<i-col span=\"11\">")
                            .append("\n\t<FormItem prop=\"")
                            .append(fieldName)
                            .append("Min")
                            .append("\">")
                            .append("\n\t<InputNumber v-model=\"")
                            .append("searchForm.")
                            .append(fieldName)
                            .append("Min")
                            .append("\" placeholder=\"请输入开始")
                            .append(columnDetail.getComment())
                            .append("\" style=\"width: 100%;\"/>")
                            .append("\n</FormItem>")
                            .append("\n</i-col>")
                            .append("\n\t<i-col span=\"2\" style=\"text-align: center\">-</i-col>")
                            .append("\n\t<i-col span=\"11\">")
                            .append("\n\t<FormItem prop=\"")
                            .append(fieldName)
                            .append("Max")
                            .append("\">")
                            .append("\n\t<InputNumber v-model=\"")
                            .append("searchForm.")
                            .append(fieldName)
                            .append("Max")
                            .append("\" placeholder=\"请输入结束")
                            .append(columnDetail.getComment())
                            .append("\" style=\"width: 100%;\"/>")
                            .append("\n</FormItem>")
                            .append("\n</i-col>")
                            .append("\n</Row>");
                    break;
            }
            formItems.append("\n</FormItem>\n")
                    .append("</i-col>");
            if (index % 2 == 1) {
                formItems.append("\n</Row>\n");
            }
            index++;
        }
        if (!formItems.toString().endsWith("</Row>\n")) {
            formItems.append("\n</Row>\n");
        }
        return formItems.toString();
    }

    /**
     * 生成详情部分
     * @param tableColumns 表字段信息
     * @return
     */
    private static String generateDetailItems(TableColumns tableColumns) {
        StringBuilder detailItems = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumns.getColumnDetailList();
        int index = 0;
        for (ColumnDetail columnDetail : columnDetailList) {
            if (index % 2 == 0) {
                detailItems.append("<Row>\n\t");
            }
            detailItems.append("<i-col span=\"11\">")
                    .append("<span class=\"detail-title\">")
                    .append(columnDetail.getComment())
                    .append("：</span><span v-text=\"form.")
                    .append(columnDetail.getFieldName())
                    .append("\"></span></i-col>\n");
            if (index % 2 == 0) {
                detailItems.append("<i-col span=\"2\"></i-col>");
            }
            if (index % 2 == 1) {
                detailItems.append("\n</Row>\n");
            }
            index++;
        }
        if (!detailItems.toString().endsWith("</Row>\n")) {
            detailItems.append("\n</Row>\n");
        }
        return detailItems.toString();
    }

    /**
     * 生成addForm和editForm的所有表单属性
     * @param generator
     * @param tableColumns 表字段信息
     * @param exclusiveColumns 是否排队不需要添加和编辑的字段
     * @return
     */
    private static String generateFormFields(Generator generator, TableColumns tableColumns, Boolean exclusiveColumns) {
        StringBuilder formFields = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumns.getColumnDetailList();
        for (ColumnDetail columnDetail : columnDetailList) {
            if (exclusiveColumns && generator.getExclusiveAddEditColumns().contains(columnDetail.getName())) {
                continue;
            }
            formFields.append(columnDetail.getFieldName())
                    .append(": null,\n");
        }
        return formFields.toString();
    }

    /**
     * 生成searchForm的表单属性
     * @param tableColumns 表字段信息
     * @return
     */
    private static String generateSearchFormFields(TableColumns tableColumns) {
        StringBuilder formFields = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumns.getColumnDetailList();
        for (ColumnDetail columnDetail : columnDetailList) {
            formFields.append(columnDetail.getFieldName())
                    .append(": null,\n");
            if (!columnDetail.getJavaTypeName().equals("String")) {
                formFields.append(columnDetail.getFieldName())
                        .append("Min")
                        .append(": null, \n")
                        .append(columnDetail.getFieldName())
                        .append("Max")
                        .append(": null, \n");
            }
        }
        return formFields.toString();
    }

    /**
     * 为addForm和editForm生成表单验证规则
     * @param generator Generator对象
     * @param tableColumns 表字段信息
     * @return
     */
    private static String generateValidateRules(Generator generator, TableColumns tableColumns) {
        StringBuilder validateRules = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumns.getColumnDetailList();
        for (ColumnDetail columnDetail : columnDetailList) {
            if (!StringUtils.isInArray(generator.getExclusiveAddEditColumns().split(","), columnDetail.getName())) {
                if (columnDetail.getJavaTypeName().equals("String") && columnDetail.getNullable() == DatabaseMetaData.columnNoNulls) {
                    validateRules.append(columnDetail.getFieldName())
                            .append(": [")
                            .append("\n{type: 'string', required: true, message: '此项为必须项', trigger: 'blur'},")
                            .append("\n{type: 'string', min: 1, max: ")
                            .append(columnDetail.getColumnSize())
                            .append(", message: '必须1-")
                            .append(columnDetail.getColumnSize())
                            .append("个字符', trigger: 'blur'}\n],\n");
                } else if (columnDetail.getJavaTypeName().equals("String") && columnDetail.getNullable() == DatabaseMetaData.columnNullable) {
                    validateRules.append(columnDetail.getFieldName())
                            .append(": [")
                            .append("\n{type: 'string', min: 1, max: ")
                            .append(columnDetail.getColumnSize())
                            .append(", message: '必须1-")
                            .append(columnDetail.getColumnSize())
                            .append("个字符', trigger: 'blur'}\n],\n");
                } else if (StringUtils.isInArray(new String[]{"Byte", "Short", "Integer", "Long"}, columnDetail.getJavaTypeName())
                        && columnDetail.getNullable() == DatabaseMetaData.columnNoNulls){
                    validateRules.append(columnDetail.getFieldName())
                            .append(": [")
                            .append("\n{type: 'integer', required: true, message: '此项为必须项', trigger: 'blur, change'}\n],\n");
                } else if (StringUtils.isInArray(new String[]{"BigDecimal", "Float", "Double"}, columnDetail.getJavaTypeName())
                        && columnDetail.getNullable() == DatabaseMetaData.columnNoNulls){
                    validateRules.append(columnDetail.getFieldName())
                            .append(": [")
                            .append("\n{type: 'number', required: true, message: '此项为必须项', trigger: 'blur, change'}\n],\n");
                } else if (columnDetail.getJavaTypeName().equals("Date") && columnDetail.getNullable() == DatabaseMetaData.columnNoNulls){
                    validateRules.append(columnDetail.getFieldName())
                            .append(": [")
                            .append("\n{type: 'string', required: true, message: '此项为必须项', trigger: 'blur'}\n],\n");
                }
            }
        }
        return validateRules.toString();
    }

    /**
     * 生成视图中的Table的列信息
     * @param tableColumn 表字段信息
     * @return
     */
    private static String generateTableColumns(TableColumns tableColumn) {
        StringBuilder tableColumns = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumn.getColumnDetailList();
        for (ColumnDetail columnDetail : columnDetailList) {
            tableColumns.append("{")
                    .append("\ntitle: '")
                    .append(columnDetail.getComment())
                    .append("',")
                    .append("\nkey: '")
                    .append(columnDetail.getFieldName())
                    .append("',")
                    .append("\nminWidth: 120,")
                    .append("\nsortable: true,");
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(columnDetail.getCommentDetail())) {
                tableColumns.append("\nrenderHeader: (h, params) => {\n" +
                        "              return h('span', [\n" +
                        "                h('span', '" + columnDetail.getComment() + "'),\n" +
                        "                h('Tooltip', {\n" +
                        "                  props: {\n" +
                        "                    content: '" + columnDetail.getCommentDetail() + "',\n" +
                        "                    placement: 'top',\n" +
                        "                    transfer: true,\n" +
                        "                    maxWidth: 500\n" +
                        "                  }\n" +
                        "                }, [\n" +
                        "                  h('Icon', {\n" +
                        "                    props: {\n" +
                        "                      type: 'ios-help-circle'\n" +
                        "                    },\n" +
                        "                    style: {\n" +
                        "                      marginLeft: '3px'\n" +
                        "                    }\n" +
                        "                  })\n" +
                        "                ])\n" +
                        "              ])\n" +
                        "            }");
            }
            tableColumns.append("\n},\n");
        }
        return tableColumns.toString();
    }

    /**
     * 把用户在视图中选择的多表关联的字段信息转化成一个TableColumn对象，用于生成searchForm的formItems和formFields等
     * @param generator Generator对象
     * @param primaryTable 主表名称
     * @param columns 所有选择的字段
     * @param tableColumnsMap 数据库中所有表信息map
     * @return
     */
    private static TableColumns columnsToTableColumn(Generator generator, String primaryTable, String[] columns, Map<String, TableColumns> tableColumnsMap) {
        List<ColumnDetail> primaryColumnDetailList = new ArrayList<>();
        List<ColumnDetail> otherColumnDetailList = new ArrayList<>();
        for (String columnInfo : columns) {
            String[] tableNameAndColumn = columnInfo.split(GeneratorConstants.TABLE_COLUMN_SEPARATOR);
            String tableName = tableNameAndColumn[0];
            String columnName = tableNameAndColumn[1];
            TableColumns tableColumns = tableColumnsMap.get(tableName);
            ColumnDetail columnDetail = tableColumns.getColumnDetailMap().get(columnName);
            String field = org.apache.commons.lang3.StringUtils.uncapitalize(GeneratorUtils.tableNameToClassName(tableName, generator.getTablePrefix()))
                    + org.apache.commons.lang3.StringUtils.capitalize(columnDetail.getFieldName());
            ColumnDetail cd = new ColumnDetail();
            cd.setComment(columnDetail.getComment());
            cd.setCommentDetail(columnDetail.getCommentDetail());
            cd.setFieldName(field);
            cd.setJavaTypeName(columnDetail.getJavaTypeName());
            if (tableName.equals(primaryTable)) {
                primaryColumnDetailList.add(cd);
            } else {
                otherColumnDetailList.add(cd);
            }
        }
        TableColumns tableColumns = new TableColumns();
        primaryColumnDetailList.addAll(otherColumnDetailList);
        tableColumns.setColumnDetailList(primaryColumnDetailList);
        return tableColumns;
    }
}
