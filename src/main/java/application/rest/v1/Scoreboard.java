package application.rest.v1;

import javax.enterprise.context.ApplicationScoped;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@ApplicationScoped
@Path("v1/scores")
public class Scoreboard {
  
  private List<Score> scoreboard = new ArrayList<>();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Response scores() {
    return Response.ok(scoreboard).build();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  public synchronized void addScore(Score score) {
    scoreboard = Stream
                  .concat(scoreboard.stream(),Stream.of(score))
                  .sorted((o1,o2)->o2.compareTo(o1))
                  .limit(10)
                  .collect(Collectors.toList());
  }
  
}
