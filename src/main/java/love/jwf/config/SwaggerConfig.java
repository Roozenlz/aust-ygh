package love.jwf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 *
 * @author <a href="https://roozen.top">Roozen</a>
 * @version 1.0
 */
@Configuration  //配置类
@EnableSwagger2 //启用Swagger2功能
public class SwaggerConfig {
    /**
     * 创建JavaBean
     *
     * @return 创建的JavaBean
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.basePackage("love.jwf.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建接口文档
     *
     * @return 创建的接口文档
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("嘉晚饭Api接口文档")
                .contact(new Contact("Roozen", "https://roozen.top", "2273038475@qq.com"))
                .description("接口文档")
                .version("1.0")
                .build();
    }
}
