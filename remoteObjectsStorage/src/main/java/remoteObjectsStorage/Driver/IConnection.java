package remoteObjectsStorage.Driver;

import remoteObjectsStorage.Model.RequestModel;

import java.io.IOException;

public interface IConnection {
    void connect(String host, int port);
    void stopConnection() throws IOException;
    Object proceedRequest(RequestModel transferObject) throws IOException, ClassNotFoundException;
    boolean isConnected();
    void setOutStreamTimeout(int milis);
}
