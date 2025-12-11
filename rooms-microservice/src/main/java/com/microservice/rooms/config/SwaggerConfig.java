package com.microservice.rooms.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;

@OpenAPIDefinition(
        info = @Info(
                title = "Room microservice",
                description = "Service that handles all business logic related with the rooms of the hotel.",
                version = "1.0.0",
                contact = @Contact(
                        name = "Michael Garnica",
                        email = "mgarnica.oficial@gmail.com"
                )
        ),
        servers = {
                @Server(
                        description = "dev server",
                        url = "http://localhost:{port}/",
                        variables = {
                                @ServerVariable(
                                        name = "port",
                                        defaultValue = "9093"
                                )
                        }
                )
        }

)
public class SwaggerConfig {
}
