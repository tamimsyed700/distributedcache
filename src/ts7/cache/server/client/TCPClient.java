package ts7.cache.server.client;

import ts7.cache.server.utility.Utility;

import java.io.IOException;

// Client class
public class TCPClient {
  public static void main(String[] args) throws IOException {
    Utility.talktoAnotherServer("localhost",8000,"Store$Allah$ServantofAllah,Thameemuddin R");
  }
}
