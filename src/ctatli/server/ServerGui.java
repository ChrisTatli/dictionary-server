package ctatli.server;

import javax.swing.*;

public class ServerGui {
    JFrame frame = new JFrame("Server");
    Server server;
    JTextArea serverLogArea;

    public ServerGui(Server server){
        this.server = server;
        this.InitGui();
        this.serverLogArea = new JTextArea(15,60);

    }

    private void InitGui(){
        ConnectionPanel connectionPanel = new ConnectionPanel(server.serverInformation, this);
        frame.getContentPane().add(connectionPanel);
        frame.setSize(800,400);
        frame.setVisible(true);

    }

    public void DrawContent(){
        LogPanel logPanel = new LogPanel(this.serverLogArea);
        frame.getContentPane().add(logPanel);
        frame.setVisible(true);
        server.StartServerWorker();
    }
}
