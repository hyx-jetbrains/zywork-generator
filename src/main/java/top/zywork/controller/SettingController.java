package top.zywork.controller;

import org.springframework.web.bind.annotation.*;
import top.zywork.bean.Generator;
import top.zywork.bean.JDBC;
import top.zywork.common.JDBCUtils;
import top.zywork.exception.AppException;
import top.zywork.vo.ResponseStatusVO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * 显示JDBC和Generator配置页面的控制器<br/>
 *
 * 创建于2018-03-22<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
@RestController
@RequestMapping("/setting")
public class SettingController {

    /**
     * 获取JDBC配置
     * @param request
     * @return
     */
    @GetMapping("jdbc")
    public ResponseStatusVO showJdbc(HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        ResponseStatusVO statusVO = new ResponseStatusVO();
        statusVO.okStatus(200, "成功获取JDBC配置信息", servletContext.getAttribute("jdbc"));
        return statusVO;
    }

    /**
     * 获取generator配置
     * @param request
     * @return
     */
    @GetMapping("generator")
    public ResponseStatusVO showGenerator(HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        ResponseStatusVO statusVO = new ResponseStatusVO();
        statusVO.okStatus(200, "成功获取JDBC配置信息", servletContext.getAttribute("generator"));
        return statusVO;
    }

    /**
     * 更新jdbc配置
     * @param jdbc
     * @param request
     * @return
     */
    @PostMapping("jdbc")
    public ResponseStatusVO saveJdbc(@RequestBody JDBC jdbc, HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        JDBCUtils jdbcUtils = new JDBCUtils();
        ResponseStatusVO statusVO = new ResponseStatusVO();
        try {
            jdbcUtils.connect(jdbc.getDriverClassName(), jdbc.getUrl(), jdbc.getUsername(), jdbc.getPassword());
            servletContext.setAttribute("jdbc", jdbc);
            servletContext.removeAttribute("tableColumnList");
            statusVO.okStatus(200, "已修改JDBC配置", null);
        } catch (AppException e){
            statusVO.errorStatus(500, "数据库连接失败，请重新修改JDBC配置", null);
        }
        return statusVO;
    }

    /**
     * 更新generator配置
     * @param generator
     * @param request
     * @return
     */
    @PostMapping("generator")
    public ResponseStatusVO saveGenerator(@RequestBody Generator generator, HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute("generator", generator);
        ResponseStatusVO statusVO = new ResponseStatusVO();
        statusVO.okStatus(200, "已修改Generator配置", null);
        return statusVO;
    }

}
