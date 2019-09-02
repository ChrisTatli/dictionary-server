package ctatli.server;

import javax.swing.*;

public class LogPanel extends JPanel {
    LogPanel(JTextArea logArea){
        JScrollPane pane = new JScrollPane(logArea);

        add(pane);
    }
}
