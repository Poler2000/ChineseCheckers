package tp.client.game;

import tp.client.graphical.GUIManager;
import tp.client.network.NetworkManager;

public class ClientLauncher {

	public static void main(String[] args) {
		GUIManager gui = new GUIManager(null);
		NetworkManager net = new NetworkManager();
		GameManager main = new GameManager(gui, net);
	}

}
