package com.company;

import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class DifficultGUI extends JFrame {
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JTextField input1;
    private JLabel label1;
    private JTextField input2;
    private JLabel label2;
    private JTable table;
    private boolean stop;
    private int number_of_words;
    private String direction;

    public DifficultGUI() {
        super("Work 18");
        this.button1 = new JButton("Create table");
        this.button2 = new JButton("Move");
        this.button3 = new JButton("Stop");
        this.input1 = new JTextField("", 3);
        this.label1 = new JLabel("Rows");
        this.input2 = new JTextField("", 3);
        this.label2 = new JLabel("Columns");
        this.setBounds(100, 100, 350, 150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.stop = true;
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;


        constraints.gridy = 0;
        constraints.gridx = 0;
        container.add(label1, constraints);
        constraints.gridx = 1;
        container.add(label2, constraints);

        constraints.gridy = 1;
        constraints.gridx = 0;
        container.add(input1, constraints);
        constraints.gridx = 1;
        container.add(input2, constraints);

        constraints.gridy = 2;
        constraints.gridx = 0;
        container.add(button1, constraints);
        constraints.gridx = 1;
        container.add(button2, constraints);
        constraints.gridx = 2;
        container.add(button3, constraints);


        button1.addActionListener(new DifficultGUI.Button_1_EventListener());

        button2.addActionListener(new DifficultGUI.Button_2_EventListener());

    }

    class Button_1_EventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String text = "Snake";
            int rows = Integer.parseInt(input1.getText());
            int columns = Integer.parseInt(input2.getText());
            JFrame panel = new JFrame("Table");
            table = new JTable(rows, columns);
            panel.setBounds(100, 100, 250, 100);
            panel.add(table);
            panel.setVisible(true);
            table.setValueAt(text, new Random().nextInt(rows), new Random().nextInt(columns));
        }
    }


    class Button_2_EventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (stop && number_of_words != 0) {
            } else {
                Snake snake = new Snake();
                number_of_words++;
            }
        }
    }


    public static class TableSize {
        private int row;
        private int column;

        TableSize() {
            this.column = 0;
            this.row = 0;
        }
    }

    public static class Directions {
        private boolean up;
        private boolean down;
        private boolean hand;
    }


    public class Snake implements Runnable {
        Thread thread;

        Snake() {
            this.thread = new Thread(this);
            this.thread.start();
        }

        @Override
        public void run() {

            button3.addActionListener((event)-> {
                stop = false;
            });

            try {
                String text = "Snake";
                stop = true;
                Directions directions = new Directions();
                TableSize size = new TableSize();
                getLocation(size, text);
                determination_of_direction(directions);
                while (size.column != table.getColumnCount()) {
                    if (directions.hand) {
                        if ((size.row == table.getRowCount() - 1 || size.row == 0)
                                && size.column == table.getColumnCount() - 1) {
                            startLocation(size, text, directions);
                            Thread.sleep(500);
                        } else {
                            moveHand(size, text, directions);
                            Thread.sleep(500);
                        }
                        direction = "";
                    }
                    if (size.row == 0 && directions.up && size.column != table.getColumnCount()) {
                        if (size.column == table.getColumnCount() - 1
                                && (size.row == table.getRowCount() - 1 || size.row == 0)) {
                            startLocation(size, text, directions);
                            Thread.sleep(500);
                            continue;
                        }
                        moveHand(size, text, directions);
                        Thread.sleep(500);
                    }
                    while (size.row != table.getRowCount() - 1) {
                        if (direction == "up") {
                            break;
                        }
                        moveDown(size, text, directions);
                        Thread.sleep(500);
                        direction = "";
                    }
                    if (size.row == table.getRowCount() - 1
                            && directions.down && size.column != table.getColumnCount()) {
                        if (size.column == table.getColumnCount() - 1
                                && (size.row == table.getRowCount() - 1 || size.row == 0)) {
                            startLocation(size, text, directions);
                            Thread.sleep(500);
                            continue;
                        }
                        moveHand(size, text, directions);
                        Thread.sleep(500);
                    }
                    while (size.row != 0) {
                        if (direction == "down") {
                            break;
                        }
                        moveUp(size, text, directions);
                        Thread.sleep(500);
                        direction = "";
                    }
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }


        public void getLocation(TableSize size, String text) {
            Object pivot = "";
            for (int i = 0; i < table.getColumnCount(); i++) {
                for (int j = 0; j < table.getRowCount(); j++) {
                    pivot = table.getValueAt(j, i);
                    if (text == (String) pivot) {
                        size.row = j;
                        size.column = i;
                        break;
                    }
                }
            }
        }

        public void startLocation(TableSize size, String text, Directions directions) {
            table.setValueAt("", size.row, size.column);
            size.row = 0;
            size.column = 0;
            table.setValueAt(text, size.row, size.column);
            if (!stop) {
                thread.interrupt();
            }
            directions.up = false;
            directions.down = false;
        }


        public void moveDown(TableSize size, String text, Directions directions) {
            table.setValueAt("", size.row, size.column);
            size.row++;
            table.setValueAt(text, size.row, size.column);
            if (!stop) {
                if (size.row == table.getRowCount() - 1) {
                    direction = "hand";
                } else {
                    direction = "down";
                }
                thread.interrupt();
            }
            directions.down = true;
        }


        public void moveUp(TableSize size, String text, Directions directions) {
            table.setValueAt("", size.row, size.column);
            size.row--;
            table.setValueAt(text, size.row, size.column);
            if (!stop) {
                if (size.row == 0) {
                    direction = "hand";
                } else {
                    direction = "up";
                }
                thread.interrupt();
            }
            directions.up = true;
        }

        public void moveHand(TableSize size, String text, Directions directions) {
            table.setValueAt("", size.row, size.column);
            size.column++;
            table.setValueAt(text, size.row, size.column);
            if (!stop) {
                thread.interrupt();
            }
            directions.hand = false;
        }

        public void determination_of_direction(Directions directions) {
            if (direction == "up") {
                directions.up = true;
                directions.down = false;
            }
            if (direction == "down") {
                directions.down = true;
                directions.up = false;
            }
            if (direction == "hand") {
                directions.hand = true;
            }
        }

    }
}