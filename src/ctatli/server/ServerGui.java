package ctatli.server;

import javax.swing.*;

public class ServerGui {
    JFrame frame = new JFrame("Server");
    Server server;
    JTextArea serverLogArea;

    public ServerGui(Server server){
        this.server = server;
        this.serverLogArea = new JTextArea(15,60);
        this.InitGui();

    }

    private void InitGui(){
        frame.setSize(800,400);

        frame.setVisible(true);
        DrawContent();
    }

    public void DrawContent(){
        LogPanel logPanel = new LogPanel(this.serverLogArea);
        frame.getContentPane().add(logPanel);
        frame.setVisible(true);

    }
}
