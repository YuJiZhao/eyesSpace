package com.eyes.eyesspace.sync.common.config;

import java.util.ArrayList;
import java.util.List;
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

/**
 * @author eyesYeager
 * @date 2023/1/25 14:52
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket createRestApi() {
    // 添加head参数配置start
    List<Parameter> pars = new ArrayList<>();
    ParameterBuilder tokenPar1 = new ParameterBuilder();
    ParameterBuilder tokenPar2 = new ParameterBuilder();
    tokenPar1.name("sAuth").description("短令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    tokenPar2.name("lAuth").description("长令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
    pars.add(tokenPar1.build());
    pars.add(tokenPar2.build());
    // 添加head参数配置end
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.eyes.eyesspace"))
        .paths(PathSelectors.any())
        .build()
        .globalOperationParameters(pars);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("耶瞳空间API文档")
        .description("耶瞳空间API文档")
        .version("1.0")
        .build();
  }
}
