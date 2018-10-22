package top.zywork.query;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * {zywork.beanName}{zywork.suffix}查询对象类<br/>
 *
 * 创建于{zywork.createDate}<br/>
 *
 * @author {zywork.author}
 * @version 1.0
 */
public class {zywork.beanName}{zywork.suffix} extends PageQuery {

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