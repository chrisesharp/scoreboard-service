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

@Api(tags = { "Scoreboard" })
@Path("scores")
@ApiModel()
public class Scoreboard {

  @Inject
  private ScoreboardPersistence persistence = new ScoreboardInMemory();

  @GET
  @ApiOperation(value = "Get scores as a list", responseContainer = "List", response = Score.class)
  @ApiResponses({ @ApiResponse(code = 200, message = "scores", responseContainer = "List", response = Score.class) })
  @Produces(MediaType.APPLICATION_JSON)
  public Response scores() {
    return Response.ok().entity(persistence.getTopTenScores()).build();
  }

  @POST
  @ApiOperation("Post a score to the scoreboard")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  @ApiResponses({ @ApiResponse(code = 200, message = "Score added", response = String.class) })
  public synchronized Response addScore(@ApiParam(required = true) Score score) {
    persistence.addScore(score);
    return Response.ok().entity("Thanks").build();
  }

}
