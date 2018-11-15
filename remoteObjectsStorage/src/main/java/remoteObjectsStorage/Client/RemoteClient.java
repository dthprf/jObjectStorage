package remoteObjectsStorage.Client;

import remoteObjectsStorage.Driver.Connection;
import remoteObjectsStorage.Model.RequestModel;

public class RemoteClient {
    private final int port;
    private final String IP;

    private final static String getMethod = "GET";
    private final static String putMethod = "PUT";
    private final static String deleteMethod = "DELETE";

    public RemoteClient(String address, int port) {
        this.port = port;
        this.IP = address;
    }

    public boolean addObject(String key, Object object) {
        Connection connection = new Connection(port, IP);
        RequestModel transferObject = new RequestModel(key, object, putMethod);
        Object serverResponse = connection.sendRequest(transferObject);

        return (boolean) serverResponse;
    }


    public Object getObject(String key) {
        Connection connection = new Connection(port, IP);
        RequestModel transferObject = new RequestModel(key, null, getMethod);
        Object serverResponse = connection.sendRequest(transferObject);

        return serverResponse;

    }

    public boolean removeObject(String key) {
        Connection connection = new Connection(port, IP);
        RequestModel transferObject = new RequestModel(key, null, deleteMethod);
        Object serverResponse = connection.sendRequest(transferObject);

        return (boolean) serverResponse;
    }
}
