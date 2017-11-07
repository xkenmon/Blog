package cn.xkenmon.blog.control.servlet;

import cn.xkenmon.blog.util.Config;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.File;

public class Log4jInit extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("正在初始化LOG4J...");
        String locate = Config.get("log4jLocation");
        if (locate == null) {
            System.err.println("无法获取初始化参数，使用默认配置");
            BasicConfigurator.configure();
        }else {
            File yoMamaYesThisSaysYoMama = new File(locate);
            if (yoMamaYesThisSaysYoMama.exists()) {
                System.out.println("使用: " + locate +"初始化日志设置信息");
                PropertyConfigurator.configure(locate);
            } else {
                System.err.println("*** " + locate + " 文件没有找到， 所以使用 BasicConfigurator初始化");
                BasicConfigurator.configure();
            }
        }
        super.init();
    }
}
