package tp.client.network;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class SocketTest {

	@Test
	public void messageExchangeTest() throws InterruptedException {
		FakeServer fserv = new FakeServer();
		NetworkManager callb = Mockito.mock(NetworkManager.class);
		
		ConnMan undertest = new ConnMan(callb);
		//wait for networking
		java.util.concurrent.TimeUnit.MILLISECONDS.sleep(100);
		
		undertest.connect("localhost");
		java.util.concurrent.TimeUnit.MILLISECONDS.sleep(100);
		
		undertest.connect("localhost");
		java.util.concurrent.TimeUnit.MILLISECONDS.sleep(100);
		
		undertest.send("Hello!");
		java.util.concurrent.TimeUnit.MILLISECONDS.sleep(100);
		
		assertEquals(fserv.lastmsgs, "Hello!\nMessageTerminated\n");
		
		fserv.write("We do what we must, because we can!\r\nMessageTerminated\r\n");
		java.util.concurrent.TimeUnit.MILLISECONDS.sleep(100);
		
		Mockito.verify(callb).handleIncoming("We do what we must, because we can!");
		Mockito.verify(callb, Mockito.times(2)).connStateChanged(true);
		Mockito.verify(callb, Mockito.times(1)).connStateChanged(false);
		
		fserv.end = true;
		
		
		
	}
}
