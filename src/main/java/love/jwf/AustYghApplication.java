package love.jwf;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 项目启动类
 */
@Slf4j //启动日志
@SpringBootApplication
public class AustYghApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(AustYghApplication.class, args);
        Environment env = applicationContext.getEnvironment(); //获取当前应用程序运行的环境
        String ip = InetAddress.getLocalHost().getHostAddress();  //获取当前应用程序运行的外部ip地址
        String port = env.getProperty("server.port"); //获取当前应用程序占用的端口号
        port = port == null ? "8080" : port; //为null时表示采用tomcat默认端口号8080
        String path = env.getProperty("server.servlet.context-path"); //获取当前应用程序配置的上下文路径(context-path)
        path = path == null ? "" : path; //为null时表示路径为"/"
        log.info("\n----------------------------------------------------------\n\t" +
                "aust-ygh is running! Access URLs:\n\t" +
                "本地访问地址: \thttp://localhost:" + port + path + "/\n\t" +
                "外部访问地址: \thttp://" + ip + ":" + port + path + "/\n\t" +
                "Swagger文档: \thttp://" + ip + ":" + port + path + "/swagger-ui.html\n" +
                "----------------------------------------------------------");
    }

}
