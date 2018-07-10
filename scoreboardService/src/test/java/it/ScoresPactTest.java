package it;

import org.junit.runner.RunWith;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import org.apache.http.HttpRequest;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.junit.TargetRequestFilter;
import au.com.dius.pact.provider.junit.loader.PactFolder;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import javax.json.JsonObjectBuilder;
import javax.json.JsonObject;
import javax.json.Json;
import it.util.JwtVerifier;

import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;

@RunWith(PactRunner.class)
@Provider("scoreboard_provider")
@PactFolder("pacts")
public class ScoresPactTest {
  private final int port = Integer.parseInt(System.getProperty("liberty.test.port"));
  private final String rootPath = "/scoreboard";
  private final String url = "http://localhost:" + port + rootPath;
  private final String stateUrl = url + "/reset";
  private Client client = ClientBuilder.newClient().register(JsrJsonpProvider.class);
  
  @TestTarget
  public final Target target = new HttpTarget("http", "localhost", port, rootPath, true);

  @TargetRequestFilter
  public void JwtRequestFilter(HttpRequest request) throws Exception {
    String authHeader = "Bearer " + new JwtVerifier().createPlayerJwt("TESTUSER");
    request.addHeader("Authorization", authHeader);
  }

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
    Invocation.Builder request = client.target(stateUrl).request();
    Entity<JsonObject> entity = Entity.entity(state, MediaType.APPLICATION_JSON);
    request.post(entity).close();
  }
  
  private void sendRequest() {
    Invocation.Builder request = client.target(stateUrl).request();
    request.get().close();
  }
}