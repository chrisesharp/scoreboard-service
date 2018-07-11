package application.rest;

import javax.inject.Inject;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import application.persistence.ScoreboardInMemory;
import application.persistence.ScoreboardPersistence;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;

import com.ibm.json.java.JSONObject;

@Tag(ref = "Scoreboard")
@Path("")
public class Scoreboard {
  private String deployment;
  @Inject
  private ScoreboardPersistence persistence = new ScoreboardInMemory();

  @GET
  @Path("/scores")
  @Produces(MediaType.APPLICATION_JSON)
  @Operation(
    summary = "Get scores",
    description = "Get top 10 scores from the scoreboard",
    operationId = "scores"
  )
  @APIResponses(value = { 
    @APIResponse(
      responseCode = "200",
      description = "scores",
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(
          type = SchemaType.ARRAY, 
          implementation = Score.class
        )
      )
    )
  })
  public Response scores() {
    return Response.ok().entity(persistence.getTopTenScores()).build();
  }

  @POST
  @Path("/scores")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  @RolesAllowed({ "player" })
  @Operation(
    summary = "Post score",
    description = "Post a score to the scoreboard",
    operationId = "addScore"
  )
  @SecurityRequirement(name = "scoreboardService_auth_Bearer")
  @APIResponses(value = { 
    @APIResponse(
      responseCode = "200",
      description = "Score added",
      content = @Content(
        mediaType = "text/plain",
        schema = @Schema(
          type = SchemaType.STRING, 
          implementation = String.class,
          example = "Thanks"
        )
      )
    )
  })
  public synchronized Response addScore(
    @RequestBody(
        description = "New score",
        required = true,
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                name = "Score",
                type = SchemaType.OBJECT,
                required = true,
                implementation = Score.class)
        )
    ) Score score) {
    persistence.addScore(score);
    return Response.ok().entity("Thanks").build();
  }
  
  @POST
  @Path("/reset")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.TEXT_PLAIN)
  public synchronized Response reset(JSONObject request) {
    if (deployment != "production") {
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
    }
    return Response.ok().entity("Reset").build();
  }
}
