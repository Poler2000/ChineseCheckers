package tp.server.communication;

import org.junit.Before;
import org.junit.Test;
import tp.server.logic.Game;
import tp.server.tmp.FakeClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

public class CommunicationCenterTest {

    CommunicationCenter center;

    FakeClient c1;
    FakeClient c2;

    //@Before
    public void setup() {
        try {
            center = new CommunicationCenter(1410, new Game());
            c1 = new FakeClient();
            c2 = new FakeClient();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void establishConnectionsTest() {
        setup();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    c1.init("localhost", 1410);
                    c1.sendRegister();
                    Thread.sleep(100);
                    c2.init("localhost", 1410);
                    c2.sendRegister();
                    Thread.sleep(100);
                    center.stopListeningForNewClients();
                    /*c1.sendSetup();

                    while (true) {
                        Thread.sleep(100);
                        c1.sendMove();
                        Thread.sleep(100);
                        c2.sendMove();
                    }*/
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };
        Thread t = new Thread(r);
        t.start();
        int n = center.establishConnections();
        assertEquals(2, n);
    }

    @Test
    public void sendMessageToAllTest() {

    }

    @Test
    public void sendMessageTest() {
    }

}
