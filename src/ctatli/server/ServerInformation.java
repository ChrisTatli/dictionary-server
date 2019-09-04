package ctatli.server;

import java.io.File;

public class ServerInformation {
    public int port;
    public int clientCount = 0;
    public File file;
    public Dictionary dictionary;
    public String outputDir;

    public boolean IsPortValid(int port){
        return (port > 1024) && (port < 4951);
    }
}
