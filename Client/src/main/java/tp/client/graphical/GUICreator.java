package tp.client.graphical;

import javax.swing.JFrame;

public class GUICreator {
	public BoardGUI createBoard(FieldGUI[] map, int size) {
		return new BoardGUI(map, size);
	}
	
	public NetworkGUI createNetwork(GUIManager up) {
		return new NetworkGUI(up,null,null,null);
	}
	
	public ActionsGUI createActions(GUIManager up) {
		return new ActionsGUI(up,null,null,null);
	}
	
	public JFrame createWindow(String title) {
		return new JFrame(title);
	}
}
