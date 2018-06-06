package application.persistence;

import java.util.List;

import application.rest.Score;

public interface ScoreboardPersistence {

    void addScore(Score score);

    List<Score> getTopTenScores();

}