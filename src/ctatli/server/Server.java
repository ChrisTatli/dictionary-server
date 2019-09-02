package ctatli.server;

import javax.net.ServerSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private  static int port = 3005;
    private static int counter = 0;

    public static void main(String[] args) {
	// write your code here
        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        try(ServerSocket server = factory.createServerSocket(port) )
        {
            System.out.println(("Waiting for client connection-"));

            while(true)
            {
                Socket client = server.accept();
                counter++;
                System.out.println("Client"+counter+": Applying for connection!");

                Thread t = new Thread(() -> serveClient(client));
                t.start();
            }
        }
        catch (IOException e )
        {
            e.printStackTrace();
        }
    }

    private static void serveClient(Socket client)
    {
        try(Socket clientSocket = client)
        {
            // Input stream
            DataInputStream input = new DataInputStream(clientSocket.getInputStream());
            // Output Stream
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

            System.out.println("CLIENT: "+input.readUTF());

            output.writeUTF("Server: Hi Client "+counter+" !!!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}