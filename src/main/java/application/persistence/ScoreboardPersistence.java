package application.persistence;

import java.util.List;

import application.rest.v1.Score;

public interface ScoreboardPersistence {

    void addScore(Score score);

    List<Score> getTopTenScores();

}