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

//import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import org.junit.Before;
import org.junit.Test;
import it.util.TestUtils;
import it.util.JwtVerifier;

public class ResetEndpointTest {

  private final String RESET = "/leaderboard/reset";
  private final String TESTNAME = "TESTUSER";

  String baseUrl = "https://localhost:" + System.getProperty("liberty.test.ssl.port");

  String authHeader;

  @Before
  public void setup() throws Exception {
    authHeader = "Bearer " + new JwtVerifier().createAdminJwt(TESTNAME);
  }

  @Test
  public void testGetResetWithJwt() {
    String resetUrl = baseUrl + RESET;
    Response resetResponse = TestUtils.processRequest(resetUrl, "GET", null, authHeader);

    assertEquals(
        "HTTP response code should have been " + TestUtils.OK + ".",
        TestUtils.OK, resetResponse.getStatus());
  }
  
  //@Test
  public void testPostResetWithJwt() {
    String resetUrl = baseUrl + RESET;
    Response resetResponse = TestUtils.processRequest(resetUrl, "POST", null, authHeader);

    assertEquals(
        "HTTP response code should have been " + TestUtils.OK + ".",
        TestUtils.OK, resetResponse.getStatus());
  }
}
// end::test[]
