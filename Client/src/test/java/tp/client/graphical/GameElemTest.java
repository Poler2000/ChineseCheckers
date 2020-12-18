package tp.client.graphical;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GameElemTest {
	@Test
	public void FieldGUITest() {
		FieldGUI undertest = new FieldGUI(0,0);
		assertFalse(undertest.checkCollision(0.7, 0.75, 1, 0, 0));
		assertTrue(undertest.checkCollision(0.7, 0.75, 2, 0, 0));
		undertest.setCoords(20, 20, 800, 400, 700);
		assertTrue(undertest.checkCollision(20, 20, 800, 400, 700));
		
		Graphics2D mockgra = Mockito.mock(Graphics2D.class);
		undertest.render((Graphics)mockgra, 400, 500, 500);
		Mockito.verify(mockgra).draw(Mockito.any());
	}
	
	@Test
	public void PawnGUITest() {
		PawnGUI undertest = new PawnGUI(0,0);
		undertest.setCoords(20, 20, 800, 400, 700);
		assertTrue(undertest.checkCollision(20, 20, 800, 400, 700));
		assertFalse(undertest.checkCollision(1000, 20, 800, 400, 700));
		undertest.setColor(Color.CYAN);
		Graphics2D mockgra = Mockito.mock(Graphics2D.class);
		undertest.render(mockgra, 800, 400, 700);
		Mockito.verify(mockgra).setColor(Color.CYAN);
		Mockito.verify(mockgra).fillOval(-580, -580, 1200, 1200);
		
	}
	
	
}
