package ru.sber.spring.java13springmy.sdproject.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    //http://localhost:9090/api/rest/swagger-ui/index.htm
    @Bean
    public OpenAPI libraryProject() {
        return new OpenAPI()
                .info(new Info()
                        .title("СервисДеск")
                        .description("Система регистации заявок СервисДеск")
                        .version("v0.1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                        .contact(new Contact().name("Ignatev V, Stepanenko B")
                                .email("ignatevvk@mail.ru, greek-hohlov@yandex.ru")
                                .url(""))
                );
    }
}
