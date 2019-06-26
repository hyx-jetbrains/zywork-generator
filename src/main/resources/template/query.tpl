package top.zywork.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * {zywork.beanName}{zywork.suffix}查询对象类<br/>
 *
 * 创建于{zywork.createDate}<br/>
 *
 * @author {zywork.author}
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class {zywork.beanName}{zywork.suffix} extends PageQuery {

    private static final long serialVersionUID = {zywork.serialVersionId}L;

    {zywork.fields}
}