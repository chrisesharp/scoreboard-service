package application.rest;

import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.OpenAPI;
import org.eclipse.microprofile.openapi.models.Paths;
//import org.eclipse.microprofile.openapi.models.Operation;
//import org.eclipse.microprofile.openapi.models.PathItem;

public class ScoreboardOASFilter implements OASFilter {
  /*
  public Operation filterOperation(Operation operation) {
    return (operation.getOperationId().equals("reset")) ? null: operation;
  }
  
  public PathItem filterPathItem(PathItem pathItem) {
    return (pathItem.readOperations().size()==0) ? null : pathItem;
  }
  */
  public void filterOpenAPI(OpenAPI openApi) {
    Paths paths = openApi.getPaths();
    paths.remove("/reset");
  }
}
