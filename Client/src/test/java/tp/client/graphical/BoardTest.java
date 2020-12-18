package tp.client.graphical;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class BoardTest {
	@Test
	public void BoardGUITest() throws InterruptedException {
		FieldGUI[] map = new FieldGUI[2];
		FieldGUI t1 =  Mockito.spy(new FieldGUI(0,0));
		FieldGUI t2 = Mockito.spy(new FieldGUI(10,10));
		map[0] = t1;
		map[1] = t2;
		
		JFrame testwind = new JFrame();
		testwind.setSize(200,200);
		testwind.setVisible(true);
		BoardGUI undertest = new BoardGUI(map, 20);
		testwind.add(undertest);
		testwind.revalidate();
		testwind.repaint();
		
		PawnGUI[] pieces = new PawnGUI[1];
		PawnGUI testpawn = Mockito.spy(new PawnGUI(0,0));
		pieces[0] = testpawn;
		
		undertest.setPawns(pieces);
		
		//wait for AWT dispatch
		java.util.concurrent.TimeUnit.MILLISECONDS.sleep(500);
		Mockito.verify(t1, Mockito.atLeastOnce()).render(Mockito.any(), Mockito.eq(3.2), Mockito.eq(100.0), Mockito.eq(81.0));
		Mockito.verify(testpawn, Mockito.atLeastOnce()).render(Mockito.any(), Mockito.eq(3.2), Mockito.eq(100.0), Mockito.eq(81.0));
		
		PawnMovementHandler fakhan = Mockito.mock(PawnMovementHandler.class);
		Mockito.when(fakhan.handlePawnMovement(Mockito.any(), Mockito.any())).thenReturn(true).thenReturn(false);
		undertest.setMovementHandler(fakhan);
		
		MouseEvent press = new MouseEvent(undertest, MouseEvent.MOUSE_PRESSED, 1, MouseEvent.BUTTON1_DOWN_MASK, 100, 81, 0, false, MouseEvent.BUTTON1);
		undertest.dispatchEvent(press);
		MouseEvent drag = new MouseEvent(undertest, MouseEvent.MOUSE_DRAGGED, 2, MouseEvent.BUTTON1_DOWN_MASK, 132, 113, 0, false, MouseEvent.BUTTON1);
		undertest.dispatchEvent(drag);
		MouseEvent release = new MouseEvent(undertest, MouseEvent.MOUSE_RELEASED, 3, 0, 132, 113, 1, false, MouseEvent.BUTTON1);
		undertest.dispatchEvent(release);
		
		
		MouseEvent press2 = new MouseEvent(undertest, MouseEvent.MOUSE_PRESSED, 11, MouseEvent.BUTTON1_DOWN_MASK, 132, 113, 0, false, MouseEvent.BUTTON1);
		undertest.dispatchEvent(press2);
		MouseEvent drag2 = new MouseEvent(undertest, MouseEvent.MOUSE_DRAGGED, 12, MouseEvent.BUTTON1_DOWN_MASK, 1, 1, 0, false, MouseEvent.BUTTON1);
		undertest.dispatchEvent(drag2);
		MouseEvent release2 = new MouseEvent(undertest, MouseEvent.MOUSE_RELEASED, 13, 0, 1, 1, 1, false, MouseEvent.BUTTON1);
		undertest.dispatchEvent(release2);
		
		
		MouseEvent press3 = new MouseEvent(undertest, MouseEvent.MOUSE_PRESSED, 11, MouseEvent.BUTTON1_DOWN_MASK, 132, 113, 0, false, MouseEvent.BUTTON1);
		undertest.dispatchEvent(press3);
		MouseEvent drag3 = new MouseEvent(undertest, MouseEvent.MOUSE_DRAGGED, 12, MouseEvent.BUTTON1_DOWN_MASK, 100, 81, 0, false, MouseEvent.BUTTON1);
		undertest.dispatchEvent(drag3);
		MouseEvent release3 = new MouseEvent(undertest, MouseEvent.MOUSE_RELEASED, 13, 0, 100, 81, 1, false, MouseEvent.BUTTON1);
		undertest.dispatchEvent(release3);
		
		Mockito.verify(testpawn, Mockito.times(1)).setCoords(100.0, 81.0, 3.2, 100.0, 81.0);
		Mockito.verify(testpawn, Mockito.times(1)).setCoords(1.0, 1.0, 3.2, 100.0, 81.0);
		Mockito.verify(testpawn, Mockito.times(3)).setCoords(10.0, 10.0);
		
	}
}
