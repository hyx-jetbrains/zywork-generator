package top.zywork.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zywork.bean.ColumnDetail;
import top.zywork.bean.IViewSelect;
import top.zywork.bean.JDBC;
import top.zywork.bean.TableColumn;
import top.zywork.common.JDBCUtils;
import top.zywork.vo.ResponseStatusVO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 获取表及表字段信息的控制器<br/>
 *
 * 创建于2018-03-22<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
@RestController
@RequestMapping("/table")
public class TableController {

    /**
     * 获取所有表信息
     * @param request
     * @return
     */
    @GetMapping("all")
    public ResponseStatusVO allTables(HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        JDBC jdbc = (JDBC) servletContext.getAttribute("jdbc");
        JDBCUtils jdbcUtils = new JDBCUtils();
        jdbcUtils.connect(jdbc.getDriverClassName(), jdbc.getUrl(), jdbc.getUsername(), jdbc.getPassword());
        List<TableColumn> tableColumnList = jdbcUtils.getTableColumns();
        servletContext.setAttribute("tableColumnList", tableColumnList);
        List<IViewSelect> iViewSelectList = new ArrayList<>();
        for (TableColumn tableColumn : tableColumnList) {
            iViewSelectList.add(new IViewSelect(tableColumn.getTableName(), tableColumn.getTableName(), false));
        }
        return ResponseStatusVO.ok("查询所有表数据成功", iViewSelectList);
    }

    /**
     * 获取单个表的所有字段信息
     * @param tableName
     * @param request
     * @return
     */
    @GetMapping("table-columns/{tableName}")
    @SuppressWarnings({"unchecked"})
    public ResponseStatusVO tableColumn(@PathVariable("tableName") String tableName, HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        Object obj = servletContext.getAttribute("tableColumnList");
        if (obj == null) {
            return ResponseStatusVO.dataError("不存在表字段", null);
        }
        List<TableColumn> tableColumnList = (List<TableColumn>) obj;
        for (TableColumn tableColumn : tableColumnList) {
            if (tableName.equals(tableColumn.getTableName())) {
                return ResponseStatusVO.ok("查询表字段成功", tableColumn.getColumnDetails());
            }
        }
        return ResponseStatusVO.dataError("不存在的表", null);
    }

    /**
     * 返回多个表的字段信息
     * @param tableNames
     * @param request
     * @return
     */
    @GetMapping("tables-columns/{tableNames}")
    @SuppressWarnings({"unchecked"})
    public ResponseStatusVO tablesColumns(@PathVariable("tableNames") String tableNames, HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        Object obj = servletContext.getAttribute("tableColumnList");
        List<List<ColumnDetail>> tablesColumns = new ArrayList<List<ColumnDetail>>();
        if (obj == null) {
            return ResponseStatusVO.dataError("不存在表字段", null);
        }
        List<TableColumn> tableColumnList = (List<TableColumn>) obj;
        String[] tables = tableNames.split(",");
        for (String tableName : tables) {
            for (TableColumn tableColumn : tableColumnList) {
                if (tableName.equals(tableColumn.getTableName())) {
                    tablesColumns.add(tableColumn.getColumnDetails());
                }
            }
        }
        return ResponseStatusVO.ok("查询多个表的字段成功", tablesColumns);
    }

}
