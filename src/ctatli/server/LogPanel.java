package ctatli.server;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LogPanel extends JPanel {
    LogPanel(JTextArea logArea, ServerGui gui){
        JScrollPane pane = new JScrollPane(logArea);
        JButton closeServer = new JButton("Close Server");
        add(pane);
        add(closeServer);

        closeServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                gui.ShutdownServer();
            }
        });
    }
}
