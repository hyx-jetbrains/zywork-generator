package top.zywork.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * {zywork.beanName}{zywork.suffix}数据传输对象类<br/>
 *
 * 创建于{zywork.createDate}<br/>
 *
 * @author {zywork.author}
 * @version 1.0
 */
public class {zywork.beanName}{zywork.suffix} extends BaseDTO {

    private static final long serialVersionUID = {zywork.serialVersionId}L;

    {zywork.fields}
    public {zywork.beanName}{zywork.suffix} () {}

    public {zywork.beanName}{zywork.suffix} ({zywork.constructorParams}) {
        {zywork.constructor}
    }

    {zywork.fieldsGetterSetter}
    @Override
    public String toString() {
        return "{zywork.beanName}{zywork.suffix} {" +
                {zywork.toString}" }";
    }

}