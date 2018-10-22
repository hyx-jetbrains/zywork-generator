package top.zywork.bean;

/**
 * 关联表代码生成时所需要的信息<br/>
 *
 * 创建于2018-09-28<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class JoinInfo {
    private String beanName;
    private String requestMapping;
    private String primaryTable;
    private String whereClause;
    private String[] columns;
    private String[] codeTypes;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getRequestMapping() {
        return requestMapping;
    }

    public void setRequestMapping(String requestMapping) {
        this.requestMapping = requestMapping;
    }

    public String getPrimaryTable() {
        return primaryTable;
    }

    public void setPrimaryTable(String primaryTable) {
        this.primaryTable = primaryTable;
    }

    public String getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public String[] getCodeTypes() {
        return codeTypes;
    }

    public void setCodeTypes(String[] codeTypes) {
        this.codeTypes = codeTypes;
    }
}
