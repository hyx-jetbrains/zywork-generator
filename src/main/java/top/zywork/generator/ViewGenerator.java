package top.zywork.generator;

import top.zywork.bean.ColumnDetail;
import top.zywork.bean.Generator;
import top.zywork.bean.TableColumn;
import top.zywork.common.GeneratorUtils;
import top.zywork.common.StringUtils;
import top.zywork.constant.TemplateConstants;

import java.sql.DatabaseMetaData;
import java.util.List;

/**
 * View视图自动生成代码封装类<br/>
 * <p>
 * 创建于2018-03-12<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class ViewGenerator {

    public static void generateView(Generator generator, TableColumn tableColumn) {
        String beanName = GeneratorUtils.tableNameToClassName(tableColumn.getTableName(), generator.getTablePrefix());
        String moduleName = GeneratorUtils.getModuleName(tableColumn.getTableName(), generator.getTablePrefix());
        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir());
        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_TEMPLATE);
        fileContent = fileContent.replace(TemplateConstants.FORM_ITEMS, generateFormItems(generator, tableColumn))
                .replace(TemplateConstants.SEARCH_FORM_ITEMS, generateSearchFormItems(tableColumn))
                .replace(TemplateConstants.DETAIL_ITEMS, generateDetailItems(tableColumn))
                .replace(TemplateConstants.BEAN_NAME_LOWER_CASE, beanName)
                .replace(TemplateConstants.MAPPING_URL, moduleName)
                .replace(TemplateConstants.FORM_FIELDS, generateFormFields(tableColumn))
                .replace(TemplateConstants.FORM_VALIDATE_RULES, generateValidateRules(generator, tableColumn))
                .replace(TemplateConstants.SEARCH_FORM_FIELDS, generateSearchFormFields(tableColumn))
                .replace(TemplateConstants.TABLE_COLUMNS, generateTableColumns(tableColumn));
        GeneratorUtils.writeFile(fileContent, resDir, beanName + ".vue");
    }

//    public static void generateJoinView(String beanName, String mappingUrl, Generator generator, String primaryTable, String[] columns, List<TableColumn> tableColumnList) {
//        String resDir = GeneratorUtils.createResDir(generator, generator.getViewFileDir());
//        String fileContent = GeneratorUtils.readTemplate(generator, TemplateConstants.VIEW_TEMPLATE);
//        fileContent = fileContent.replace(TemplateConstants.FORM_ITEMS, generateFormItems(generator, tableColumn))
//                .replace(TemplateConstants.SEARCH_FORM_ITEMS, generateSearchFormItems(tableColumn))
//                .replace(TemplateConstants.DETAIL_ITEMS, generateDetailItems(tableColumn))
//                .replace(TemplateConstants.BEAN_NAME_LOWER_CASE, beanName)
//                .replace(TemplateConstants.MAPPING_URL, mappingUrl)
//                .replace(TemplateConstants.FORM_FIELDS, generateFormFields(tableColumn))
//                .replace(TemplateConstants.FORM_VALIDATE_RULES, generateValidateRules(generator, tableColumn))
//                .replace(TemplateConstants.SEARCH_FORM_FIELDS, generateSearchFormFields(tableColumn))
//                .replace(TemplateConstants.TABLE_COLUMNS, generateTableColumns(tableColumn));
//        GeneratorUtils.writeFile(fileContent, resDir, beanName + ".vue");
//    }

    private static String generateFormItems(Generator generator, TableColumn tableColumn) {
        StringBuilder formItems = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumn.getColumnDetails();
        for (ColumnDetail columnDetail : columnDetailList) {
            if (!StringUtils.isInArray(generator.getExclusiveAddEditColumns().split(","), columnDetail.getName())) {
                formItems.append(formItem(columnDetail.getComment(), columnDetail.getFieldName(), columnDetail.getJavaTypeName()));
            }
        }
        return formItems.toString();
    }

    private static String generateJoinFormItems(Generator generator, TableColumn tableColumn) {
        StringBuilder formItems = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumn.getColumnDetails();
        for (ColumnDetail columnDetail : columnDetailList) {
            if (!StringUtils.isInArray(generator.getExclusiveAddEditColumns().split(","), columnDetail.getName())) {
                formItems.append(formItem(columnDetail.getComment(), columnDetail.getFieldName(), columnDetail.getJavaTypeName()));
            }
        }
        return formItems.toString();
    }

    private static String formItem(String title, String fieldName, String javaTypeName) {
        StringBuilder formItem = new StringBuilder();
        formItem.append("<FormItem label=\"")
                .append(title)
                .append("\" prop=\"")
                .append(fieldName)
                .append("\">");
        if (!javaTypeName.equals("Date")) {
            formItem.append("\n\t<Input v-model=\"")
                    .append("form.")
                    .append(fieldName)
                    .append("\"/>");
        } else {
            formItem.append("\n\t<DatePicker v-model=\"")
                    .append("form.")
                    .append(fieldName)
                    .append("\" type=\"datetime\" format=\"yyyy-MM-dd HH:mm\" style=\"width: 100%;\">")
                    .append("</DatePicker>");
        }
        formItem.append("\n</FormItem>");
        return formItem.toString();
    }

    private static String generateSearchFormItems(TableColumn tableColumn) {
        StringBuilder formItems = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumn.getColumnDetails();
        for (ColumnDetail columnDetail : columnDetailList) {
            String fieldName = columnDetail.getFieldName();
            String javaType = columnDetail.getJavaTypeName();
            if (javaType.equals("String")) {
                formItems.append("<FormItem label=\"")
                        .append(columnDetail.getComment())
                        .append("\" prop=\"")
                        .append(fieldName)
                        .append("\">")
                        .append("\n\t<Input v-model=\"")
                        .append("form.")
                        .append(fieldName)
                        .append("\"/>");
            } else {
                if (javaType.equals("Date")) {
                    formItems.append("<FormItem label=\"")
                            .append(columnDetail.getComment())
                            .append("\">")
                            .append("<Row>")
                            .append("\n\t<i-col span=\"11\">")
                            .append("\n\t<FormItem prop=\"")
                            .append(fieldName)
                            .append("Min")
                            .append("\">")
                            .append("\n\t<DatePicker v-model=\"")
                            .append("form.")
                            .append(fieldName)
                            .append("Min")
                            .append("\" type=\"datetime\" format=\"yyyy-MM-dd HH:mm\" style=\"width: 100%;\">")
                            .append("</DatePicker>")
                            .append("\n</FormItem>")
                            .append("\n</i-col>")
                            .append("<i-col span=\"2\" style=\"text-align: center\">-</i-col>")
                            .append("\n\t<i-col span=\"11\">")
                            .append("\n\t<FormItem prop=\"")
                            .append(fieldName)
                            .append("Max")
                            .append("\">")
                            .append("\n\t<DatePicker v-model=\"")
                            .append("form.")
                            .append(fieldName)
                            .append("Max")
                            .append("\" type=\"datetime\" format=\"yyyy-MM-dd HH:mm\" style=\"width: 100%;\">")
                            .append("</DatePicker>")
                            .append("\n</FormItem>")
                            .append("\n</i-col>")
                            .append("\n</Row>");
                } else {
                    formItems.append("<FormItem label=\"")
                            .append(columnDetail.getComment())
                            .append("\">")
                            .append("<Row>")
                            .append("\n\t<i-col span=\"11\">")
                            .append("\n\t<FormItem prop=\"")
                            .append(fieldName)
                            .append("Min")
                            .append("\">")
                            .append("\n\t<Input v-model=\"")
                            .append("form.")
                            .append(fieldName)
                            .append("Min")
                            .append("\"/>")
                            .append("\n</FormItem>")
                            .append("\n</i-col>")
                            .append("<i-col span=\"2\" style=\"text-align: center\">-</i-col>")
                            .append("\n\t<i-col span=\"11\">")
                            .append("\n\t<FormItem prop=\"")
                            .append(fieldName)
                            .append("Max")
                            .append("\">")
                            .append("\n\t<Input v-model=\"")
                            .append("form.")
                            .append(fieldName)
                            .append("Max")
                            .append("\"/>")
                            .append("\n</FormItem>")
                            .append("\n</i-col>")
                            .append("\n</Row>");
                }
            }
            formItems.append("\n</FormItem>\n");
        }
        return formItems.toString();
    }

    private static String generateDetailItems(TableColumn tableColumn) {
        StringBuilder detailItems = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumn.getColumnDetails();
        for (ColumnDetail columnDetail : columnDetailList) {
            detailItems.append("<p>")
                    .append(columnDetail.getComment())
                    .append(": ")
                    .append("<span v-text=\"form.")
                    .append(columnDetail.getFieldName())
                    .append("\"></span></p>\n");
        }
        return detailItems.toString();
    }

    private static String generateFormFields(TableColumn tableColumn) {
        StringBuilder formFields = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumn.getColumnDetails();
        for (ColumnDetail columnDetail : columnDetailList) {
            formFields.append(columnDetail.getFieldName())
                    .append(": '',\n");
        }
        return formFields.toString();
    }

    private static String generateSearchFormFields(TableColumn tableColumn) {
        StringBuilder formFields = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumn.getColumnDetails();
        for (ColumnDetail columnDetail : columnDetailList) {
            formFields.append(columnDetail.getFieldName())
                    .append(": '',\n");
            if (!columnDetail.getJavaTypeName().equals("String")) {
                formFields.append(columnDetail.getFieldName())
                        .append("Min")
                        .append(": '', \n")
                        .append(columnDetail.getFieldName())
                        .append("Max")
                        .append(": '', \n");
            }
        }
        return formFields.toString();
    }

    private static String generateValidateRules(Generator generator, TableColumn tableColumn) {
        StringBuilder validateRules = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumn.getColumnDetails();
        for (ColumnDetail columnDetail : columnDetailList) {
            if (!StringUtils.isInArray(generator.getExclusiveAddEditColumns().split(","), columnDetail.getName())) {
                if (columnDetail.getJavaTypeName().equals("String") && columnDetail.getNullable() == DatabaseMetaData.columnNoNulls) {
                    validateRules.append(columnDetail.getFieldName())
                            .append(": [")
                            .append("\n{required: true, message: '此项为必须项', trigger: 'blur'},")
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
                } else {
                    validateRules.append(columnDetail.getFieldName())
                            .append(": [")
                            .append("\n{required: true, message: '此项为必须项', trigger: 'blur'}\n],\n");
                }
            }
        }
        return validateRules.toString();
    }

    private static String generateTableColumns(TableColumn tableColumn) {
        StringBuilder tableColumns = new StringBuilder();
        List<ColumnDetail> columnDetailList = tableColumn.getColumnDetails();
        for (ColumnDetail columnDetail : columnDetailList) {
            tableColumns.append("{")
                    .append("\ntitle: '")
                    .append(columnDetail.getComment())
                    .append("',")
                    .append("\nkey: '")
                    .append(columnDetail.getFieldName())
                    .append("',")
                    .append("\nwidth: 120,")
                    .append("\nsortable: true\n},\n");
        }
        return tableColumns.toString();
    }
}
