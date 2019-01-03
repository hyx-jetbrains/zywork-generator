package top.zywork.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import top.zywork.common.BeanUtils;
import top.zywork.dto.PagerDTO;
import top.zywork.enums.ResponseStatusEnum;
import top.zywork.exception.ServiceException;
import top.zywork.query.{zywork.beanName}{zywork.querySuffix};
import top.zywork.service.{zywork.beanName}{zywork.serviceSuffix};
import top.zywork.vo.ResponseStatusVO;
import top.zywork.vo.PagerVO;
import top.zywork.vo.{zywork.beanName}{zywork.voSuffix};

/**
 * {zywork.beanName}{zywork.suffix}控制器类<br/>
 *
 * 创建于{zywork.createDate}<br/>
 *
 * @author {zywork.author}
 * @version 1.0
 */
@RestController
@RequestMapping("/{zywork.moduleName}")
public class {zywork.beanName}{zywork.suffix} extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger({zywork.beanName}{zywork.suffix}.class);

    private {zywork.beanName}{zywork.serviceSuffix} {zywork.beanNameLowerCase}{zywork.serviceSuffix};

    @GetMapping("admin/multi/{id}")
    public ResponseStatusVO listById(@PathVariable("id") Long id) {
        ResponseStatusVO statusVO = new ResponseStatusVO();
        try {
            PagerDTO pagerDTO = {zywork.beanNameLowerCase}{zywork.serviceSuffix}.listById(id);
            PagerVO pagerVO = BeanUtils.copy(pagerDTO, PagerVO.class);
            pagerVO.setRows(BeanUtils.copyList(pagerDTO.getRows(), {zywork.beanName}{zywork.voSuffix}.class));
            statusVO.okStatus(ResponseStatusEnum.OK.getCode(), "查询成功", pagerVO);
        } catch (ServiceException e) {
            logger.error("返回单个对象JSON数据失败：{}", e.getMessage());
            statusVO.errorStatus(ResponseStatusEnum.ERROR.getCode(), "查询失败", null);
        }
        return statusVO;
    }

    @GetMapping("admin/all")
    public ResponseStatusVO listAll() {
        ResponseStatusVO statusVO = new ResponseStatusVO();
        try {
            PagerDTO pagerDTO = {zywork.beanNameLowerCase}{zywork.serviceSuffix}.listAll();
            PagerVO pagerVO = BeanUtils.copy(pagerDTO, PagerVO.class);
            pagerVO.setRows(BeanUtils.copyList(pagerDTO.getRows(), {zywork.beanName}{zywork.voSuffix}.class));
            statusVO.okStatus(ResponseStatusEnum.OK.getCode(), "查询成功", pagerVO);
        } catch (ServiceException e) {
            logger.error("返回所有对象JSON数据失败：{}", e.getMessage());
            statusVO.errorStatus(ResponseStatusEnum.ERROR.getCode(), "查询失败", null);
        }
        return statusVO;
    }

    @PostMapping("admin/pager-cond")
    public ResponseStatusVO listPageByCondition(@RequestBody {zywork.beanName}{zywork.querySuffix} {zywork.beanNameLowerCase}{zywork.querySuffix}) {
        ResponseStatusVO statusVO = new ResponseStatusVO();
        try {
            PagerDTO pagerDTO = {zywork.beanNameLowerCase}{zywork.serviceSuffix}.listPageByCondition({zywork.beanNameLowerCase}{zywork.querySuffix});
            PagerVO pagerVO = BeanUtils.copy(pagerDTO, PagerVO.class);
            pagerVO.setRows(BeanUtils.copyList(pagerDTO.getRows(), {zywork.beanName}{zywork.voSuffix}.class));
            statusVO.okStatus(ResponseStatusEnum.OK.getCode(), "查询成功", pagerVO);
        } catch (ServiceException e) {
            logger.error("返回指定条件的分页对象JSON数据失败：{}", e.getMessage());
            statusVO.errorStatus(ResponseStatusEnum.ERROR.getCode(), "查询失败", null);
        }
        return statusVO;
    }

    @Autowired
    public void set{zywork.beanName}{zywork.serviceSuffix}({zywork.beanName}{zywork.serviceSuffix} {zywork.beanNameLowerCase}{zywork.serviceSuffix}) {
        this.{zywork.beanNameLowerCase}{zywork.serviceSuffix} = {zywork.beanNameLowerCase}{zywork.serviceSuffix};
    }
}
