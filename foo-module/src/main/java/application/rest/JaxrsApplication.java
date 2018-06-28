package application.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import application.rest.Scoreboard;

import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

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
  @Override
  public Set<Object> getSingletons() {
      Set<Object> set = new HashSet<>();
      set.add(new Scoreboard());
      return set;
  }
}
