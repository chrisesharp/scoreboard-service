package application.rest.v1;

import io.swagger.annotations.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Api(tags={"Scoreboard"})
@ApiModel()
@Entity
public class Score implements Comparable<Score> {
  
  @Column
  @ApiModelProperty(required = true, example = "chris")
  private String player;

  @Column
  @ApiModelProperty(required = true, example = "10000")
  private int score;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  protected Score() {
    
  }
  
  public Score(String player, int score) {
    this.player = player;
    this.score = score;
  }
  
  /**
   * @param score the score to set
   */
  public void setScore(int score) {
  	this.score = score;
  }
  
  /**
   * @param player the player to set
   */
  public void setPlayer(String player) {
  	this.player = player;
  }
  
  /**
   * @return the score
   */
  public int getScore() {
  	return score;
  }
  
  /**
   * @return the player
   */
  public String getPlayer() {
  	return player;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Score) {
      Score theScore = (Score) obj;
      return this.player == theScore.player && this.score == theScore.score;
    }
    return false;
  }
  
  @Override
  public int hashCode() {
    return this.player.hashCode() + this.score * 31;
  }
  
  @Override
  public String toString() {
    return this.player + this.score;
  }
  
  public int compareTo(Score other) {
    return this.score - other.score;
  } 
}