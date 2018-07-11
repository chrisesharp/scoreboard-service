package application.rest;

import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Paths;

public class ScoreboardOASFilter implements OASFilter {
  public void filterOpenAPI(OpenAPI openApi) {
    Paths paths = openApi.getPaths();
    paths.remove("/reset");
  }
}
