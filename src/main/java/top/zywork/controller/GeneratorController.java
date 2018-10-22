package top.zywork.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zywork.bean.Generator;
import top.zywork.bean.JoinInfo;
import top.zywork.bean.SingleInfo;
import top.zywork.bean.TableColumn;
import top.zywork.common.FileUtils;
import top.zywork.constant.TemplateConstants;
import top.zywork.generator.CodeGenerator;
import top.zywork.vo.ResponseStatusVO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * 代码生成控制器<br/>
 *
 * 创建于2018-03-22<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
@RestController
@RequestMapping("/generator")
public class GeneratorController {

    /**
     * 生成选中的单表的数据，可支持同时选中多个单表
     * @param singleInfo
     * @param request
     * @return
     */
    @PostMapping("codes")
    public ResponseStatusVO generateCodes(@RequestBody SingleInfo singleInfo, HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        Generator generator = (Generator) servletContext.getAttribute("generator");
        List<TableColumn> tableColumnList = (List<TableColumn>) servletContext.getAttribute("tableColumnList");
        for (String tableName : singleInfo.getTables()) {
            for (TableColumn tableColumn : tableColumnList) {
                if (tableName.equals(tableColumn.getTableName())) {
                    CodeGenerator.generateCode(generator, tableColumn, singleInfo.getCodeTypes());
                }
            }
        }
        ResponseStatusVO statusVO = new ResponseStatusVO();
        statusVO.okStatus(200, "成功生成所选表的代码！", null);
        return statusVO;
    }

    @PostMapping("join-code")
    public ResponseStatusVO generateJoinCode(@RequestBody JoinInfo joinInfo, HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        Generator generator = (Generator) servletContext.getAttribute("generator");
        String dir = generator.getSaveBaseDir() + File.separator + generator.getJavaSrcDir() + File.separator
                + generator.getBasePackage().replace(".", File.separator) + File.separator + generator.getDoPackage();
        String fileName = joinInfo.getBeanName() + generator.getDoSuffix();
        ResponseStatusVO statusVO = new ResponseStatusVO();
        if (FileUtils.exist(dir, fileName)) {
            statusVO.errorStatus(500, "已经存在指定名称的实体类，请重新填写实体类名称后再生成代码", null);
        } else {
            List<TableColumn> tableColumnList = (List<TableColumn>) servletContext.getAttribute("tableColumnList");
            CodeGenerator.generateJoinCode(joinInfo.getBeanName(), joinInfo.getRequestMapping(), generator, joinInfo.getPrimaryTable(), joinInfo.getColumns(), tableColumnList, joinInfo.getWhereClause(), joinInfo.getCodeTypes());
            statusVO.okStatus(200, "成功生成所选关联表的代码！", null);
        }
        return statusVO;
    }

}
