package com.modusoftware.sitic.phoneProtect.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import com.modusoftware.commons.ws.config.EnableMSCommonsWs;
import com.modusoftware.commons.ws.rest.config.MSDefaultWebMvcConfigurer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableMSCommonsWs
@EnableTransactionManagement
@PropertySource(value={"classpath:application-${spring.profiles.active}.properties", "classpath:ws.properties"})
@EnableJpaRepositories(basePackages = {"com.modusoftware.sitic.phoneProtect.user.persistent"})
@EntityScan(basePackages = "com.modusoftware.sitic.phoneProtect.entity")
@EnableWebMvc
@EnableSwagger2
public class UserServiceApplication extends MSDefaultWebMvcConfigurer {
	
	@Value("${api.description}")
	private String apiDescription;
	
	@Value("${api.owner.url}")
	private String apiOwnerUrl;
	
	@Value("${api.owner.email}")
	private String apiOwnerEmail;
	
	@Value("${api.owner.name}")
	private String apiOwnerName;
	
	@Autowired
	private BuildProperties buildProperties;
	
	public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
	registry.addRedirectViewController("/api/v2/api-docs", "/v2/api-docs");
	registry.addRedirectViewController("/api/swagger-resources/configuration/ui","/swagger-resources/configuration/ui");
	registry.addRedirectViewController("/api/swagger-resources/configuration/security", "/swagger-resources/configuration/security");
	registry.addRedirectViewController("/api/swagger-resources", "/swagger-resources");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	registry.addResourceHandler("/api/swagger-ui.html**").addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
	registry.addResourceHandler("/api/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}
	
	@Bean
	public Docket apiDocket() {
		ModelRef errorModel = new ModelRef("RestApiExceptionModel");
		
		List<ResponseMessage> responseMessages = Arrays.asList(
				new ResponseMessageBuilder().code(HttpStatus.OK.value()).message("Proceso ejecutado de forma exitosa").responseModel(errorModel).build(),
				new ResponseMessageBuilder().code(HttpStatus.BAD_REQUEST.value()).message("Petición incorrecta (obligatoriedad, tipo de datos, formato)").responseModel(errorModel).build(),
		        new ResponseMessageBuilder().code(HttpStatus.UNAUTHORIZED.value()).message("Credenciales de consumo del API invalidas").responseModel(errorModel).build(),
		        new ResponseMessageBuilder().code(HttpStatus.FORBIDDEN.value()).message("Las credenciales no tienen permisos de consumo del API").responseModel(errorModel).build(),
		        new ResponseMessageBuilder().code(HttpStatus.INTERNAL_SERVER_ERROR.value()).message("Se presento un error interno en el API").responseModel(errorModel).build());
		
		return new Docket(DocumentationType.SWAGGER_2)
				.enable(true)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.modusoftware.sitic.phoneProtect.user.ws"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(getApiInfo())
				.pathMapping("/")
				.directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class)
				.useDefaultResponseMessages(true)
				.globalResponseMessage(RequestMethod.GET, responseMessages)
				.globalResponseMessage(RequestMethod.POST, responseMessages)
				.globalResponseMessage(RequestMethod.PUT, responseMessages)
				.globalResponseMessage(RequestMethod.DELETE, responseMessages);				   
	}
	
	private ApiInfo getApiInfo() {		
		return new ApiInfo(
				this.buildProperties.getName(), 
				this.apiDescription, 
				this.buildProperties.getVersion(), 
				this.apiOwnerUrl, 
				new Contact(this.apiOwnerName, this.apiOwnerUrl, this.apiOwnerEmail), 
				this.apiOwnerUrl, 
				this.apiOwnerUrl, 
				new ArrayList<VendorExtension>());
	}
}
