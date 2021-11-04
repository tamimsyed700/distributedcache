package ts7.cache.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

// Server class
public class TCPServer
{
  public static final int PORT =8000;

  public static void main(String[] args) throws IOException
  {
    // server is listening on port 5056
    ServerSocket ss = new ServerSocket(PORT);

    // running infinite loop for getting
    // client request
    while (true)
    {
      Socket s = null;

      try
      {
        // socket object to receive incoming client requests
        s = ss.accept();

        System.out.println("A new client is connected : " + s);

        // obtaining input and out streams
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        System.out.println("Assigning new thread for this client");

        // create a new thread object
        Thread t = new ClientHandler(s, dis, dos);

        // Invoking the start() method
        t.start();

      }
      catch (Exception e){
        s.close();
        e.printStackTrace();
      }
    }
  }
}
