package it;

import org.junit.runner.RunWith;

import au.com.dius.pact.provider.junit.PactRunner;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.HttpTarget;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.junit.loader.PactFolder;


@RunWith(PactRunner.class)
@Provider("scoreboard_provider")
@PactFolder("pacts")
public class ScoresPactTest {
  private int port = Integer.parseInt(System.getProperty("liberty.test.port"));
  
  @TestTarget
  public final Target target = new HttpTarget(port);

  @State("empty scoreboard")
  public void toEmptyState() {
    System.out.println("toEmptyState got called");
    
  }
  @State("scoreboard with low score")
  public void toLowScoreState() {
    System.out.println("toLowScoreState got called");
  }
  @State("scoreboard with two scores")
  public void toTwoScoreState() {
    System.out.println("toTwoScoreState got called");
  }
}