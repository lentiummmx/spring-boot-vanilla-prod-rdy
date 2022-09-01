package mx.nuniez.xoco.spring.boot.vanilla.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@EnableWebMvc
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket api() {
        List<Parameter> parameters = new ArrayList<>();

        parameters.add(new ParameterBuilder().name("Authorization")
                .description("JWT token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build());

        parameters.add(new ParameterBuilder().name("Content-Type")
                .description("Content-Type")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(true)
                .build());

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .globalOperationParameters(parameters);
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Spring Boot Vanilla REST API",
                "Spring Boot Vanilla REST API Swagger Documentation",
                "Version 1",
                "urn:tos",
                new Contact("Admin", "xoco.nuniez.mx", "info@xoco.nuniez.mx"),
                "Apache 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList<>());
    }

}
