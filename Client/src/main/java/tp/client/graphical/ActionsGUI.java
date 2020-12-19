package tp.client.graphical;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * A class representing a bar with buttons
 * using which the user can control the gamestate
 * @author anon
 *
 */
public class ActionsGUI extends JPanel{
    private JButton startGameButton;
    private JButton endTurnButton;
    private JLabel statusText;

    /**
     * The default constructor
     * If any of the optional parameters is null
     * they all get replaced by defaults
     * @param listener the object to call back on user actions (handleStartGame, handleEndTurn)
     * @param sgb optional: the start game button
     * @param etb optional: the end turn button
     * @param slbl optional: the game status label
     */
    public ActionsGUI(final GUIManager listener, JButton sgb, JButton etb, JLabel slbl){
        super();
        startGameButton = new JButton("Start gry");
        endTurnButton = new JButton("Koniec tury");
        statusText = new JLabel("Czekam na połączenie");
        if (sgb != null && etb != null && slbl != null) {
        	startGameButton = sgb;
        	endTurnButton = etb;
        	statusText = slbl;
        }
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

    /**
     * Set the game state label
     * @param text text to set
     */
    public void setLabel(String text){
        statusText.setText(text);
    }

    /**
     * enable/disable the start game button
     * @param doit en/dis
     */
    public void enableStartGame(boolean doit){
        startGameButton.setEnabled(doit);
    }

    /**
     * enable/disable the end turn button
     * @param doit en/dis
     */
    public void enableEndTurn(boolean doit){
        endTurnButton.setEnabled(doit);
    }

}