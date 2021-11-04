package ts7.cache.server.operations.impl;

import ts7.cache.server.SlaveServer;
import ts7.cache.server.operations.ISlaveServerOperations;
import ts7.cache.server.utility.Utility;

public class SlaveServerOperationsImpl implements ISlaveServerOperations {

  @Override
  public boolean storeObject(String key,Object objToStoreInMemoryDataStructure) {
    SlaveServer.storeKeysAndObject.put(key,objToStoreInMemoryDataStructure);
    return true;
  }

  @Override
  public void pingMasterServer(String server,int port,String message) {
    Utility.talktoAnotherServer(server,port,message);
  }
}
