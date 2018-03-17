/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.core.gui;

import server.core.ServerCore;

import javax.swing.*;

public class ServerFrame extends JFrame {

	private JTextArea console_txta;
	private ServerCore serverCore = null;

    /**
     * Creates new form ServerFrame
     */
    public ServerFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

	    JPanel jPanel = new JPanel();
	    JButton startServer_btn = new JButton();
	    JButton closeServer_btn = new JButton();
	    JScrollPane jScrollPane1 = new JScrollPane();
	    console_txta = new JTextArea();

	    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        startServer_btn.setText("Start server");
	    startServer_btn.addActionListener(evt -> {
		    String answer = JOptionPane.showInputDialog(this,
				    "Text",
				    "[sample text to help input]",
				    JOptionPane.INFORMATION_MESSAGE);
		    if (answer != null && ! answer.isEmpty()) {
			    int port = Integer.parseInt(answer);
			    new Thread(new ServerCore(this, port)).start();
		    }
        });

        closeServer_btn.setText("Close server");
	    closeServer_btn.addActionListener(evt -> {
		    if (serverCore != null) {
			    serverCore.finish();
			    setVisible(false);
			    dispose();
		    }
	    });

        console_txta.setEditable(false);
        console_txta.setColumns(20);
        console_txta.setRows(5);
        jScrollPane1.setViewportView(console_txta);

	    GroupLayout jPanelLayout = new GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
		        jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelLayout.createSequentialGroup()
		                        .addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				                        .addGroup(GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                                                .addContainerGap(471, Short.MAX_VALUE)
                                                .addComponent(closeServer_btn)
						                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(startServer_btn))
                                        .addComponent(jScrollPane1))
                                .addContainerGap())
        );
        jPanelLayout.setVerticalGroup(
		        jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
				        .addGroup(GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                                .addContainerGap()
						        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
						        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
						        .addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(startServer_btn)
                                        .addComponent(closeServer_btn))
                                .addContainerGap())
        );

	    GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
		        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				        .addComponent(jPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
		        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				        .addComponent(jPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
	    setVisible(true);
    }// </editor-fold>//GEN-END:initComponents

	public void registerServer(ServerCore serverCore) {
		this.serverCore = serverCore;
	}

	public void println(String s) {
		console_txta.append(s);
		console_txta.append("\n");
	}
}
