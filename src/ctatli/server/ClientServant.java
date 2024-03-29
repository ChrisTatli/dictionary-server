// Created by Christopher Tatli, ctatli@student.unimelb.edu.au 640427 for COMP90015 Project 1
package ctatli.server;

import com.google.gson.Gson;
import org.javatuples.*;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
                    SendMessage(new Message(Message.MessageType.PING, "Pong!"));
                    break;
                case LOOKUP:
                    SendMessage(QueryDictionary(message.message));
                    break;
                case ADD:
                    SendMessage(AddToDictionary(message.multiMessage));
                    break;
                case DELETE:
                    SendMessage(DeleteFromDictionary(message.message));
                    break;
                case DISCONNECT:
                	this.socket.close();
                    SendMessage(new Message(Message.MessageType.DISCONNECT, ""));
                    break;
            }
            return message;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Message QueryDictionary(String word){
        Message message;
        if(this.info.dictionary.ContainsWord(word)){
            ArrayList<String> successMessage =  this.info.dictionary.QueryWord(word);
            message = new Message(Message.MessageType.FOUND, new Pair<>(word, successMessage));
        }
        else{
            String errorMessage = String.format("%s not found in dictionary", word);
            message = new Message(Message.MessageType.ERROR, errorMessage);
        }
        return message;
    }

    private Message DeleteFromDictionary(String word){
        Message message;
        if(this.info.dictionary.ContainsWord(word)){
            this.info.dictionary.DeleteWord(word);
            String successMessage = String.format("%s deleted from dictionary", word);
            message = new Message(Message.MessageType.SUCCESS, successMessage );
        }
        else{
            String errorMessage = String.format("%s not found in dictionary");
            message = new Message(Message.MessageType.ERROR, errorMessage);

        }
        return message;
    }

    private Message AddToDictionary(Pair<String, ArrayList<String>> input){
        Message message;
        String word = input.getValue0();
        ArrayList<String> definition = input.getValue1();
        if(this.info.dictionary.ContainsWord(word)){
            String errorMessage = String.format("%s already contained in the dictionary", word);
            message = new Message(Message.MessageType.ERROR, errorMessage);
        }
        else{
            this.info.dictionary.AddWord(word, definition);
            String successMessage = String.format("%s added to the dictionary", word);
            message = new Message(Message.MessageType.SUCCESS, successMessage);
        }
        return message;
    }

    private void LogConnection(){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logInfo = String.format("[%s] Client %d: Established new connection!\n", timeStamp, clientId);
        gui.serverLogArea.append(logInfo);
        gui.serverLogArea.setCaretPosition(gui.serverLogArea.getDocument().getLength());
    }

    private void LogMessage(Message message){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logInfo = String.format("[%s] Client %d: (%s) %s.\n", timeStamp, clientId, message.messageType.toString(), message.message != null ? message.message : message.multiMessage);
        gui.serverLogArea.append(logInfo);
        gui.serverLogArea.setCaretPosition(gui.serverLogArea.getDocument().getLength());
    }

    private void DropClientConnection(){
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run(){
        this.Connect();
        while(info.isAlive){
            Message message = this.ReceiveMessage(in);
            if(message.messageType == Message.MessageType.DISCONNECT ){
                this.DropClientConnection();
                break;
            }
        }
        this.DropClientConnection();
    }



}