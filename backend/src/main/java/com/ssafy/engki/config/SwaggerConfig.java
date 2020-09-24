package com.ssafy.engki.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

public class SwaggerConfig {
	@Autowired
	private TypeResolver resolver;

	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName("Engki")
			.useDefaultResponseMessages(false)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.ssafy.engki"))
			.paths(PathSelectors.ant("/**"))
			.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Engki API")
			.description("Engki API Reference for Developers")
			.termsOfServiceUrl("https://j3a501.p.ssafy.io")
			.license("Deki License")
			.licenseUrl("ssafy@ssafy.com").version("0.0.1").build();
	}
}
