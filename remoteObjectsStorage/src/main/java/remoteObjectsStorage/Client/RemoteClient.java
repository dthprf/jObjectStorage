package remoteObjectsStorage.Client;

import remoteObjectsStorage.Constant.MethodType;
import remoteObjectsStorage.Driver.IConnection;
import remoteObjectsStorage.Driver.Connection;
import remoteObjectsStorage.Factory.RequestModelFactory;
import remoteObjectsStorage.Model.RequestModel;

import java.io.IOException;

public class RemoteClient {
    private String hostAddress;
    private int port;
    private Connection clientConnection;
    private RequestModelFactory requestFactory;

    public RemoteClient(String hostAddress, int port) {
        this.hostAddress = hostAddress;
        this.port = port;
        clientConnection = new Connection();
        requestFactory = new RequestModelFactory();
    }

    public boolean addObject(String key, Object object) {
        RequestModel request = requestFactory.generateRequestModel(MethodType.PUT_METHOD, key, object);
        Object serverResponse = communicateServer(request);

        return (boolean) serverResponse;
    }

    public Object getObject(String key) {
        RequestModel request = requestFactory.generateRequestModel(MethodType.GET_METHOD, key);
        Object serverResponse = communicateServer(request);

        return serverResponse;
    }

    public boolean removeObject(String key) {
        RequestModel request = requestFactory.generateRequestModel(MethodType.DELETE_METHOD, key);
        Object serverResponse = communicateServer(request);

        return (boolean) serverResponse;
    }

    private Object communicateServer(RequestModel request) {
        clientConnection.connect(this.hostAddress, this.port);
        Object serverResponse = clientConnection.proceedRequest(request);
        clientConnection.stopConnection();

        return serverResponse;
    }
}
