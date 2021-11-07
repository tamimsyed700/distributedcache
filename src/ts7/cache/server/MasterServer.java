package ts7.cache.server;

import ts7.cache.server.operations.IMasterServerOperations;
import ts7.cache.server.utility.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Master Server class
public class MasterServer implements IMasterServerOperations {
  public static final int PORT =8000;

  public static final List<String> nodesAddressList = new ArrayList<>();

  public static final Map<String,Object> storeKeyValuesObject = new HashMap<>();

  public static void main(String[] args) throws IOException {
    new Utility().startCacheServer(1,PORT,storeKeyValuesObject);
  }

  @Override
  public void discoverSlaveNodes() {
  }

  @Override
  public void computeHashToStoreOnSlave(String key, Object value) {

  }

  @Override
  public Object getObject(String key) {
    return null;
  }
}
