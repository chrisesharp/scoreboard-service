package application.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import application.persistence.ScoreboardInMemory;
import application.persistence.ScoreboardPersistence;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.annotation.security.RolesAllowed;

@Api(tags = { "Scoreboard" })
@Path("/")
@ApiModel()
public class Scoreboard {

  @Inject
  private ScoreboardPersistence persistence = new ScoreboardInMemory();

  @GET
  @Path("scores")
  @ApiOperation(value = "Get scores as a list", responseContainer = "List", response = Score.class)
  @ApiResponses({ @ApiResponse(code = 200, message = "scores", responseContainer = "List", response = Score.class) })
  @Produces(MediaType.APPLICATION_JSON)
  public Response scores() {
    return Response.ok().entity(persistence.getTopTenScores()).build();
  }

  @POST
  @Path("scores")
  @ApiOperation("Post a score to the scoreboard")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  @ApiResponses({ @ApiResponse(code = 200, message = "Score added", response = String.class) })
  public synchronized Response addScore(@ApiParam(required = true) Score score) {
    persistence.addScore(score);
    return Response.ok().entity("Thanks").build();
  }
  
  @GET
  @Path("reset")
  @Produces(MediaType.TEXT_PLAIN)
  @RolesAllowed({ "admin" })
  @ApiOperation("Reset the scoreboard")
  @ApiResponses({ @ApiResponse(code = 200, message = "Scoreboard reset", response = String.class) })
  public synchronized Response reset() {
    persistence.deleteAll();
    return Response.ok().entity("Reset").build();
  }
  
  @POST
  @Path("reset")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  @RolesAllowed({ "admin" })
  @ApiOperation("Reset the scoreboard to a given state")
  @ApiResponses({ @ApiResponse(code = 200, message = "Scoreboard reset", response = String.class) })
  public synchronized Response reset(com.ibm.json.java.JSONObject request) {
    String state = (String) request.get("state");
    persistence.deleteAll();
    System.out.println("State requested = " + state);
    switch (state) {
      case "empty scoreboard":
        break;
      case "scoreboard with low score":
        persistence.addScore(new Score("david", 8000));
        break;
      case "scoreboard with two scores":
        persistence.addScore(new Score("david", 8000));
        persistence.addScore(new Score("chris", 10000));
        break;
      default:
        System.out.println("Unrecognized state requested");
        break;
    }
    return Response.ok().entity("Reset").build();
  }
}
