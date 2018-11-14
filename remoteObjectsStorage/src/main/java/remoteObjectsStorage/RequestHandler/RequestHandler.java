package remoteObjectsStorage.RequestHandler;

import remoteObjectsStorage.Model.RequestModel;
import remoteObjectsStorage.Server.MTServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RequestHandler {

    private MTServer mtServer;
    private Socket socket;

    public RequestHandler(MTServer mtServer, Socket socket) {
        this.mtServer = mtServer;
        this.socket = socket;
    }

    public void handleRequest(RequestModel requestModel) {
        String method = requestModel.getRequestType();

        switch(method) {
            case "PUT":
                addObject(requestModel.getKey(), requestModel.getStoredObject());
                break;
            case "DELETE":
                removeObject(requestModel.getKey());
                break;
            case "GET":
                getObject(requestModel.getKey());
                break;
            default:
                throw new IllegalArgumentException("Wrong request method");
        }
    }

    private void addObject(String key, Object value) {
        try {
            ObjectOutputStream objectInputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream.writeObject(this.mtServer.addObject(key, value));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void removeObject(String key) {
        try {
            ObjectOutputStream objectInputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream.writeObject(this.mtServer.removeObject(key));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void getObject(String key) {
        Object object = this.mtServer.getObject(key);
        try {
            ObjectOutputStream objectInputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream.writeObject(object);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
