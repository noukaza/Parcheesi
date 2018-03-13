package client.core.gui;

import client.core.handler.ServerHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Random;

public class GameLayout {
	private JTabbedPane tabbedPane1;
	private JPanel panel1;
	private JPanel dashPanel;
	private JList listeRoom;
	private JButton startButton;
	private JButton exitButton;
	private JButton addRoomButton;
	private Boolean gamePanelExist;
	private String roomName;
	private ServerHandler serverHandler;


	public GameLayout(ServerHandler serverHandler) {
		JFrame frame = new JFrame("GameLayout");
		frame.setContentPane(this.panel1);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		this.serverHandler = serverHandler;
		gamePanelExist = false;

		startButton.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!gamePanelExist) {
					//TODO a custom Panel to the game
					JPanel GamePanel = new JPanel();
					tabbedPane1.addTab("Game", GamePanel);
					gamePanelExist = !gamePanelExist;
				}
				tabbedPane1.setSelectedComponent(tabbedPane1.getComponentAt(1));
			}
		});

		addRoomButton.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				roomName = JOptionPane.showInputDialog("Name of the room ");
				Random r = new Random();
				serverHandler.creatRoom(roomName);

			}
		});

		exitButton.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				serverHandler.disconnect();
				System.exit(0);
			}
		});

	}

	//pour le test
	public void setListeRoom(ArrayList<String> test) {
		for (String nam : test) {
			// add item to the liste
		}
	}


}
