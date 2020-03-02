package org.atgpcm.oneStopApplet.config;

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
 * @author chaihaoping
 * @title 接口文档
 * @date 2020/1/6 11:28
 * @description 接口文档
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 通过 createRestApi函数来构建一个DocketBean
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())//调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                .select()
                //控制暴露出去的路径下的实例
                //如果某个接口不想暴露,可以使用以下注解
                //@ApiIgnore 这样,该接口就不会暴露在 swagger2 的页面下
                .apis(RequestHandlerSelectors.basePackage("org.atgpcm.oneStopApplet.controller"))
                .paths(PathSelectors.any())
                //禁用swagger
//                .paths(PathSelectors.none())
                .build();
    }

    //构建api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("一站式小程序API接口文档")
                //描述
                .description("API接口描述")
                .termsOfServiceUrl("localhost:2501/")
                .contact(new Contact("chaihaoping","http://localhost:2501/oneStopApplet/swagger-ui.html","chaihaoping@atgco.cn"))
                .version("1.0")
                .build();
    }
}
