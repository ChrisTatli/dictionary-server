package ctatli.server;

import javax.net.ServerSocketFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {


    ServerInformation serverInformation = new ServerInformation();

    private Socket socket;
    private ServerGui gui;

    public static void main(String[] args) {
	// write your code here
        Server server = new Server();
        server.serverInformation.port = 3005;
        ServerGui gui = new ServerGui(server);


        server.InitServer(gui);



    }

    private void InitServer(ServerGui gui){
        ServerSocketFactory factory = ServerSocketFactory.getDefault();
        Dictionary dictionary = new Dictionary();


        try(ServerSocket server = factory.createServerSocket(this.serverInformation.port) )
        {
            System.out.println(("Waiting for client connection-"));

            while(true)
            {
                Socket client = server.accept();
                serverInformation.clientCount++;

                gui.serverLogArea.append("Client"+serverInformation.clientCount+": Applying for connection!\n");
                gui.serverLogArea.setCaretPosition(gui.serverLogArea.getDocument().getLength());

                System.out.println("Client"+serverInformation.clientCount+": Applying for connection!");

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

            //output.writeUTF("Server: Hi Client "+counter+" !!!");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
