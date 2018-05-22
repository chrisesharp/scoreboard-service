package application.rest;

//import application.rest.v1.*;
//import java.util.Set;
//import java.util.HashSet;
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
  /*
  public Set<Class<?>> getClasses() {
          Set<Class<?>> classes = new HashSet<Class<?>>();
          classes.add(Score.class);
          classes.add(Scoreboard.class);
          return classes;
      }
      */
}
