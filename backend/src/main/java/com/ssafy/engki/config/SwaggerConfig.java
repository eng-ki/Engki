package com.ssafy.engki.config;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Profile("local")
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {
	@Bean
	public Docket postsApi() {
		return new Docket(DocumentationType.SWAGGER_2)
			.groupName("Engki")
			.useDefaultResponseMessages(false)
			.apiInfo(apiInfo())
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.ssafy.engki"))
			.paths(PathSelectors.ant("/**"))
			.build()
			.securitySchemes(Collections.singletonList(apiKey()))
			.securityContexts(Collections.singletonList(securityContext()));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Engki API")
			.description("Engki API Reference for Developers")
			.termsOfServiceUrl("https://j3a501.p.ssafy.io")
			.license("Deki License")
			.licenseUrl("ssafy@ssafy.com").version("0.0.1").build();
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", "X-AUTH-TOKEN", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
			.securityReferences(defaultAuth())
			.forPaths(PathSelectors.ant("/**"))
			.build();
	}

	List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[] {authorizationScope};
		return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
	}
}
