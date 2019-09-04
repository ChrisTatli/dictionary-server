package ctatli.server;

import javafx.util.Pair;

public class Message {
    public enum  MessageType {PING, ADD, DELETE, ERROR, SUCCESS, LOOKUP, DISCONNECT};

    public MessageType messageType;
    public String message;
    public Pair<String, String> addMessage;


    public Message(MessageType messageType, String message)
    {
        this.messageType = messageType;
        this.message = message;
    }

    public Message(MessageType messageType, Pair<String,String> message){
        this.messageType = messageType;
        this.addMessage = message;
    }
}
