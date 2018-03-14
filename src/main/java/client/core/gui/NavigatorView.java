/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.core.gui;

import javax.swing.*;

/**
 * @author NouakazaPc
 */
public class NavigatorView extends javax.swing.JPanel {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createRoom_btn;
    private javax.swing.JButton disconnect_btn;
    private javax.swing.JButton enterRoom_btn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> roomList_jl;

    /**
     * Creates new form NavigatorView
     */
    public NavigatorView() {
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

        createRoom_btn = new javax.swing.JButton();
        enterRoom_btn = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        roomList_jl = new javax.swing.JList<>();
        disconnect_btn = new javax.swing.JButton();

        createRoom_btn.setText("New room");
        createRoom_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createRoom_btnActionPerformed(evt);
            }
        });

        enterRoom_btn.setText("Enter room");

        roomList_jl.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {"Item 1", "Item 2", "Item 3", "Item 4", "Item 5"};

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
        jScrollPane1.setViewportView(roomList_jl);

        disconnect_btn.setText("Logout");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(24, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane1)
                                        .addComponent(enterRoom_btn, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                                        .addComponent(createRoom_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(disconnect_btn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(createRoom_btn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(enterRoom_btn)
                                .addGap(18, 18, 18)
                                .addComponent(disconnect_btn)
                                .addContainerGap(55, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void createRoom_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createRoom_btnActionPerformed
        String roomName = JOptionPane.showInputDialog("Name of the room ");
    }//GEN-LAST:event_createRoom_btnActionPerformed
    // End of variables declaration//GEN-END:variables
}