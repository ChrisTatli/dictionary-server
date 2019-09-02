package ctatli.client;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {

    public ConnectionOptions options = new ConnectionOptions();
    private Socket socket;

    public Client(){

    }

    public static void main(String[] args)
    {
        Client client = new Client();
        ClientGui clientGui = new ClientGui(client);
    }

    public boolean connectToServer(){
        try{
            socket = new Socket(options.ip, options.port);
            DataInputStream input = new DataInputStream((socket.getInputStream()));

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            String sendData = "I want to connect";

            output.writeUTF(sendData);
            System.out.println(("Data sent to Server -->" + sendData));
            output.flush();
            while(true && input.available() > 0) {
                String message = input.readUTF();
                System.out.println(message);
            }
            return true;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }return  false;
    }

    public void DisconnectFromServer() throws IOException {
        this.socket.close();
    }


}
