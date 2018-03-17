package client.core.gui;

import client.core.ClientCore;

import javax.swing.*;

public class ConnectionLayout {
	private JPanel connectionPanel;
	private JTextField serverAdress_tf;
	private JTextField port_tf;
	private JButton connectButton;
	private JButton cancelButton;
	private JLabel port;
	private JLabel serverAdress;

	public ConnectionLayout() {
		final JFrame frame = new JFrame("Connection");
		frame.setContentPane(this.connectionPanel);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		connectButton.addActionListener(e -> {
			int port = Integer.parseInt(port_tf.getText());
			String serverAdress = serverAdress_tf.getText();
			new Thread(new ClientCore(serverAdress, port)).start();
			frame.setVisible(false);
			frame.dispose();
		});

		cancelButton.addActionListener(e -> {
			frame.setVisible(false);
			frame.dispose();
		});
	}
}
