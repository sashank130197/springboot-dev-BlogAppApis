package com.blog.config;

import java.util.Collections;
import java.util.List;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
//all the configuration of swagger is done with help of docket class. We will return docket class as bean from method
@Configuration
public class SwaggerConfig {
	
	public static final String AUTHORIZATION_HEADER="Authorization";
	
	private ApiKey apiKeys() {
		return new ApiKey("JWT",AUTHORIZATION_HEADER,"header");
	}
	
	private List<SecurityContext> securityContexts() {
		return Arrays.asList(SecurityContext.builder().securityReferences(sf()).build());
	}
	
	
	private List<SecurityReference> sf(){
		AuthorizationScope scope= new AuthorizationScope("global","accessEverything");
		return Arrays.asList(new SecurityReference("JWT",new AuthorizationScope[] {scope}));
	}
	@Bean
	public Docket api() {
		// we have provided all api using any() and all paths using any() 
		// now configure security using securitycontexts and security schemes
		
		
		
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(getInfo()).securityContexts(securityContexts())
				.securitySchemes(Arrays.asList(apiKeys()))
				.select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build();
	}

	private ApiInfo getInfo() {
		
		return new ApiInfo("Blogging Application ", "This project is developed using Spring", "1.0", "Terms of service", new Contact("Sashank","", "poddarsashasnk2009@gmail.com"), "License of APIS", "Api license url", Collections.emptyList());
	}

}
 