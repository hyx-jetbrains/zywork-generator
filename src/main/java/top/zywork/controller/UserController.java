package top.zywork.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zywork.bean.UserVO;
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

    @RequestMapping("pager-cond")
    public ResponseStatusVO listPageByCondition() {
        ResponseStatusVO statusVO = new ResponseStatusVO();
        PagerVO pagerVO = new PagerVO();
        try {
            pagerVO.setTotal(10L);
            List<Object> userVOList = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                UserVO userVO = new UserVO();
                userVO.setId((long) i + 1);
                userVO.setAccountName("testhhhhhhhhhhhhhhhhh");
                userVO.setIsActive((byte) 0);
                userVOList.add(userVO);
            }
            pagerVO.setRows(userVOList);
            statusVO.okStatus(200, "查询成功", pagerVO);
        } catch (ServiceException e) {
            logger.error("返回指定条件的分页对象JSON数据失败：{}", e.getMessage());
            statusVO.errorStatus(500, "查询失败", null);
        }
        return statusVO;
    }
}
