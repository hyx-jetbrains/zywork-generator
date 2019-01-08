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
import top.zywork.query.{zywork.beanName}{zywork.querySuffix};
import top.zywork.service.{zywork.beanName}{zywork.serviceSuffix};
import top.zywork.vo.ResponseStatusVO;
import top.zywork.vo.PagerVO;
import top.zywork.vo.{zywork.beanName}{zywork.voSuffix};

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

    @PostMapping("admin/save")
    public ResponseStatusVO save(@RequestBody @Validated {zywork.beanName}{zywork.voSuffix} {zywork.beanNameLowerCase}{zywork.voSuffix}, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseStatusVO.dataError(BindingResultUtils.errorString(bindingResult), null);
        }
        {zywork.beanNameLowerCase}{zywork.serviceSuffix}.save(BeanUtils.copy({zywork.beanNameLowerCase}{zywork.voSuffix}, {zywork.beanName}{zywork.dtoSuffix}.class));
        return ResponseStatusVO.ok("添加成功", null);
    }

    @PostMapping("admin/batch-save")
    public ResponseStatusVO saveBatch(@RequestBody @Validated List<{zywork.beanName}{zywork.voSuffix}> {zywork.beanNameLowerCase}{zywork.voSuffix}List, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseStatusVO.dataError(BindingResultUtils.errorString(bindingResult), null);
        }
        {zywork.beanNameLowerCase}{zywork.serviceSuffix}.saveBatch(BeanUtils.copyListObj({zywork.beanNameLowerCase}{zywork.voSuffix}List, {zywork.beanName}{zywork.dtoSuffix}.class));
        return ResponseStatusVO.ok("批量添加成功", null);
    }

    @GetMapping("admin/remove/{id}")
    public ResponseStatusVO removeById(@PathVariable("id") Long id) {
        {zywork.beanNameLowerCase}{zywork.serviceSuffix}.removeById(id);
        return ResponseStatusVO.ok("删除成功", null);
    }

    @PostMapping("admin/batch-remove")
    public ResponseStatusVO removeByIds(@RequestBody String[] ids) {
        {zywork.beanNameLowerCase}{zywork.serviceSuffix}.removeByIds(StringUtils.strArrayToLongArray(ids));
        return ResponseStatusVO.ok("批量删除成功", null);
    }

    @PostMapping("admin/update")
    public ResponseStatusVO update(@RequestBody @Validated {zywork.beanName}{zywork.voSuffix} {zywork.beanNameLowerCase}{zywork.voSuffix}, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseStatusVO.dataError(BindingResultUtils.errorString(bindingResult), null);
        }
        int updateRows = {zywork.beanNameLowerCase}{zywork.serviceSuffix}.update(BeanUtils.copy({zywork.beanNameLowerCase}{zywork.voSuffix}, {zywork.beanName}{zywork.dtoSuffix}.class));
        if (updateRows == 1) {
            return ResponseStatusVO.ok("更新成功", null);
        } else {
            return ResponseStatusVO.dataError("数据版本号有问题，在此更新前数据已被更新", null);
        }
    }

    @PostMapping("admin/batch-update")
    public ResponseStatusVO updateBatch(@RequestBody @Validated List<{zywork.beanName}{zywork.voSuffix}> {zywork.beanNameLowerCase}{zywork.voSuffix}List, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseStatusVO.dataError(BindingResultUtils.errorString(bindingResult), null);
        }
        {zywork.beanNameLowerCase}{zywork.serviceSuffix}.updateBatch(BeanUtils.copyListObj({zywork.beanNameLowerCase}{zywork.voSuffix}List, {zywork.beanName}{zywork.dtoSuffix}.class));
        return ResponseStatusVO.ok("批量更新成功", null);
    }

    @PostMapping("admin/active")
    public ResponseStatusVO active(@RequestBody {zywork.beanName}{zywork.voSuffix} {zywork.beanNameLowerCase}{zywork.voSuffix}) {
        {zywork.beanNameLowerCase}{zywork.serviceSuffix}.update(BeanUtils.copy({zywork.beanNameLowerCase}{zywork.voSuffix}, {zywork.beanName}{zywork.dtoSuffix}.class));
        return ResponseStatusVO.ok("激活或冻结成功", null);
    }

    @PostMapping("admin/batch-active")
    public ResponseStatusVO activeBatch(@RequestBody @Validated List<{zywork.beanName}{zywork.voSuffix}> {zywork.beanNameLowerCase}{zywork.voSuffix}List) {
        {zywork.beanNameLowerCase}{zywork.serviceSuffix}.updateBatch(BeanUtils.copyListObj({zywork.beanNameLowerCase}{zywork.voSuffix}List, {zywork.beanName}{zywork.dtoSuffix}.class));
        return ResponseStatusVO.ok("批量激活或冻结成功", null);
    }

    @GetMapping("admin/one/{id}")
    public ResponseStatusVO getById(@PathVariable("id") Long id) {
        {zywork.beanName}{zywork.voSuffix} {zywork.beanNameLowerCase}{zywork.voSuffix} = new {zywork.beanName}{zywork.voSuffix}();
        Object obj = {zywork.beanNameLowerCase}{zywork.serviceSuffix}.getById(id);
        if (obj != null) {
            {zywork.beanNameLowerCase}{zywork.voSuffix} = BeanUtils.copy(obj, {zywork.beanName}{zywork.voSuffix}.class);
        }
        return ResponseStatusVO.ok("查询成功", {zywork.beanNameLowerCase}{zywork.voSuffix});
    }

    @GetMapping("admin/all")
    public ResponseStatusVO listAll() {
        PagerDTO pagerDTO = {zywork.beanNameLowerCase}{zywork.serviceSuffix}.listAll();
        PagerVO pagerVO = BeanUtils.copy(pagerDTO, PagerVO.class);
        pagerVO.setRows(BeanUtils.copyList(pagerDTO.getRows(), {zywork.beanName}{zywork.voSuffix}.class));
        return ResponseStatusVO.ok("查询成功", pagerVO);
    }

    @PostMapping("admin/pager-cond")
    public ResponseStatusVO listPageByCondition(@RequestBody {zywork.beanName}{zywork.querySuffix} {zywork.beanNameLowerCase}{zywork.querySuffix}) {
        PagerDTO pagerDTO = {zywork.beanNameLowerCase}{zywork.serviceSuffix}.listPageByCondition({zywork.beanNameLowerCase}{zywork.querySuffix});
        PagerVO pagerVO = BeanUtils.copy(pagerDTO, PagerVO.class);
        pagerVO.setRows(BeanUtils.copyList(pagerDTO.getRows(), {zywork.beanName}{zywork.voSuffix}.class));
        return ResponseStatusVO.ok("查询成功", pagerVO);
    }

    @Autowired
    public void set{zywork.beanName}{zywork.serviceSuffix}({zywork.beanName}{zywork.serviceSuffix} {zywork.beanNameLowerCase}{zywork.serviceSuffix}) {
        this.{zywork.beanNameLowerCase}{zywork.serviceSuffix} = {zywork.beanNameLowerCase}{zywork.serviceSuffix};
    }
}
