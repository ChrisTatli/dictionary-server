package ctatli.client;

import javax.xml.crypto.Data;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import ctatli.server.Message;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;


public class Client {

    private  static String ip = "localhost";
    private static int port = 3005;

    public Client()
    {
        JFrame frame = new JFrame("Client");
        frame.setSize(800,400);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        Client client = new Client();
        Message payload = new Message(Message.MessageType.PING, "Ping");
        try(Socket socket = new Socket(ip,port);){
            DataInputStream input = new DataInputStream((socket.getInputStream()));

            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            String sendData = "I want to connect";

            output.writeUTF(sendData);
            System.out.println(("Data sent to Server -->" + sendData));
            output.flush();
            while(true && input.available() > 0)
            {
                String message = input.readUTF();
                System.out.println(message);
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
