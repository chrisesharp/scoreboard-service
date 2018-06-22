package it;

import static org.junit.Assert.assertTrue;
import javax.ws.rs.core.Response;

import org.junit.Test;
import it.util.TestUtils;

public class HealthEndpointIT {

    private String port = System.getProperty("liberty.test.port");
    private String endpoint = "/health";
    private String url = "http://localhost:" + port + endpoint;
    

    @Test
    public void testEndpoint() throws Exception {
        System.out.println("Testing endpoint " + url);
        int maxCount = 10;
        Response healthResponse = TestUtils.processRequest(url, "GET", null, null);
        int responseCode = healthResponse.getStatus();

        for(int i = 0; (responseCode != TestUtils.OK) && (i < maxCount); i++) {
          System.out.println("Response code : " + responseCode + ", retrying ... (" + i + " of " + maxCount + ")");
          Thread.sleep(5000);
          responseCode = TestUtils.processRequest(url, "GET", null, null).getStatus();
        }
        assertTrue("Incorrect response code: " + responseCode, responseCode == TestUtils.OK);
    }
}
