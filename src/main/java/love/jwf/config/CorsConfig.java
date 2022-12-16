package love.jwf.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
//@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //允许跨域访问的路径
                .allowedOriginPatterns("*") //允许跨域访问的源
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS") //允许的请求方式
                .maxAge(168000) //预检间隔时间
                .allowedHeaders("*") //允许头部设置
                .allowCredentials(true); //是否发送cookie
    }
}
