package top.zywork.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zywork.bean.UserVO;
import top.zywork.common.BindingResultUtils;
import top.zywork.common.DateUtils;
import top.zywork.exception.ServiceException;
import top.zywork.vo.PagerVO;
import top.zywork.vo.ResponseStatusVO;

import java.util.ArrayList;
import java.util.List;

/**
 * UserController控制器类<br/>
 *
 * 创建于2018-09-28<br/>
 *
 * @author http://zywork.top 王振宇
 * @version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping("save")
    public ResponseStatusVO save(@RequestBody @Validated UserVO userVO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseStatusVO.dataError(BindingResultUtils.errorString(bindingResult), null);
        }
        try {
            return ResponseStatusVO.ok("添加成功", null);
        } catch (ServiceException e) {
            logger.error("添加失败：{}", e.getMessage());
            return ResponseStatusVO.error("添加失败", null);
        }
    }

    @PostMapping("pager-cond")
    public ResponseStatusVO listPageByCondition() {
        PagerVO pagerVO = new PagerVO();
        try {
            pagerVO.setTotal(10L);
            List<Object> userVOList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                UserVO userVO = new UserVO();
                userVO.setId((long) i + 1);
                userVO.setAccountName("user" + (i + 1));
                userVO.setAge(i + 1);
                userVO.setCreateTime(DateUtils.currentDate());
                userVO.setIsActive((byte) 0);
                userVOList.add(userVO);
            }
            pagerVO.setRows(userVOList);
            return ResponseStatusVO.ok("查询成功", pagerVO);
        } catch (ServiceException e) {
            logger.error("返回指定条件的分页对象JSON数据失败：{}", e.getMessage());
            return ResponseStatusVO.error("查询失败", null);
        }
    }
}
