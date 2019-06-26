package top.zywork.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class {zywork.beanName}{zywork.suffix} extends BaseDTO {

    private static final long serialVersionUID = {zywork.serialVersionId}L;

    {zywork.fields}
}