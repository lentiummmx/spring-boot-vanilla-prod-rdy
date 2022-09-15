package mx.nuniez.xoco.spring.boot.vanilla.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import springfox.documentation.builders.ParameterBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.schema.ModelRef;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.service.Parameter;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

//@EnableWebMvc
//@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

//    @Bean
//    public Docket api() {
//        List<Parameter> parameters = new ArrayList<>();
//
//        parameters.add(new ParameterBuilder().name("Authorization")
//                .description("JWT token")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(false)
//                .build());
//
//        parameters.add(new ParameterBuilder().name("Content-Type")
//                .description("Content-Type")
//                .modelRef(new ModelRef("string"))
//                .parameterType("header")
//                .required(true)
//                .build());
//
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                .paths(PathSelectors.any())
//                .build()
//                .apiInfo(apiInfo())
//                .globalOperationParameters(parameters);
//    }
//
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("spring-boot-vanilla")
                .pathsToMatch("/**")
                //.addOperationCustomizer(new OperationCustomizer() {
                //    @Override
                //    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
                //        operation.addParametersItem(new HeaderParameter()
                //                        .name("Content-Type")
                //                        .description("Content-Type")
                //                        .required(true));
                //        operation.addParametersItem(new HeaderParameter()
                //                        .name("Authorization")
                //                        .description("JWT token")
                //                        .required(false));
                //        return operation;
                //    }
                //})
                .build();
    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfo(
//                "Spring Boot Vanilla REST API",
//                "Spring Boot Vanilla REST API Swagger Documentation",
//                "Version 1",
//                "urn:tos",
//                new Contact("Admin", "xoco.nuniez.mx", "info@xoco.nuniez.mx"),
//                "Apache 2.0",
//                "https://www.apache.org/licenses/LICENSE-2.0",
//                new ArrayList<>());
//    }
//
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("basicScheme", new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic"))
                        .addParameters("Authorization", new Parameter().in("header").schema(new StringSchema()).name("JWT token"))
                        .addHeaders("Content-Type", new Header().description("Content-Type").schema(new StringSchema())))
                .info(new Info().title("Spring Boot Vanilla REST API")
                        .description("Spring Boot Vanilla REST API Swagger Documentation")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringShop Wiki Documentation")
                        .url("https://springshop.wiki.github.org/docs"));
    }

    @Bean
    public OpenApiCustomiser customerGlobalHeaderOpenApiCustomiser() {
        return openApi -> openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream())
                .forEach(operation ->{
                    operation.addParametersItem(new HeaderParameter().$ref("#/components/headers/Content-Type"));
                    operation.addParametersItem(new HeaderParameter().$ref("#/components/headers/Authorization"));
                });
    }

}
