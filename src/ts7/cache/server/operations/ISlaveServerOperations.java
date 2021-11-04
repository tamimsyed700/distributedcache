package ts7.cache.server.operations;

public interface ISlaveServerOperations {

  boolean storeObject(String key,Object objToStoreInMemoryDataStructure);

  void pingMasterServer(String server,int port,String message);
}
