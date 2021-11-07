package ts7.cache.server.utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

// ClientHandler class
public class SlaveServerHandler extends Thread
{
  final DataInputStream dis;
  final DataOutputStream dos;
  final Socket s;
  final Map<String,Object> storeKeysAndObject;

  // Constructor
  public SlaveServerHandler(Socket s, DataInputStream dis, DataOutputStream dos, Map<String, Object> storeKeysAndObject)
  {
    this.s = s;
    this.dis = dis;
    this.dos = dos;
    this.storeKeysAndObject = storeKeysAndObject;
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
          System.out.println("AT the slave server RECEIVING THIS "+received+" FROM CLIENT");
          String[] splitString =received.split(Pattern.quote("$"));
          System.out.println("Key : "+splitString[1]);
          System.out.println("Value : "+splitString[2]);
          storeKeysAndObject.put(splitString[1],splitString[2]);
          dos.writeUTF("Done on the client for storing the key "+splitString[1]);
        }
        else if (received!=null && received.startsWith("GetKey")){
          System.out.println("AT the slave server RECEIVING THIS "+received+" FROM CLIENT");
          String[] splitString =received.split(Pattern.quote("$"));
          System.out.println("Slave Key : "+splitString[1]);
          String value = (String)storeKeysAndObject.get(splitString[1]);
          dos.writeUTF("Slave value "+value);
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
