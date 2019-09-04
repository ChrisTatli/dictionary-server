// Created by Christopher Tatli, ctatli@student.unimelb.edu.au 640427 for COMP90015 Project 1
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
