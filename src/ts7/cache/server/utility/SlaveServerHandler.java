package ts7.cache.server.utility;

import ts7.cache.server.MasterServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

// ClientHandler class
public class SlaveServerHandler extends Thread
{
  final DataInputStream dis;
  final DataOutputStream dos;
  final Socket s;

  // Constructor
  public SlaveServerHandler(Socket s, DataInputStream dis, DataOutputStream dos)
  {
    this.s = s;
    this.dis = dis;
    this.dos = dos;
  }

  @Override
  public void run()
  {
    String received;
    String toreturn;
//    while (true)
//    {
      try {
        // Ask user what he wants
        System.out.println("Slave server handlers picked up .....");
        //dos.writeUTF("Slave server handlers picked up .....");
        // receive the answer from client
        received = dis.readUTF();
        // creating Date object
        Date date = new Date();
        // write on output stream based on the
        // answer from the client
        if (received!=null && received.startsWith("Store")){
          System.out.println("AT the master server RECEIVING THIS "+received+" FROM CLIENT");
          String extractNodeHostname=received.substring(received.indexOf("$")+1);
          MasterServer.nodesAddressList.add(extractNodeHostname);
          dos.writeUTF("IP address : "+extractNodeHostname);
        }
        else {
          dos.writeUTF("Master Invalid input");
        }
//        switch (received) {
//          case "Time" :
//            toreturn = fortime.format(date);
//            dos.writeUTF(toreturn);
//            break;
//          default:
//            dos.writeUTF("Slave Invalid input");
//            break;
//        }
//        if(received.equals("Exit"))
//        {
          System.out.println("Client " + this.s + " sends exit...");
          System.out.println("Closing this connection.");
          this.s.close();
          System.out.println("Connection closed");
          //break;
     //   }
      } catch (IOException e) {
        e.printStackTrace();
      }
   // }

    try
    {
      // closing resources
      this.dis.close();
      this.dos.close();
    }catch(IOException e){
      e.printStackTrace();
    }
  }
}
