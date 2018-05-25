package application.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import application.rest.v1.Score;

public class ScoreboardInMemory implements ScoreboardPersistence {

    private List<Score> scores = new ArrayList<>();

    public void addScore(Score score) {
        scores = Stream
        .concat(scores.stream(),Stream.of(score))
        .sorted((o1,o2)->o2.compareTo(o1))
        .limit(10)
        .collect(Collectors.toList());
    }

    public List<Score> getTopTenScores() {
        return scores;
    }

}