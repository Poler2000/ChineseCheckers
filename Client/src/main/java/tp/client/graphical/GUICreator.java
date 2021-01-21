package tp.client.graphical;

import javax.swing.JFrame;

/**
 * A helper class for creating many GUI primitives
 * @author anon
 *
 */
public class GUICreator {
	///Create a BoardGUI
	public BoardGUI createBoard(FieldGUI[] map, int size) {
		return new BoardGUI(map, size);
	}
	///Create a NetworkGUI
	public NetworkGUI createNetwork(GUIManager up) {
		return new NetworkGUI(up,null,null,null);
	}
	///Create an ActionsGUI
	public ActionsGUI createActions(GUIManager up) {
		return new ActionsGUI(up,null,null,null,null);
	}
	///Create a JFrame
	public JFrame createWindow(String title) {
		return new JFrame(title);
	}
}
