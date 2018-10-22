package top.zywork.bean;

/**
 * 与iview的select对应的类<br/>
 * 创建于2018-07-13<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
public class IViewSelect {

    private String value;
    private String label;
    private Boolean disabled;

    public IViewSelect() {}

    public IViewSelect(String value, String label, Boolean disabled) {
        this.value = value;
        this.label = label;
        this.disabled = disabled;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
}
