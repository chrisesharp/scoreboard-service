package application.rest.v1;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class ScoresTest {
  @Test
  public void testEmptyScoreboard() throws Exception {
    List<Score> expected = new ArrayList<>();
    Scoreboard scoreboard = new Scoreboard();
    Response scoresResponse = scoreboard.scores();
    assertEquals(expected, scoresResponse.readEntity(List.class));
  }
  
  @Test
  public void testSingleEntryScoreboard() throws Exception {
    List<Score> expected = new ArrayList<>();
    Score expectedScore = new Score("chris", 100);
    expected.add(expectedScore);
    
    Scoreboard scoreboard = new Scoreboard();
    Score ourScore = new Score("chris", 100);
    scoreboard.addScore(ourScore);
    
    Response scoresResponse = scoreboard.scores();
    assertEquals(expected, scoresResponse.readEntity(List.class));
  }
  
  @Test
  public void testOrderedScoreboard() throws Exception {
    List<Score> expected = new ArrayList<>();
    Score score1 = new Score("chris", 100);
    Score score2 = new Score("david", 200);
    expected.add(score2);
    expected.add(score1);
    
    Scoreboard scoreboard = new Scoreboard();
    Score ourScore1 = new Score("chris", 100);
    Score ourScore2 = new Score("david", 200);
    scoreboard.addScore(ourScore1);
    scoreboard.addScore(ourScore2);
    
    Response scoresResponse = scoreboard.scores();
    assertEquals(expected, scoresResponse.readEntity(List.class));
  }
  
  @Test
  public void testOrderedScoreboardWithThreeScores() throws Exception {
    List<Score> expected = new ArrayList<>();
    Score score1 = new Score("chris", 100);
    Score score2 = new Score("david", 200);
    Score score3 = new Score("chris", 300);
    expected.add(score3);
    expected.add(score2);
    expected.add(score1);
    
    Scoreboard scoreboard = new Scoreboard();
    Score ourScore1 = new Score("chris", 100);
    Score ourScore2 = new Score("david", 200);
    Score ourScore3 = new Score("chris", 300);
    scoreboard.addScore(ourScore1);
    scoreboard.addScore(ourScore2);
    scoreboard.addScore(ourScore3);
        
    Response scoresResponse = scoreboard.scores();
    assertEquals(expected, scoresResponse.readEntity(List.class));
  }
  
  @Test
  public void testOrderedScoreboardWithMultipleEqualScores() throws Exception {
    List<Score> expected = new ArrayList<>();
    Score score1 = new Score("chris", 100);
    Score score2 = new Score("david", 100);
    Score score3 = new Score("fred", 500);
    Score score4 = new Score("fred", 50);
    expected.add(score3);
    expected.add(score1);
    expected.add(score2);
    expected.add(score4);
    
    Scoreboard scoreboard = new Scoreboard();
    Score ourScore1 = new Score("chris", 100);
    Score ourScore2 = new Score("david", 100);
    Score ourScore3 = new Score("fred", 500);
    Score ourScore4 = new Score("fred", 50);
    scoreboard.addScore(ourScore1);
    scoreboard.addScore(ourScore3);
    scoreboard.addScore(ourScore2);
    scoreboard.addScore(ourScore4);
        
    Response scoresResponse = scoreboard.scores();
    assertEquals(expected, scoresResponse.readEntity(List.class));
  }
  
  @Test
  public void testMaxTenScoresOnScoreboard() throws Exception {    
    Scoreboard scoreboard = new Scoreboard();
    for (int i=0;i<12;i++) {
      Score score = new Score("foobar", i);
      scoreboard.addScore(score);
    }
        
    Response scoresResponse = scoreboard.scores();
    List<Score> results = scoresResponse.readEntity(List.class);
    assertEquals(10, results.size());
  }
}