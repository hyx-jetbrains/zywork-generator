package top.zywork.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.zywork.vo.BaseVO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * UserVO值对象类<br/>
 *
 * 创建于2018-09-28<br/>
 *
 * @author http://zywork.top 王振宇
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO extends BaseVO {

    private static final long serialVersionUID = -9223372036307011640L;

	/**
	 * 编号
	 */
	private Long id;
	/**
	 * 邮箱
	 */
	@Size(min = 0, max = 100, message = "必须小于100个字符")
	private String email;
	/**
	 * 手机号
	 */
	@Size(min = 0, max = 11, message = "必须小于11个字符")
	private String phone;
	/**
	 * 账户名
	 */
	@Size(min = 0, max = 20, message = "必须小于20个字符")
	private String accountName;
	/**
	 * 密码
	 */
	@NotBlank(message = "此项是必须项")
	@Size(min = 1, max = 50, message = "必须是1-50个字符")
	private String password;
	/**
	 * 加密盐值
	 */
	@Size(min = 0, max = 200, message = "必须小于200个字符")
	private String salt;
	/**
	 * 年龄
	 */
	@NotNull(message = "此项是必须项")
	private Integer age;
	/**
	 * 创建时间
	 */
	@NotNull(message = "此项是必须项")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date updateTime;
	/**
	 * 是否激活
	 */
	private Byte isActive;

}
