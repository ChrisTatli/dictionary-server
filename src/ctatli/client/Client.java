package ctatli.client;

import com.google.gson.Gson;
import ctatli.server.Message;
import org.w3c.dom.ls.LSException;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
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
            this.ReceiveMessage(in);

        }
    }

    public void DisconnectFromServer() throws IOException {
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
            this.gui.responseArea.append(message.message);
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
