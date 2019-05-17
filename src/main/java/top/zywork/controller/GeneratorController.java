package top.zywork.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zywork.bean.Generator;
import top.zywork.bean.JoinInfo;
import top.zywork.bean.SingleInfo;
import top.zywork.bean.TableColumns;
import top.zywork.common.FileUtils;
import top.zywork.generator.CodeGenerator;
import top.zywork.vo.ResponseStatusVO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.Map;

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
     *
     * @param singleInfo
     * @param request
     * @return
     */
    @PostMapping("codes")
    @SuppressWarnings({"unchecked"})
    public ResponseStatusVO generateCodes(@RequestBody SingleInfo singleInfo, HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        Generator generator = (Generator) servletContext.getAttribute("generator");
        Map<String, TableColumns> tableColumnsMap = (Map<String, TableColumns>) servletContext.getAttribute(TableController.TABLE_COLUMNS_MAP);
        for (String tableName : singleInfo.getTables()) {
            CodeGenerator.generateCode(generator, tableColumnsMap.get(tableName), singleInfo.getCodeTypes());
        }
        return ResponseStatusVO.ok("成功生成所选表的代码！", null);
    }

    @PostMapping("join-code")
    @SuppressWarnings({"unchecked"})
    public ResponseStatusVO generateJoinCode(@RequestBody JoinInfo joinInfo, HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        Generator generator = (Generator) servletContext.getAttribute("generator");
        if (!"override".equals(joinInfo.getOverrideCodes())) {
            String dir = generator.getSaveBaseDir() + File.separator + generator.getJavaSrcDir() + File.separator
                    + generator.getBasePackage().replace(".", File.separator) + File.separator + generator.getDoPackage();
            String fileName = joinInfo.getBeanName() + generator.getDoSuffix() + ".java";
            if (FileUtils.exist(dir, fileName)) {
                return ResponseStatusVO.dataError("已经存在指定名称的实体类，请重新填写实体类名称后再生成代码", null);
            }
        }
        Map<String, TableColumns> tableColumnsMap = (Map<String, TableColumns>) servletContext.getAttribute(TableController.TABLE_COLUMNS_MAP);
        CodeGenerator.generateJoinCode(joinInfo.getBeanName(), joinInfo.getRequestMapping(), generator, joinInfo.getTables(),
                joinInfo.getPrimaryTable(), joinInfo.getColumns(), tableColumnsMap, joinInfo.getWhereClause(), joinInfo.getCodeTypes());
        return ResponseStatusVO.ok("成功生成所选关联表的代码！", null);
    }

}
