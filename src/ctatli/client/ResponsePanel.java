package ctatli.client;

import javax.swing.*;

public class ResponsePanel extends JPanel {
    ResponsePanel(JTextArea responseArea){
        JScrollPane pane = new JScrollPane(responseArea);
        add(pane);
    }
}
