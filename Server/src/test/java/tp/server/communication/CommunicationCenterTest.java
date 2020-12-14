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
                try
                {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                c1.init("localhost", 1410);
                c2.init("localhost", 1410);
            }
        };
        Thread t = new Thread(r);
        t.start();
        center.establishConnections();

    }

    @Test
    public void sendMessageToAllTest() {

    }

    @Test
    public void sendMessageTest() {
    }

}
