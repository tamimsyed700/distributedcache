package ts7.cache.server.utility;

import ts7.cache.server.MasterServerHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;

public class Utility {
  public static void talktoAnotherServer(String server, int port,String message) {
    try
    {
      Scanner scn = new Scanner(System.in);
      // getting localhost ip
      InetAddress ip = InetAddress.getByName(server);
      // establish the connection with server port 5056
      Socket s = new Socket(ip, port);
      // obtaining input and out streams
      DataInputStream dis = new DataInputStream(s.getInputStream());
      DataOutputStream dos = new DataOutputStream(s.getOutputStream());
      // the following loop performs the exchange of
      // information between client and client handler
//      while (true)
//      {
       // System.out.println(dis.readUTF());
        dos.writeUTF(message);
        // If client sends exit,close this connection
        // and then break from the while loop
        // printing date or time as requested by client
        String received = dis.readUTF();
        System.out.println(received);
//        if(tosend.equals("Exit"))
//        {
        System.out.println("Closing this connection : " + s);
        s.close();
        System.out.println("Connection closed");
      //  break;
        // }
     // }
      // closing resources
      scn.close();
      dis.close();
      dos.close();
    }catch(Exception e){
      e.printStackTrace();
    }
  }

  public void startCacheServer(int serverType, int port, Map<String,Object> storeKeysAndObject) throws IOException {
    // server is listening on port 5056
    ServerSocket ss = new ServerSocket(port);
    // running infinite loop for getting
    // client request
    while (true) {
      if (serverType==2) {
//        System.out.println("Slave server started....");
//        String sendMessage ="DiscoverMe$localhost:"+port;
//        System.out.println("Message sent : "+sendMessage);
//        Utility.talktoAnotherServer(SlaveServer.MASTER_SERVER,SlaveServer.MASTER_PORT,sendMessage);
      }
      else if (serverType==1)
        System.out.println("Master server started....");
      Socket s = null;
      try {
        // socket object to receive incoming client requests
        s = ss.accept();
        System.out.println("A new client is connected : " + s);
        // obtaining input and out streams
        DataInputStream dis = new DataInputStream(s.getInputStream());
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        System.out.println("Assigning new thread for this client");
        // create a new thread object
        if (serverType==1) {
          Thread t = new MasterServerHandler(s, dis, dos);
          t.start();
        }
        else if (serverType==2) {
          Thread t = new SlaveServerHandler(s, dis, dos,storeKeysAndObject);
          t.start();
        }
      }
      catch (Exception e){
        s.close();
        e.printStackTrace();
      }
    }
  }

  private static boolean init = false;
  private static long[] CRCTable = new long[256];
  private static final long POLY64REV = 0x95AC9329AC4BC9B5L;
  private static final long INITIALCRC = 0xFFFFFFFFFFFFFFFFL;

  public static final long crc64Long(String in) {
    if (in == null || in.length() == 0) {
      return 0;
    }
    // http://bioinf.cs.ucl.ac.uk/downloads/crc64/crc64.c
    long crc = INITIALCRC, part;
    if (!init) {
      for (int i = 0; i < 256; i++) {
        part = i;
        for (int j = 0; j < 8; j++) {
          int value = ((int) part & 1);
          if (value != 0) {
            part = (part >> 1) ^ POLY64REV;
          } else {
            part >>= 1;
          }
        }
        CRCTable[i] = part;
      }
      init = true;
    }
    int length = in.length();
    for (int k = 0; k < length; ++k) {
      char c = in.charAt(k);
      crc = CRCTable[(((int) crc) ^ c) & 0xff] ^ (crc >> 8);
    }
    return crc;
  }
}
