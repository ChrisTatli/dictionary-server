package ctatli.server;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectionPanel extends JPanel {
    ConnectionPanel(ServerInformation serverInformation, ServerGui serverGui){
        JTextField portField = new JTextField("3005",20);
        JButton uploadButton = new JButton("Upload File");
        JButton startButton = new JButton("Start Server");


        add(portField);
        add(uploadButton);
        add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                serverInformation.port = Integer.parseInt(portField.getText());
                if(serverInformation.file != null ){
                    serverGui.frame.getContentPane().removeAll();
                    serverGui.DrawContent();
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
                if(returnValue == JFileChooser.APPROVE_OPTION){
                    serverInformation.file = jfc.getSelectedFile();
                    System.out.println(serverInformation.file.getAbsolutePath());
                }
            }
        });
    }
}
