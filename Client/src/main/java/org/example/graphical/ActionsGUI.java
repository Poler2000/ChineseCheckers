package graphical;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ActionsGUI extends JPanel{
    private JButton startGameButton;
    private JButton endTurnButton;
    private JLabel statusText;

    public ActionsGUI(GUIManager listener){
        super();
        startGameButton = new JButton("Start gry");
        endTurnButton = new JButton("Koniec tury");
        statusText = new JLabel("Czekam na połączenie");
        add(startGameButton);
        add(endTurnButton);
        add(statusText);
        startGameButton.setEnabled(false);
        endTurnButton.setEnabled(false);

        startGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                listener.handleStartGame();
            }
        });

        endTurnButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                listener.handleEndTurn();
            }
        });
    }

    public void setLabel(String text){
        statusText.setText(text);
    }

    public void enableStartGame(boolean doit){
        startGameButton.setEnabled(doit);
    }

    public void enableEndTurn(boolean doit){
        endTurnButton.setEnabled(doit);
    }

}