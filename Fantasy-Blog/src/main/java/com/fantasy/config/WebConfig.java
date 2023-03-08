package com.fantasy.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 配置CORS跨域支持、拦截器
 */
@Configuration
@EnableKnife4j
@EnableSwagger2
@EnableAsync // 开启异步编程
public class WebConfig implements WebMvcConfigurer {

    private String accessPath;
    private String resourcesLocations;

    /**
     * @param accessPath 请求地址映射
     */
    @Value("${upload.access.path}")
    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    /**
     * @param resourcesLocations 本地文件路径映射
     */
    @Value("${upload.resources.locations}")
    public void setResourcesLocations(String resourcesLocations) {
        this.resourcesLocations = resourcesLocations;
    }

    /**
     * 跨域请求
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .maxAge(3600);
    }
    

    /**
     * 本地静态资源路径映射
     *
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(accessPath).addResourceLocations(resourcesLocations);
        //设置Swagger文档静态资源映射
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 声明Swagger接口文档
     * @return
     */
    @Bean
    public Docket createRestApi() {
        // 文档类型
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.fantasy.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Fantasy-Blog")
                .version("1.0")
                .description("Fantasy-Blog接口文档")
                .build();
    }


}
