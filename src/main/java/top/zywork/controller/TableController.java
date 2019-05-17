package top.zywork.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zywork.bean.ColumnDetail;
import top.zywork.bean.IViewSelect;
import top.zywork.bean.JDBC;
import top.zywork.bean.TableColumns;
import top.zywork.common.JDBCUtils;
import top.zywork.vo.ResponseStatusVO;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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

    public static final String TABLE_COLUMNS_MAP = "tableColumnsMap";

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
        Map<String, TableColumns> tableColumnsMap = jdbcUtils.getTableColumns();
        servletContext.setAttribute(TABLE_COLUMNS_MAP, tableColumnsMap);
        // 获取key，即表名称
        List<String> keyList = new ArrayList<>(tableColumnsMap.keySet());
        Collections.sort(keyList);
        List<IViewSelect> iViewSelectList = new ArrayList<>();
        for (String key : keyList) {
            iViewSelectList.add(new IViewSelect(key, key, false));
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
    public ResponseStatusVO tableColumn(@PathVariable("tableName") String tableName, HttpServletRequest request) {
        Map<String, TableColumns> tableColumnsMap = getTableColumnListFromServletContext(request);
        if (tableColumnsMap == null) {
            return ResponseStatusVO.dataError("不存在表字段，请刷新页面", null);
        }
        TableColumns tableColumns = tableColumnsMap.get(tableName);
        if (tableColumns != null) {
            return ResponseStatusVO.ok("查询表字段成功", tableColumns.getColumnDetailList());
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
    public ResponseStatusVO tablesColumns(@PathVariable("tableNames") String tableNames, HttpServletRequest request) {
        Map<String, TableColumns> tableColumnsMap = getTableColumnListFromServletContext(request);
        if (tableColumnsMap == null) {
            return ResponseStatusVO.dataError("不存在表字段，请刷新页面", null);
        }
        List<List<ColumnDetail>> tablesColumns = new ArrayList<>();
        String[] tables = tableNames.split(",");
        for (String tableName : tables) {
            tablesColumns.add(tableColumnsMap.get(tableName).getColumnDetailList());
        }
        return ResponseStatusVO.ok("查询多个表的字段成功", tablesColumns);
    }

    /**
     * 从servlet context中获取保存的所有表字段信息
     * @param request
     * @return
     */
    @SuppressWarnings({"unchecked"})
    private Map<String, TableColumns> getTableColumnListFromServletContext(HttpServletRequest request) {
        ServletContext servletContext = request.getServletContext();
        Object obj = servletContext.getAttribute(TABLE_COLUMNS_MAP);
        if (obj != null) {
            Map<String, TableColumns> tableColumnsMap = (Map<String, TableColumns>) obj;
            return tableColumnsMap;
        }
        return null;
    }

}
