package tp.client.graphical;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class BarTest {
	@Test
	public void networkBarTest() {
		GUIManager mockman = Mockito.mock(GUIManager.class);
		JTextField mocktxt = Mockito.spy(JTextField.class);
		JButton mockbtn = Mockito.spy(JButton.class);
		JLabel mocklbl = Mockito.spy(JLabel.class);
		
		NetworkGUI undertest = new NetworkGUI(mockman, mocktxt, mockbtn, mocklbl);
		Mockito.verify(mockbtn).addActionListener(Mockito.any());
		undertest.setConnStatus("test");
		Mockito.verify(mocklbl).setText("test");
		mocktxt.setText("localhost");
		mockbtn.doClick();
		Mockito.verify(mocktxt).getText();
		Mockito.verify(mockman).handleConnectServer("localhost");
	}
	
	@Test
	public void userBarTest() {
		GUIManager mockman = Mockito.mock(GUIManager.class);
		JButton mockbtn = Mockito.spy(JButton.class);
		JButton mockbtn2 = Mockito.spy(JButton.class);
		JLabel mocklbl = Mockito.spy(JLabel.class);
		JButton mockbtn3 = Mockito.spy(JButton.class);
		
		ActionsGUI undertest = new ActionsGUI(mockman, mockbtn, mockbtn2, mocklbl, mockbtn3);
		undertest.setLabel("hello");
		Mockito.verify(mocklbl).setText("hello");
		undertest.enableStartGame(true);
		Mockito.verify(mockbtn).setEnabled(true);
		undertest.enableEndTurn(true);
		Mockito.verify(mockbtn2).setEnabled(true);
		mockbtn.doClick();
		Mockito.verify(mockman).handleStartGame();
		mockbtn2.doClick();
		Mockito.verify(mockman).handleEndTurn();
	}
}
