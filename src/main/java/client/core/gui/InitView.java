/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.core.gui;

import client.core.handler.ServerHandler;

import javax.swing.*;


public class InitView extends JPanel {

    private JButton login_btn;
    private JTextField name_jtf;

    /**
     * Creates new form InitView
     */
    InitView(ServerHandler handler) {
        initComponents();
        login_btn.addActionListener(e -> {
            String value = name_jtf.getText();
            if (! value.isEmpty())
                handler.commandeName(value);
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        JLabel name_ljb = new JLabel();
        name_jtf = new JTextField();
        login_btn = new JButton();

        name_ljb.setText("Enter your name ...");

        login_btn.setText("Log in");

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(name_ljb)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(name_jtf, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(login_btn)))
                                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(name_ljb)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(name_jtf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(login_btn))
                                .addContainerGap(79, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void chosenNameIsBad() {
        JOptionPane.showMessageDialog(null, "name format is bad, or it already exists", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
