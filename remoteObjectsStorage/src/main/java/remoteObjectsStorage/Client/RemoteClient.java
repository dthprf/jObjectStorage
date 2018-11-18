package remoteObjectsStorage.Client;

import remoteObjectsStorage.Constant.MethodType;
import remoteObjectsStorage.Constant.StatusCode;
import remoteObjectsStorage.Driver.Connection;
import remoteObjectsStorage.Exception.RemoteObjectStoreOperationException;
import remoteObjectsStorage.Factory.RequestModelFactory;
import remoteObjectsStorage.Model.RequestModel;
import remoteObjectsStorage.Model.ResponseModel;

import java.io.Serializable;

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

    public void addObject(String key, Serializable object) throws RemoteObjectStoreOperationException {
        RequestModel request = requestFactory.generateRequestModel(MethodType.PUT_METHOD, key, object);
        ResponseModel serverResponse = communicateServer(request);
        checkStatus(serverResponse);
    }

    public Object getObject(String key) throws RemoteObjectStoreOperationException {
        RequestModel request = requestFactory.generateRequestModel(MethodType.GET_METHOD, key);
        ResponseModel serverResponse = communicateServer(request);
        checkStatus(serverResponse);

        return serverResponse.getRequestedObject();
    }

    public void removeObject(String key) throws RemoteObjectStoreOperationException {
        RequestModel request = requestFactory.generateRequestModel(MethodType.DELETE_METHOD, key);
        ResponseModel serverResponse = communicateServer(request);
        checkStatus(serverResponse);
    }

    private ResponseModel communicateServer(RequestModel request) {
        clientConnection.connect(this.hostAddress, this.port);
        Object serverResponse = clientConnection.proceedRequest(request);
        clientConnection.stopConnection();

        return (ResponseModel) serverResponse;
    }

    private void checkStatus(ResponseModel serverResponse) throws RemoteObjectStoreOperationException {

        if (!serverResponse.getStatus().equals(StatusCode.OK.getCode())) {
            StatusCode response = StatusCode.valueOf(serverResponse.getStatus());
            throw new RemoteObjectStoreOperationException(response.getDescription());
        }
    }
}
