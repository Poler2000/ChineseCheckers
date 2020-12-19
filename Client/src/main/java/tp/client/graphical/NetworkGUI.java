package tp.client.graphical;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Class representing the network bar
 * allowing users to connect to a chosen server
 * @author anon
 *
 */
public class NetworkGUI extends JPanel{
    private JTextField serverAddress;
    private JButton connectButton;
    private JLabel connStatus;

    /**
     * Default constructor
     * all optional parameters are replaced by defaults if any is missing
     * @param listener the object to callback on connect request (handleConnectServer)
     * @param tfield the server address textfield
     * @param bttn the connect button
     * @param lbl the network status label
     */
    public NetworkGUI(final GUIManager listener, JTextField tfield, JButton bttn, JLabel lbl){
        super();
        serverAddress = new JTextField("localhost");
        connectButton = new JButton("Połącz");
        connStatus = new JLabel("Disconnected");
        if (tfield != null && bttn != null && lbl != null) {
        	serverAddress = tfield;
        	connectButton = bttn;
        	connStatus = lbl;
        }
        add(serverAddress);
        add(connectButton);
        add(connStatus);

        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                listener.handleConnectServer(serverAddress.getText());
            }
        });

    }

    /**
     * Set the network status label text
     * @param text text to set
     */
    public void setConnStatus(String text){
        connStatus.setText(text);
    }
}
