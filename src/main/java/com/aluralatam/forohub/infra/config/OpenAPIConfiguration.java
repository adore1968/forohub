package com.aluralatam.forohub.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Bean
    public OpenAPI defineOpenApi() {
        Contact myContact = new Contact();
        myContact.setName("German Jose Gomez");
        myContact.setEmail("jg345762@gmail.com");

        Info information = new Info()
                .title("API REST de ForoHub")
                .version("1.0")
                .description("Esta API expone endpoints para administrar topicos.")
                .contact(myContact);
        return new OpenAPI().info(information);
    }
}
