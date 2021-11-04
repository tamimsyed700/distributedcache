package ts7.cache.server.operations;

import java.net.InetAddress;

public interface ISlaveServerOperations {

  boolean storeObject(Object objToStoreInMemoryDataStructure);

  void pingMasterServer(InetAddress masterServerAddress);
}
