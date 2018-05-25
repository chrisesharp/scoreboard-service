package application.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.UserTransaction;

import application.rest.v1.Score;

public class ScoreboardJpa implements ScoreboardPersistence {

    private EntityManager entityManager;

    private UserTransaction userTransaction;

    public ScoreboardJpa(EntityManager entityManager, UserTransaction userTransaction) {
        this.entityManager = entityManager;
        this.userTransaction = userTransaction;
    }

    public void addScore(Score score) {
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
        return scores;
    }

}