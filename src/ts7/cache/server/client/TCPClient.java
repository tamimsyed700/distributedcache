package ts7.cache.server.client;

import ts7.cache.server.utility.Utility;

import java.io.IOException;

// Client class
public class TCPClient {
  public static void main(String[] args) throws IOException, InterruptedException {
    Utility.talktoAnotherServer("localhost",8000,"Store$Allah$ServantofAllah,Thameemuddin R");
    Thread.sleep(2000);
    Utility.talktoAnotherServer("localhost",8000,"GetKey$Allah");
    //TODO: connection tcp socket is getting closed at the master server thread itself so need to change to send the socket stream to this TCP Client program.
    //TODO: Utility.talktoAnotherServer return should be changed from void to DataOutputStream.
   }
}
