package com.redspider.pss.configuration.swagger;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket createRestApi() {
    // 全局参数
    Parameter headerParameter =
        new ParameterBuilder()
            .name("Authorization")
            .description("token")
            .modelRef(new ModelRef("string"))
            // .defaultValue("Bearer ")
            .parameterType("header")
            .required(false)
            .allowEmptyValue(true)
            .build();

    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.redspider.pss"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo())
        .globalOperationParameters(Lists.newArrayList(headerParameter))
        .enable(true);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("拼少少服务接口API")
        .description("拼少少服务接口API")
        .version("1.0")
        .build();
  }
}
