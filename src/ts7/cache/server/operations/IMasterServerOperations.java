package ts7.cache.server.operations;

public interface IMasterServerOperations {

  void discoverSlaveNodes();

  void computeHashToStoreOnSlave(String key,Object value);

  Object getObject(String key);
}
