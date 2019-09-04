// Created by Christopher Tatli, ctatli@student.unimelb.edu.au 640427 for COMP90015 Project 1
package ctatli.server;

import javax.swing.*;

public class ServerGui {
    JFrame frame = new JFrame("Server");
    Server server;
    JTextArea serverLogArea;

    public ServerGui(Server server){
        this.server = server;
        this.InitGui();
        this.serverLogArea = new JTextArea(15,50);

    }

    private void InitGui(){
        ConnectionPanel connectionPanel = new ConnectionPanel(server.serverInformation, this);
        frame.getContentPane().add(connectionPanel);
        frame.setSize(800,400);
        frame.setVisible(true);

    }

    public void DrawContent(){
        LogPanel logPanel = new LogPanel(this.serverLogArea, this);

        frame.getContentPane().add(logPanel);
        frame.setVisible(true);
        server.StartServerWorker();
    }

    public void ShutdownServer(){
        frame.getContentPane().removeAll();
        ConnectionPanel connectionPanel = new ConnectionPanel(server.serverInformation, this);
        frame.getContentPane().add(connectionPanel);
        server.Shutdown();
        frame.setVisible(true);
    }
}
