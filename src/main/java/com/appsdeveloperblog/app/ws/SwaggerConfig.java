package com.appsdeveloperblog.app.ws;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private Contact contact = new Contact(
            "Dat Nguyen Tien"
            , " http://www.appsdeveloperblog.com"
            , "cortana775@gmail.com"
    );

    private List<VendorExtension> vendorExtensions = new ArrayList<>();

    private ApiInfo apiInfo = new ApiInfo(
            "Photo app RESTful Web Service documentation"
            , "This pages documents Photo app RESTful Web Service endpoint"
            , "1,0"
            , "http://www.appsdeveloperblog.com/service.html"
            , contact
            , "Apache 2.0"
            , "http://www.apache.org/LICENSE-2.0"
            , vendorExtensions
    );

    @Bean
    public Docket apiDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .protocols(new HashSet<>(Arrays.asList("HTTP", "HTTPS")))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.appsdeveloperblog.app.ws"))
                .paths(PathSelectors.any())
                .build();
    }
}
