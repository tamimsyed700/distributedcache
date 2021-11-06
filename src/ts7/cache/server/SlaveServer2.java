package ts7.cache.server;

import ts7.cache.server.utility.Utility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// Slave Server class
public class SlaveServer2
{
  public static final String MASTER_SERVER= "localhost";

  public static final int PORT =8002;
  public static final int MASTER_PORT =8000;

  public static final Map<String,Object> storeKeysAndObject = new HashMap<>();

  public static void main(String[] args) throws IOException {
//    for (int index=1;index<=10;index++) {
//      int port = PORT + index;
//      System.out.println("Port being computed is "+port);
//      new Thread(new Runnable() {
//        @Override
//        public void run() {
//          try {
           new SlaveServer(PORT,storeKeysAndObject);
//          } catch (IOException e) {
//            e.printStackTrace();
//          }
//        }
//      });
    }
  //}
}
