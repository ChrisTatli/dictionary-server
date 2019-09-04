package ctatli.client;

import javax.swing.*;

public class ResponsePanel extends JPanel {
    ResponsePanel(JTextArea responseArea){
        JScrollPane pane = new JScrollPane(responseArea);
        responseArea.setLineWrap(true);
        responseArea.setEditable(false);
        pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        add(pane);
    }
}
