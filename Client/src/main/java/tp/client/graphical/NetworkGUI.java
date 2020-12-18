package tp.client.graphical;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NetworkGUI extends JPanel{
    private JTextField serverAddress;
    private JButton connectButton;
    private JLabel connStatus;

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

    public void setConnStatus(String text){
        connStatus.setText(text);
    }
}
