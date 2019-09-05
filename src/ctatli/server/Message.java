// Created by Christopher Tatli, ctatli@student.unimelb.edu.au 640427 for COMP90015 Project 1
package ctatli.server;

import org.javatuples.*;

import java.util.ArrayList;

public class Message {
    public enum  MessageType {PING, ADD, DELETE, ERROR, SUCCESS, LOOKUP, DISCONNECT, FOUND};

    public MessageType messageType;
    public String message;
    public Pair<String, ArrayList<String>> multiMessage;


    public Message(MessageType messageType, String message)
    {
        this.messageType = messageType;
        this.message = message;
    }

    public Message(MessageType messageType, Pair<String,ArrayList<String>> message){
        this.messageType = messageType;
        this.multiMessage = message;
    }
}
