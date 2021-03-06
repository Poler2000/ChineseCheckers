package tp.server.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import tp.server.structural.Move;
import tp.server.structural.Replay;
import tp.server.structural.Step;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to mediate between database and app
 */
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

    /**
     * creates game in database
     * @param players number of players
     * @return success status
     */
    public boolean createGame(final int players) {
        Session session = factory.openSession();
        Transaction tx = null;
        if (players < 2 || players == 5 || players > 6) {
            return false;
        }

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

    /**
     * adds given move to the database
     * @param playerId id of player who perform move
     * @param move move to save
     * @return success status
     */
    public boolean addMove(final int playerId, final Move move) {
        if (gameId < 1 || move == null) {
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

    /**
     * returns all moves from specified game
     * @param gameId  gameId
     * @return all moves from specified game
     */
    public List getMovesForGame(int gameId) {
        Session session = factory.openSession();

        Query query = session.createQuery("from MovesEntity where gameId = :id");
        query.setParameter("id", gameId);
        return query.list();
    }

    /**
     * @return all replay games from database
     */
    public List<Replay> getGames() {
        Session session = factory.openSession();

        Query query = session.createQuery("from GamesEntity");
        List<GamesEntity> gamesEntities = query.list();
        List<Replay> replays = new ArrayList<>();
        for (GamesEntity g : gamesEntities) {
            replays.add(new Replay(g));
        }
        return replays;
    }

    /**
     * returns replay with specified id
     * @param gameId
     * @return
     */
    public Replay getGameById(int gameId) {
        Session session = factory.openSession();

        Query query = session.createQuery("from GamesEntity where id=:id");
        query.setParameter("id", gameId);
        List<GamesEntity> gamesEntities = query.list();
        List<Replay> replays = new ArrayList<>();
        for (GamesEntity g : gamesEntities) {
            replays.add(new Replay(g));
        }
        return replays.get(0);
    }
}
