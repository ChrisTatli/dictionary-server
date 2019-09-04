package ctatli.client;


import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class ConnectionPanel extends JPanel {


    ConnectionPanel(Client client, ClientGui clientGui){
        JTextField ipField = new JTextField("localhost",20);
        JTextField portField = new JTextField("3005",20);
        JButton connectButton = new JButton("Connect to Server");


        add(ipField);
        add(portField);
        add(connectButton);

        connectButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                client.options.ip  = ipField.getText();
                client.options.port = Integer.parseInt(portField.getText());
                if(client.connectToServer()){
                    clientGui.frame.getContentPane().removeAll();
                    clientGui.DrawContent();
                    client.StartClientWorker();
                } else {

                    JOptionPane.showMessageDialog(clientGui.frame, "Unable to establish connection with Server");
                }


            }
        });
    }

}


