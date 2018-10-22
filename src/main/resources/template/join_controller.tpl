package top.zywork.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import top.zywork.common.BeanUtils;
import top.zywork.common.BindingResultUtils;
import top.zywork.common.StringUtils;
import top.zywork.dto.PagerDTO;
import top.zywork.dto.{zywork.beanName}{zywork.dtoSuffix};
import top.zywork.exception.ServiceException;
import top.zywork.query.{zywork.beanName}{zywork.querySuffix};
import top.zywork.service.{zywork.beanName}{zywork.serviceSuffix};
import top.zywork.vo.ResponseStatusVO;
import top.zywork.vo.PagerVO;
import top.zywork.vo.{zywork.beanName}{zywork.voSuffix};

import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping("one/{id}")
    public ResponseStatusVO getById(@PathVariable("id") Long id) {
        ResponseStatusVO statusVO = new ResponseStatusVO();
        {zywork.beanName}{zywork.voSuffix} {zywork.beanNameLowerCase}{zywork.voSuffix} = new {zywork.beanName}{zywork.voSuffix}();
        try {
            Object obj = {zywork.beanNameLowerCase}{zywork.serviceSuffix}.getById(id);
            if (obj != null) {
                {zywork.beanNameLowerCase}{zywork.voSuffix} = BeanUtils.copy(obj, {zywork.beanName}{zywork.voSuffix}.class);
            }
            statusVO.okStatus(200, "查询成功", {zywork.beanNameLowerCase}{zywork.voSuffix});
        } catch (ServiceException e) {
            logger.error("返回单个对象JSON数据失败：{}", e.getMessage());
            statusVO.errorStatus(500, "查询失败", null);
        }
        return statusVO;
    }

    @GetMapping("all")
    public ResponseStatusVO listAll() {
        ResponseStatusVO statusVO = new ResponseStatusVO();
        List<{zywork.beanName}{zywork.voSuffix}> {zywork.beanNameLowerCase}{zywork.voSuffix}List = new ArrayList<>();
        try {
            List<Object> objectList = {zywork.beanNameLowerCase}{zywork.serviceSuffix}.listAll();
            {zywork.beanNameLowerCase}VOList =  BeanUtils.copy(objectList, {zywork.beanName}{zywork.voSuffix}.class);
            statusVO.okStatus(200, "查询成功", {zywork.beanNameLowerCase}VOList);
        } catch (ServiceException e) {
            logger.error("返回所有对象JSON数据失败：{}", e.getMessage());
            statusVO.errorStatus(500, "查询失败", null);
        }
        return statusVO;
    }

    @RequestMapping("pager-cond")
    public ResponseStatusVO listPageByCondition(@RequestBody {zywork.beanName}{zywork.querySuffix} {zywork.beanNameLowerCase}{zywork.querySuffix}) {
        ResponseStatusVO statusVO = new ResponseStatusVO();
        PagerVO pagerVO = new PagerVO();
        try {
            PagerDTO pagerDTO = {zywork.beanNameLowerCase}{zywork.serviceSuffix}.listPageByCondition({zywork.beanNameLowerCase}{zywork.querySuffix});
            pagerVO = BeanUtils.copy(pagerDTO, PagerVO.class);
            pagerVO.setRows(BeanUtils.copyList(pagerDTO.getRows(), {zywork.beanName}{zywork.voSuffix}.class));
            statusVO.okStatus(200, "查询成功", pagerVO);
        } catch (ServiceException e) {
            logger.error("返回指定条件的分页对象JSON数据失败：{}", e.getMessage());
            statusVO.errorStatus(500, "查询失败", null);
        }
        return statusVO;
    }

    @Autowired
    public void set{zywork.beanName}{zywork.serviceSuffix}({zywork.beanName}{zywork.serviceSuffix} {zywork.beanNameLowerCase}{zywork.serviceSuffix}) {
        this.{zywork.beanNameLowerCase}{zywork.serviceSuffix} = {zywork.beanNameLowerCase}{zywork.serviceSuffix};
    }
}
