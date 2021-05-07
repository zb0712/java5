package com.szb.java5.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 石致彬
 * @since 2021-03-30 22:48
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(
                RequestHandlerSelectors.basePackage("com.szb.java5.controller")).build()
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Java5接口文档")
                .description("...")
                .contact(new Contact("szb","http:localhost:8081/doc.html","..."))
                .version("1.0")
                .build();
    }

    private List<ApiKey> securitySchemes() {
        //设置请求头信息
        List<ApiKey> list = new ArrayList<>();
        ApiKey apiKey = new ApiKey("Authorization","Authorization","Header");
        list.add(apiKey);
        return list;
    }

    private List<SecurityContext> securityContexts() {
        //设置需要认证的路径
        List<SecurityContext> list = new ArrayList<>();
        list.add(getContextByPath("/info"));
        return list;

    }

    private SecurityContext getContextByPath(String s) {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(s)).build();
    }

    private List<SecurityReference> defaultAuth() {
        List<SecurityReference> list = new ArrayList<>();
        AuthorizationScope authorizationScope = new AuthorizationScope("global","All");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        list.add(new SecurityReference("Authorization",authorizationScopes));
        return list;
    }
}
