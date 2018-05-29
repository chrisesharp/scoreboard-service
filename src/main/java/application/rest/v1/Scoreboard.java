package application.rest.v1;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import application.persistence.ScoreboardInMemory;
import application.persistence.ScoreboardJpa;
import application.persistence.ScoreboardPersistence;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@ApplicationScoped
@Api(tags = { "Scoreboard" })
@Path("v1/scores")
@ApiModel()
@Resource(lookup = "jdbc/db", name = "jdbc/db")
public class Scoreboard {

  @PersistenceContext(unitName = "scoreboardpersistenceunit")
  private EntityManager entityManager;

  @Resource
  private UserTransaction userTransaction;

  @Inject
  @ConfigProperty(name = "POSTGRES_HOSTNAME", defaultValue="")
  private String dbHostName;

  private ScoreboardPersistence persistence = new ScoreboardInMemory();

  private Logger log = Logger.getLogger(Scoreboard.class.getName());

  @PostConstruct
  public void init() {
    log.info("DB Host Name: " + dbHostName);
    if (!(dbHostName == null || dbHostName.isEmpty())) {
      persistence = new ScoreboardJpa(entityManager, userTransaction);
    }
  }

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
  @ApiResponses({ @ApiResponse(code = 201, message = "Score added", response = String.class) })
  public synchronized Response addScore(@ApiParam(required = true) Score score) {
    persistence.addScore(score);
    return Response.ok().entity("Thanks\n").build();
  }

}
