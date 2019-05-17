package top.zywork.listener;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.zywork.bean.Generator;
import top.zywork.bean.JDBC;
import top.zywork.common.FileUtils;
import top.zywork.common.IOUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * 自动代码生成监听器<br/>
 * 当启动项目时，需要去读取JDBC和Generator的默认配置信息，并显示到页面中<br/>
 *
 * 创建于2018-03-22<br/>
 *
 * @author 王振宇
 * @version 1.0
 */
@WebListener
@Slf4j
public class GeneratorLoadListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("zywork-generator启动，开始读取JDBC和Generator默认配置……");
        JDBC jdbc = IOUtils.readJsonFileToObject(FileUtils.getResourcePath("classpath:/config/jdbc.json"), JDBC.class);
        Generator generator = IOUtils.readJsonFileToObject(FileUtils.getResourcePath("classpath:/config/generator.json"), Generator.class);
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("jdbc", jdbc);
        servletContext.setAttribute("generator", generator);
        log.info("zywork-generator已启动……");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("zywork-generator关闭……");
    }
}
