package ctatli.server;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionPanel extends JPanel {
    private String fileText = "File :";

    ConnectionPanel(ServerInformation serverInformation, ServerGui serverGui) {
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

        JPanel portContainer = new JPanel();
        JLabel portLabel = new JLabel("Port: ");
        JTextField portField = new JTextField("3005", 20);
        portContainer.add(portLabel);
        portContainer.add(portField);


        JButton uploadButton = new JButton("Upload File");
        JLabel fileName = new JLabel("Please supply an initial file for the dictionary seed data.");


        JButton startButton = new JButton("Start Server");

        JLabel errorText = new JLabel();
        container.add(portContainer);
        container.add(uploadButton);
        container.add(startButton);
        container.add(fileName);
        container.add(errorText);
        add(container);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try{
                    int portNumber = Integer.parseInt((portField.getText()));
                    if (serverInformation.IsPortValid(portNumber)) {
                        serverInformation.port = portNumber;
                        errorText.setText("");
                    } else {
                        errorText.setText("Please use a port number between 1024 and 49151");
                    }

                    if (serverInformation.file != null && serverInformation.IsPortValid(portNumber)) {
                        serverGui.frame.getContentPane().removeAll();
                        serverGui.DrawContent();
                    }
                } catch (NumberFormatException e){
                    errorText.setText("Please use a port number between 1024 and 49151");
                }



            }
        });

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                jfc.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON files", "json");
                jfc.addChoosableFileFilter(filter);
                int returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    serverInformation.file = jfc.getSelectedFile();
                    serverInformation.outputDir = serverInformation.file.getParent();

                    fileName.setText(fileText + serverInformation.file.getAbsolutePath());
                }
            }
        });
    }
}
