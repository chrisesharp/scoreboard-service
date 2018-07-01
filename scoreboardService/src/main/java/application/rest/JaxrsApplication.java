package application.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import application.rest.Scoreboard;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.ExternalDocumentation;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;

//import io.swagger.annotations.Info;
//import io.swagger.annotations.SwaggerDefinition;
//import io.swagger.annotations.Tag;

@ApplicationPath("/")

@OpenAPIDefinition(
    tags = {
            @Tag(name = "Scoreboard", description = "Top 10 scoreboard")
    },
    info = @Info(
        title="Scoreboard Service API", 
        version = "1.0.0",
        termsOfService = "http://exampleurl.com/terms", 
        contact = @Contact(
            name = "Scoreboard API Support",
            url = "http://exampleurl.com/contact",
            email = "foo@bar.com"),
        license = @License(
            name = "Apache 2.0",
            url = "http://www.apache.org/licenses/LICENSE-2.0.html")),
        security = @SecurityRequirement(name = "scoreboardService_auth")
)
@SecurityScheme(
    securitySchemeName = "scoreboardService_auth",
    description = "authentication needed to access scoreboard service",
    type = SecuritySchemeType.APIKEY,
    apiKeyName = "api_key",
    in = SecuritySchemeIn.HEADER
) 
@Schema(
    name = "Scoreboard REST API",
    description = "API for scoreboard",
    externalDocs = @ExternalDocumentation(
        description = "For more information, see the link.",
        url = "http://exampleurl.com/schema")
    )
/*
@SwaggerDefinition(
  info = @Info(
            description = "scoreboard REST service",
            version = "1.0.0",
            title = "scoreboard API"
  ),
  tags = {
          @Tag(name = "Scoreboard",
               description = "Scoreboard REST service")
          }
)
*/

public class JaxrsApplication extends Application {
  @Override
  public Set<Object> getSingletons() {
      Set<Object> set = new HashSet<>();
      set.add(new Scoreboard());
      return set;
  }
}
