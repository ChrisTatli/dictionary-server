package ctatli.client;

import ctatli.server.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ActionPanel extends JPanel {

    ActionPanel(InputPanel inputPanel, Client client, ClientGui clientGui){


        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton queryButton = new JButton("Query");
        JButton disconnectButton = new JButton("Disconnect");
        JButton pingButton = new JButton("Ping");

        JPanel container = new JPanel();
        container.add(addButton);
        container.add(deleteButton);
        container.add(queryButton);
        container.add(pingButton);
        container.add(disconnectButton);
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        add(container);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cardLayout = (CardLayout) inputPanel.getLayout();
                cardLayout.show(inputPanel, "add");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cardLayout = (CardLayout) inputPanel.getLayout();
                cardLayout.show(inputPanel, "delete");
            }
        });

        queryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cardLayout = (CardLayout) inputPanel.getLayout();
                cardLayout.show(inputPanel, "query");
            }
        });

        pingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Message message = new Message(Message.MessageType.PING, "Ping");
                client.SendMessage(message);
            }
        });

        disconnectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    client.DisconnectFromServer();
                    clientGui.Disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
