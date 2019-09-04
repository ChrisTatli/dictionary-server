// Created by Christopher Tatli, ctatli@student.unimelb.edu.au 640427 for COMP90015 Project 1
package ctatli.server;

import java.io.File;

public class ServerInformation {
    public int port;
    public int clientCount = 0;
    public File file;
    public Dictionary dictionary;
    public String outputDir;
    public boolean isAlive;

    public boolean IsPortValid(int port){
        return (port > 1024) && (port < 49151);
    }
}
