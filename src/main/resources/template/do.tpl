package top.zywork.dos;

import java.math.BigDecimal;
import java.util.Date;

/**
 * {zywork.beanName}{zywork.suffix}数据对象实体类<br/>
 *
 * 创建于{zywork.createDate}<br/>
 *
 * @author {zywork.author}
 * @version 1.0
 */
public class {zywork.beanName}{zywork.suffix} extends BaseDO {

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
