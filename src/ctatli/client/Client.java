// Created by Christopher Tatli, ctatli@student.unimelb.edu.au 640427 for COMP90015 Project 1
package ctatli.client;

import com.google.gson.Gson;
import ctatli.server.Message;
import org.w3c.dom.ls.LSException;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Client {

    public ConnectionOptions options = new ConnectionOptions();
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ClientGui gui;


    public Client(){

    }

    public static void main(String[] args)
    {
        Client client = new Client();
        client.gui = new ClientGui(client);
    }

    public boolean connectToServer(){
        try{
            socket = new Socket(options.ip, options.port);
            this.in = new DataInputStream((socket.getInputStream()));
            this.out = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  false;
    }

    public void StartClientWorker(){
        Thread t = new Thread(() -> ListenToServer());
        t.start();
    }

    private void ListenToServer(){

        while(true){
            Message message = this.ReceiveMessage(in);
            if(message == null) break;
            if(message.messageType == Message.MessageType.DISCONNECT){
                try {
                    this.DisconnectFromServer();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void DisconnectFromServer() throws IOException {
        this.gui.responseArea.setText("");
        this.socket.close();
    }

    public void SendMessage(Message message){
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
            if(message.messageType == Message.MessageType.FOUND){
                int count = 1;
                this.gui.responseArea.setText(message.multiMessage.getKey() + "\n");
                for (String definition:
                     message.multiMessage.getValue()) {
                    String output = String.format("%d. %s\n", count, definition);
                    this.gui.responseArea.append(output);
                    count++;
                }
            } else {
                this.gui.responseArea.setText(message.message);
            }
            
            return message;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(gui.frame, "Connection closed");
            try {
                gui.Disconnect();
                DisconnectFromServer();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }


}
