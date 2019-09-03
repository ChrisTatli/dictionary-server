package ctatli.server;

import ctatli.client.Client;

import javax.net.ServerSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Server {


    ServerInformation serverInformation = new ServerInformation();

    private ServerSocket serverSocket;
    private ServerGui gui;


    public static void main(String[] args) {
	// write your code here
        Server server = new Server();
        server.serverInformation.port = 3005;
        server.gui = new ServerGui(server);




    }

    public void StartServerWorker(){
        Thread t = new Thread(() -> StartServer());
        t.start();
    }

    private void StartServer(){

        serverInformation.dictionary = new Dictionary(this.serverInformation.file);
        try{
            serverSocket = new ServerSocket(serverInformation.port);
        } catch (IOException e) {
            e.printStackTrace();
        }


        while(true)
        {
            try{
                Socket client = serverSocket.accept();
                serverInformation.clientCount++;
                ClientServant clientServant = new ClientServant(client, gui, serverInformation);
                clientServant.start();
            } catch (IOException e) {
                e.printStackTrace();
            }




        }
    }

}
