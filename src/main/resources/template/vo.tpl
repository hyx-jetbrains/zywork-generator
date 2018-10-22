package top.zywork.vo;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * {zywork.beanName}{zywork.suffix}值对象类<br/>
 *
 * 创建于{zywork.createDate}<br/>
 *
 * @author {zywork.author}
 * @version 1.0
 */
public class {zywork.beanName}{zywork.suffix} extends BaseVO {

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