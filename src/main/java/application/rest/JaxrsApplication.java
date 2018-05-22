package application.rest;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import io.swagger.annotations.*;

@ApplicationPath("/")
@SwaggerDefinition(
  info = @Info(
            description = "Leaderboard REST service",
            version = "1.0.0",
            title = "Leaderboard API"
  ),
  tags = {
          @Tag(name = "Scoreboard",
               description = "Scoreboard REST service")
          }
)
public class JaxrsApplication extends Application {

}
