package cn.tlh.admin.consumer.config;

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
 * 文档地址: http://localhost:8030/doc.html
 * @author TANG
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
//                .groupName("springboot-dubbo-nacos-demo")
                .select()
                // 扫描controller路径
                .apis(RequestHandlerSelectors.basePackage("cn.tlh.admin.consumer.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * name:开发者姓名
     * url:开发者网址
     * email:开发者邮箱
     *
     * @return /
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 标题
                .title("sdnd接口文档")
                // 文档接口的描述
                .description("springboot集成dubbo, 使用nacos作为注册中心的demo")
                .contact(new Contact("TANG", "", ""))
                // 版本号
                .version("1.0.0")
                .build();
    }

}