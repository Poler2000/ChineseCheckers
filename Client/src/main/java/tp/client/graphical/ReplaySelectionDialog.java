package tp.client.graphical;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import tp.client.structural.Replay;


public class ReplaySelectionDialog extends JFrame{
	private Replay[] givenReps;
	private GUIManager upstream;
	private JList<String> listView;
	
	public ReplaySelectionDialog(Replay[] list, GUIManager parent) {
		super("Wybierz nagranie");
		
		givenReps = list;
		upstream = parent;
		
		String[] entries = new String[list.length];
		for (int i = 0; i < list.length; i++) {
			entries[i] = "ID: " + list[i].id + " Gracze: " + list[i].players + " Data: " + list[i].date;
		}
		
		listView = new JList<String>(entries);
		listView.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listView.setLayoutOrientation(JList.VERTICAL);
		
		add(listView);
		
		JPanel bottomBar = new JPanel();
		JButton confirm = new JButton("Wybierz");
		JButton cancel = new JButton("Anuluj");
		
        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
            	int selidx = listView.getSelectedIndex();
            	if (selidx != -1) {
            		int replayID = givenReps[selidx].id;
            		//System.out.println("ID to " + replayID);
                    upstream.handleLoadReplay(replayID);
                    setVisible(false);
                    dispose();
            	}
            }
        });
        
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                setVisible(false);
                dispose();
            }
        });
		
		bottomBar.add(cancel);
		bottomBar.add(confirm);
		
		add(bottomBar, BorderLayout.PAGE_END);
		
		setSize(400, 400);
	}
	
}
