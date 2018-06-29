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

public class ResetEndpointTest {

  private final String RESET = "/scoreboard/reset";
  private final String TESTNAME = "TESTUSER";

  private String baseUrl = "https://localhost:" + System.getProperty("liberty.test.ssl.port");

  @Test
  public void testGetResetWithoutAdminRole() throws Exception {
    String authHeader = "Bearer " + new JwtVerifier().createUserJwt(TESTNAME);
    String resetUrl = baseUrl + RESET;
    Response resetResponse = TestUtils.processRequest(resetUrl, "GET", null, authHeader);

    assertEquals(
        "HTTP response code should have been " + TestUtils.FORBIDDEN + ".",
        TestUtils.FORBIDDEN, resetResponse.getStatus()
    );
  }
  
  @Test
  public void testGetResetWithAdminRole() throws Exception {
    String authHeader = "Bearer " + new JwtVerifier().createAdminJwt(TESTNAME);
    String resetUrl = baseUrl + RESET;
    Response resetResponse = TestUtils.processRequest(resetUrl, "GET", null, authHeader);

    assertEquals(
        "HTTP response code should have been " + TestUtils.OK + ".",
        TestUtils.OK, resetResponse.getStatus()
    );
    
    assertEquals(
        "Response body should have been " + TestUtils.ResetOK + ".",
        TestUtils.ResetOK, resetResponse.readEntity(String.class)
    );
  }
  
  @Test
  public void testPostResetWithoutAdminRole() throws Exception {
    String authHeader = "Bearer " + new JwtVerifier().createUserJwt(TESTNAME);
    String resetUrl = baseUrl + RESET;
    JsonObjectBuilder state = Json.createObjectBuilder();
    state.add("state","scoreboard with two scores");
    Response resetResponse = TestUtils.processRequest(resetUrl, "POST", state.build().toString(), authHeader);
    
    assertEquals("HTTP response code should have been "
        + TestUtils.FORBIDDEN + ".", TestUtils.FORBIDDEN,
        resetResponse.getStatus()
    );    
  }
}
// end::test[]
