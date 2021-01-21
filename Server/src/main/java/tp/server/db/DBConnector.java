package tp.server.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import tp.server.structural.Move;
import tp.server.structural.Step;

import java.sql.Timestamp;
import java.util.List;

public class DBConnector {
    private static SessionFactory factory;
    private int gameId = 0;

    public DBConnector() {
        try {
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public boolean createGame(final int players) {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            GamesEntity entity = new GamesEntity();
            entity.setPlayers(players);
            entity.setStart(new Timestamp(System.currentTimeMillis()));
            gameId = (Integer) session.save(entity);

            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
            System.out.println("ok");
        }
        System.out.println(gameId);
        return gameId > 0;
    }

    public boolean addMove(final int playerId, final Move move) {
        if (gameId < 1) {
            return false;
        }
        Session session = factory.openSession();
        Transaction tx = null;
        int success = 0;

        try {
            tx = session.beginTransaction();
            MovesEntity entity = new MovesEntity();
            List<Step> steps = move.getSteps();
            entity.setPlayerId(playerId);
            entity.setGameId(gameId);
            entity.setPawnId(steps.get(steps.size() - 1).getPawn().getId());
            entity.setDestX(steps.get(steps.size() - 1).getDestination().coordinatesAsXYZ().x);
            entity.setDestY(steps.get(steps.size() - 1).getDestination().coordinatesAsXYZ().y);
            entity.setDestZ(steps.get(steps.size() - 1).getDestination().coordinatesAsXYZ().z);
            success = (int) session.save(entity);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }
        return success > 0;
    }

    public List getMovesForGame(int gameId) {
        Session session = factory.openSession();

        Query query = session.createQuery("from MovesEntity where gameId = :id");
        query.setParameter("id", gameId);
        return query.list();
    }
}
