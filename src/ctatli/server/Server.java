package ctatli.server;

import com.google.gson.Gson;
import ctatli.client.Client;

import javax.net.ServerSocketFactory;
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class Server {


    ServerInformation serverInformation = new ServerInformation();

    private ServerSocket serverSocket;
    private ServerGui gui;
    private boolean isAlive;


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
            isAlive = true;
        } catch (IOException e) {
            e.printStackTrace();
        }


        while(isAlive)
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

    public void Shutdown(){
        WriteDictionary(serverInformation.dictionary, serverInformation.file);
        WriteOutputLog(gui.serverLogArea);
        isAlive = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void WriteDictionary(Dictionary dictionary, File file){
        BufferedWriter out = null;
        try {
            FileWriter fStream = new FileWriter(file, false);
            String output = dictionary.SerializeDictionary();
            out = new BufferedWriter(fStream);
            out.write(output);

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                serverInformation.file = null;
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void WriteOutputLog(JTextArea logArea){
        BufferedWriter out = null;
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = timeStamp + "_Log.txt";
        File file = new File(serverInformation.outputDir + "/" +fileName);
        try {
            FileWriter fStream = new FileWriter(file, false);
            out = new BufferedWriter(fStream);
            out.write(logArea.getText());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
