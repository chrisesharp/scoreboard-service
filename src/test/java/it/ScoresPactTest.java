package it;

import org.junit.runner.RunWith;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.junit.loader.PactFolder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import javax.json.JsonObjectBuilder;
import javax.json.JsonObject;
import javax.json.Json;

@RunWith(PactRunner.class)
@Provider("scoreboard_provider")
@PactFolder("pacts")
public class ScoresPactTest {
  private int port = Integer.parseInt(System.getProperty("liberty.test.port"));
  private String endpoint = "/leaderboard/reset";
  private String url = "http://localhost:" + port + "/"  + endpoint;
  Client client = ClientBuilder.newClient();
  
  @TestTarget
  public final Target target = new HttpTarget(port);

  @State("empty scoreboard")
  public void toEmptyState() {
    System.out.println("Setting toEmptyState");
    sendRequest();
  }
  @State("scoreboard with low score")
  public void toLowScoreState() {
    System.out.println("Setting toLowScoreState");
    JsonObjectBuilder state = Json.createObjectBuilder();
    state.add("state","scoreboard with low score");
    sendRequest(state.build());
  }
  @State("scoreboard with two scores")
  public void toTwoScoreState() {
    System.out.println("Setting toTwoScoreState");
    JsonObjectBuilder state = Json.createObjectBuilder();
    state.add("state","scoreboard with two scores");
    sendRequest(state.build());
  }
  
  private void sendRequest(JsonObject state) {
    Invocation.Builder request = client.target(url).request();
    Entity<JsonObject> entity = Entity.entity(state, MediaType.APPLICATION_JSON);
    request.post(entity).close();
  }
  
  private void sendRequest() {
    Invocation.Builder request = client.target(url).request();
    request.get().close();
  }
}