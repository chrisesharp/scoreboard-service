package application.persistence;

import java.util.logging.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Default;

import application.rest.v1.Score;

@Default
@ApplicationScoped
public class ScoreboardInMemory implements ScoreboardPersistence {
  
    private Logger log = Logger.getLogger(ScoreboardJpa.class.getName());

    private List<Score> scores = new ArrayList<>();

    public void addScore(Score score) {
        log.info("Adding score to list: " + score);
        scores = Stream
        .concat(scores.stream(),Stream.of(score))
        .sorted((o1,o2)->o2.compareTo(o1))
        .limit(10)
        .collect(Collectors.toList());
    }

    public List<Score> getTopTenScores() {
        log.info("Retrieved scores from list: " + scores);
        return scores;
    }

}