package server;

import server.core.gui.ServerFrame;

import javax.swing.*;

/**
 * {@code ServerMain} represents the class main of the server
 *
 * @author Safiy ZAGHBANE
 * @see server.core.ServerCore
 */
public class ServerMain {


	public static void main(String[] args) {

		try {
			for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		java.awt.EventQueue.invokeLater(ServerFrame::new);
	}

}
