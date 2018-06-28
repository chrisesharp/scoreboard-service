package application.persistence;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Priority;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import application.rest.Score;
import injection.config.ConfigAlternative;

@Priority(10)
@ConfigAlternative("POSTGRES_HOSTNAME")
@Resource(lookup = "jdbc/db", name = "jdbc/db")
public class ScoreboardJpa implements ScoreboardPersistence {

    @PersistenceContext(unitName = "scoreboardpersistenceunit")
    private EntityManager entityManager;
  
    @Resource
    private UserTransaction userTransaction;
  
    private Logger log = Logger.getLogger(ScoreboardJpa.class.getName());

    public void addScore(Score score) {
        log.info("Adding score to DB: " + score);
        try {
          userTransaction.begin();
          entityManager.persist(score);
          userTransaction.commit();
        } catch (Exception exc) {
          throw new RuntimeException(exc);
        }
    }

    public List<Score> getTopTenScores() {
        List<Score> scores;
        try {
          TypedQuery<Score> query = entityManager.createQuery("SELECT s FROM Score AS s ORDER BY s.score DESC", Score.class).setMaxResults(10);
          scores = query.getResultList();
        } catch (Exception exc) {
          throw new RuntimeException(exc);
        }
        log.info("Retrieved scores from DB: " + scores);
        return scores;
    }
    
    public void deleteAll() {
      log.info("Deleting all scores");
      try {
        userTransaction.begin();
        TypedQuery<Score> deleteQuery = entityManager.createQuery("DELETE FROM Score", Score.class);
        int i = deleteQuery.executeUpdate();
        userTransaction.commit();
        log.info(i + " scores deleted");
      } catch (Exception exc) {
        throw new RuntimeException(exc);
      }
    }
}