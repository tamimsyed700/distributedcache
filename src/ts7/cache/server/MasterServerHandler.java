package ts7.cache.server;

import ts7.cache.server.utility.Utility;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.Map;
import java.util.regex.Pattern;

// ClientHandler class
public class MasterServerHandler extends Thread
{
  final DataInputStream dis;
  final DataOutputStream dos;
  final Socket s;
  private Map<String, Object> storeKeysAndObject;

  // Constructor
  public MasterServerHandler(Socket s, DataInputStream dis, DataOutputStream dos, Map<String, Object> storeKeysAndObject)
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
//    while (true)
//    {
      try {
        // Ask user what he wants
        System.out.println("Master server handlers picked up .....");
        //dos.writeUTF("Master server handlers picked up .....");
        // receive the answer from client
        received = dis.readUTF();
        // creating Date object
        Date date = new Date();
        // write on output stream based on the
        // answer from the client
        if (received!=null && received.startsWith("DiscoverMe")){
          System.out.println("AT the master server RECEIVING THIS "+received+" FROM CLIENT");
          String extractNodeHostname=received.substring(received.indexOf("$")+1);
          MasterServer.nodesAddressList.add(extractNodeHostname);
          dos.writeUTF("IP address : "+extractNodeHostname);
        }
        else if (received!=null && received.startsWith("GetKey")){
          System.out.println("AT the master server RECEIVING THIS "+received+" FROM CLIENT");
          String[] splitString =received.split(Pattern.quote("$"));
          if (this.storeKeysAndObject.containsKey(splitString[1])) {
            String ipaddresstoConnect = (String)storeKeysAndObject.get(splitString[1]);
            String[] ip = ipaddresstoConnect.split(Pattern.quote(":"));
            int port = Integer.parseInt(ip[1]);
            new Thread(()->Utility.talktoAnotherServer(ip[0],port,received)).start();
          }
        }
        else if (received!=null && received.startsWith("Store")){
          System.out.println("AT the master server RECEIVING THIS "+received+" FROM CLIENT");
          String[] splitString =received.split(Pattern.quote("$"));
          long keyCRC = Utility.crc64Long(splitString[1]);
          System.out.println("keyCRC "+keyCRC);
          int nodeIndex = (int)(keyCRC % (MasterServer.nodesAddressList.stream().count()));
          nodeIndex=Math.abs(nodeIndex);
          System.out.println("nodeIndex "+nodeIndex);
          //Compute the instance to use to store the keys.
          String getNodeAddresstoStore = MasterServer.nodesAddressList.get(nodeIndex);
          System.out.println("getNodeAddresstoStore "+getNodeAddresstoStore);
          String address[] = getNodeAddresstoStore.split(Pattern.quote(":"));
          System.out.println("IP "+address[0]);
          int port = Integer.parseInt(address[1]);
          System.out.println("port "+port);
          dos.writeUTF("Done with the store at the master");
          new Thread(()->Utility.talktoAnotherServer(address[0],port,received)).start();
          System.out.println("Key "+splitString[1]+" added to the "+getNodeAddresstoStore);
          storeKeysAndObject.put(splitString[1],getNodeAddresstoStore);
//          String[] extractValues = received.split("$");
//          if(extractValues!=null && extractValues.length>2){
//            String key = extractValues[1];
//            String value=extractValues[2];
//          }
//          String extractNodeHostname=received.substring(received.indexOf("$")+1);
//          MasterServer.nodesAddressList.add(extractNodeHostname);
        }
        else if (received!=null && received.startsWith("ListSlaves")){
            System.out.println("AT the master server RECEIVING THIS "+received+" FROM CLIENT");
            StringBuffer stringBuffer = new StringBuffer();
            MasterServer.nodesAddressList.stream().forEach(e->stringBuffer.append("Slaves are : "+e+"\n"));
            dos.writeUTF(stringBuffer.toString());
        }
        else {
          dos.writeUTF("Master Invalid input");
        }
//        switch (received) {
//          case "Date" :
//            toreturn = fordate.format(date);
//            dos.writeUTF(toreturn);
//            break;
//          default:
//            dos.writeUTF("Master Invalid input");
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
