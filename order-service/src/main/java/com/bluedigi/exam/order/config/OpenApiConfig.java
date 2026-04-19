package com.bluedigi.exam.order.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
    @Value("${swagger.server.url}")
    private String swaggerServerUrl;

    @Value("${swagger.server.description}")
    private String swaggerServerDescription;

    @Value("${swagger.contact.email}")
    private String swaggerContactEmail;

    @Value("${swagger.contact.name}")
    private String swaggerContactName;

    @Value("${swagger.contact.url}")
    private String swaggerContactUrl;

    @Value("${swagger.licence.name}")
    private String swaggerLicenceName;

    @Value("${swagger.licence.url}")
    private String swaggerLicenceUrl;

    @Value("${swagger.info.title}")
    private String swaggerInfoTitle;

    @Value("${swagger.info.version}")
    private String swaggerInfoVersion;

    @Value("${swagger.info.description}")
    private String swaggerInfoDescription;

    @Value("${swagger.info.terms}")
    private String swaggerInfoTerms;

    @Bean
    public OpenAPI openAPI() {
        Server server = new Server();
        server.setUrl(swaggerServerUrl);
        server.setDescription(swaggerServerDescription);

        Contact contact = new Contact();
        contact.setEmail(swaggerContactEmail);
        contact.setName(swaggerContactName);
        contact.setUrl(swaggerContactUrl);

        License license = new License()
                .name(swaggerLicenceName)
                .url(swaggerLicenceUrl);

        Info info = new Info()
                .title(swaggerInfoTitle)
                .version(swaggerInfoVersion)
                .contact(contact)
                .description(swaggerInfoDescription)
                .termsOfService(swaggerInfoTerms)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}
