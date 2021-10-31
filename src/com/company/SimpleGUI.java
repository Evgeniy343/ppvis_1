package com.company;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SimpleGUI extends JFrame {
    private JButton button1 = new JButton("enter text");
    private JButton button2 = new JButton("move right");
    private JButton button3 = new JButton("move left");
    private JTextField input = new JTextField();
    private JLabel label = new JLabel("Text:");
    private JTable table = new JTable(1, 2);

    public SimpleGUI() {
        super("Work 5");
        this.setBounds(100, 100, 350, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 0;

        constraints.gridx = 0;
        container.add(label, constraints);
        constraints.gridx = 1;
        container.add(input, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        container.add(table, constraints);
        constraints.gridwidth = 1;

        constraints.gridx = 0;
        constraints.gridy = 2;
        container.add(button1, constraints);
        constraints.gridx = 1;
        container.add(button2, constraints);
        constraints.gridx = 2;
        container.add(button3, constraints);


        button1.addActionListener(new Button_1_EventListener());
        button2.addActionListener(new Button_2_EventListener());
        button3.addActionListener(new Button_3_EventListener());
    }

    class Button_1_EventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            table.setValueAt("",0,0);
            table.setValueAt("",0,1);
            String value = input.getText();
            boolean choice = true;
            if (value.equals("")) {
                choice = false;
            }
            if (choice) {
                table.setValueAt(value, 0, 0);
            }
        }
    }

    class Button_2_EventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object value_left = table.getValueAt(0, 0);
            Object value_right = table.getValueAt(0, 1);
            boolean choice = true;
            if (IsEmptyField()) {
                choice = false;
            }
            if (choice) {
                if(value_right == null || value_right == "") {
                    table.setValueAt("", 0, 0);
                    table.setValueAt(value_left, 0, 1);
                }
                else{ error(); }
            }
        }
    }

    class Button_3_EventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object value_left = table.getValueAt(0, 0);
            Object value_right = table.getValueAt(0, 1);
            boolean choice = true;
            if (IsEmptyField()) {
                choice = false;
            }
            if (choice) {
                if (value_left == null || value_left == "") {
                    table.setValueAt("", 0, 1);
                    table.setValueAt(value_right, 0, 0);
                }
                else{ error(); }
            }
        }
    }

    boolean IsEmptyField() {
        Object cell_left = table.getValueAt(0, 0);
        Object cell_right = table.getValueAt(0, 1);
        if (cell_left == null && cell_right == null) {
            return true;
        } else {
            return false;
        }
    }

    public void error() {
            String message = "you are out of the table!";
            JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.ERROR_MESSAGE);
    }
}