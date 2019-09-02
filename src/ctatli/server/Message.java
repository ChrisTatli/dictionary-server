package ctatli.server;

public class Message {
    public enum  MessageType {PING, ADD, DELETE, ERROR, LOOKUP };

    public MessageType messageType;
    public String message;

    public Message(MessageType messageType, String message)
    {
        this.messageType = messageType;
        this.message = message;

    }
}