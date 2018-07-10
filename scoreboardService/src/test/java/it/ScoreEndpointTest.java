// tag::copyright[]
/*******************************************************************************
 * Copyright (c) 2017, 2018 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - Initial implementation
 *******************************************************************************/
// end::copyright[]
// tag::test[]
package it;

import static org.junit.Assert.assertEquals;

import javax.json.JsonObjectBuilder;
import javax.json.Json;

import javax.ws.rs.core.Response;
import org.junit.Test;
import it.util.TestUtils;
import it.util.JwtVerifier;

public class ScoreEndpointTest {

  private final String SCORES = "/scoreboard/scores";
  private final String TESTNAME = "TESTUSER";

  private String baseUrl = "https://localhost:" + System.getProperty("liberty.test.ssl.port");
  private String scoresUrl = baseUrl + SCORES;

  @Test
  public void testGetScoresWithoutJWT() throws Exception {
    Response resetResponse = TestUtils.processRequest(scoresUrl, "GET", null, null);

    assertEquals(
        "HTTP response code should have been " + TestUtils.OK + ".",
        TestUtils.OK, resetResponse.getStatus()
    );
  }
  
  @Test
  public void testPostScoresWithPlayerRole() throws Exception {
    String authHeader = "Bearer " + new JwtVerifier().createPlayerJwt(TESTNAME);
    JsonObjectBuilder state = Json.createObjectBuilder();
    state.add("player","chris");
    state.add("score",1000);
    Response resetResponse = TestUtils.processRequest(scoresUrl, "POST", state.build().toString(), authHeader);
    resetResponse = TestUtils.processRequest(scoresUrl, "POST", state.build().toString(), authHeader);

    assertEquals(
        "HTTP response code should have been " + TestUtils.OK + ".",
        TestUtils.OK, resetResponse.getStatus()
    );
    
    assertEquals(
        "Response body should have been " + TestUtils.ScorePostOK + ".",
        TestUtils.ScorePostOK, resetResponse.readEntity(String.class)
    );
  }
  
  @Test
  public void testPostScoresWithoutPlayerRole() throws Exception {
    String authHeader = "Bearer " + new JwtVerifier().createAdminJwt(TESTNAME);
    JsonObjectBuilder state = Json.createObjectBuilder();
    state.add("player","chris");
    state.add("score",1000);
    Response resetResponse = TestUtils.processRequest(scoresUrl, "POST", state.build().toString(), authHeader);
    
    assertEquals("HTTP response code should have been "
        + TestUtils.FORBIDDEN + ".", TestUtils.FORBIDDEN,
        resetResponse.getStatus()
    );    
  }
}
// end::test[]
