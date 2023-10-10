package danya.industries.restab.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "ReStab API",
                description = "ReStab",
                version = "1.0",
                contact = @Contact(
                        name = "Малышка, если ты че то не поняла, то это не значит шо меня нада заеобувать",
                        url = "https://den4ick/slaziet"
                )
        )
)
public class SwaggerConfig { }
