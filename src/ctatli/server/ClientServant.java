package ctatli.server;

import com.google.gson.Gson;
import ctatli.client.Client;

import javax.xml.crypto.Data;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ClientServant extends Thread {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket socket;
    private ServerGui gui;
    private ServerInformation info;
    private int clientId;



    public ClientServant(Socket socket, ServerGui gui, ServerInformation info){
        this.socket = socket;
        this.gui = gui;
        this.info = info;
        this.clientId = info.clientCount;
    }

    private void Connect(){
        try {
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.LogConnection();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void SendMessage(Message message){
        Gson gson = new Gson();
        try {
            out.writeUTF(gson.toJson(message));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Message ReceiveMessage(DataInputStream in){
        Gson gson = new Gson();
        try {
            Message message = gson.fromJson(in.readUTF(), Message.class);
            LogMessage(message);
            switch (message.messageType){
                case PING:
                    String payload = "Ping!";
                    SendMessage(new Message(Message.MessageType.PING, payload));

            }
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void LogConnection(){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logInfo = String.format("[%s] Client %d: Established new connection!\n", timeStamp, clientId);
        gui.serverLogArea.append(logInfo);
        gui.serverLogArea.setCaretPosition(gui.serverLogArea.getDocument().getLength());
    }

    private void LogMessage(Message message){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logInfo = String.format("[%s] Client %d: (%s) %s.\n", timeStamp, clientId, message.messageType.toString(), message.message);
        gui.serverLogArea.append(logInfo);
        gui.serverLogArea.setCaretPosition(gui.serverLogArea.getDocument().getLength());
    }

    @Override
    public void run(){
        this.Connect();
        while(true){
            this.ReceiveMessage(in);
        }
    }

}