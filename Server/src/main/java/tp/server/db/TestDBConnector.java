package tp.server.db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import tp.server.structural.Field;
import tp.server.structural.Move;
import tp.server.structural.Pawn;
import tp.server.structural.Step;

import java.sql.Timestamp;

public class TestDBConnector {
    private static SessionFactory factory;
    private int gameId = 0;

    public static void main(String[] args) {
        Move test = new Move();
        Move test2 = new Move();
        test.addStep(new Step(new Pawn(new Field(1,2,3), 3), new Field(2,3,3)));
        test2.addStep(new Step(new Pawn(new Field(1,2,3), 1), new Field(2,-3,3)));
        test2.addStep(new Step(new Pawn(new Field(1,2,3), 1), new Field(2,-3,-4)));
        DBConnector dbConnector = new DBConnector();
        System.out.println(dbConnector.createGame(3));
        System.out.println(dbConnector.addMove(1, test));
        System.out.println(dbConnector.addMove(2, test2));
        System.out.println(dbConnector.getMovesForGame(13).size());
    }
}
