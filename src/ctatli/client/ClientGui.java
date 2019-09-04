// Created by Christopher Tatli, ctatli@student.unimelb.edu.au 640427 for COMP90015 Project 1
package ctatli.client;

import javax.swing.*;
import java.awt.*;

public class ClientGui {
    JFrame frame = new JFrame("Client");
    Client client;
    JTextArea responseArea;


    public ClientGui(Client client) {

        this.client = client;
        this.InitGui();
        this.responseArea = new JTextArea(12,60);
    }

    private void InitGui() {
        ConnectionPanel connectionPanel = new ConnectionPanel(client, this);
        frame.getContentPane().add(connectionPanel);
        frame.setSize(800, 400);
        frame.setVisible(true);
    }

    public void Disconnect(){
        frame.getContentPane().removeAll();
        ConnectionPanel connectionPanel = new ConnectionPanel(client, this);
        frame.getContentPane().add(connectionPanel);
        frame.setVisible(true);

    }



    public void DrawContent(){
        ResponsePanel responsePanel = new ResponsePanel(this.responseArea);
        InputPanel inputPanel = new InputPanel(client, this.responseArea);

        ActionPanel actionPanel = new ActionPanel(inputPanel, client, this);

        JPanel topContainer = new JPanel();
        topContainer.add(responsePanel);
        topContainer.add(actionPanel);


        topContainer.setLayout(new BoxLayout(topContainer, BoxLayout.X_AXIS));

        JPanel botContainer = new JPanel();
        botContainer.add(inputPanel);

        JPanel container = new JPanel();
        container.add(topContainer);
        container.add(botContainer);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        frame.getContentPane().add(container);
        frame.setVisible(true);
    }
}