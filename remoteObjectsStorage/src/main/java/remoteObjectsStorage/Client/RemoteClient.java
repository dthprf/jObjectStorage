package remoteObjectsStorage.Client;

import remoteObjectsStorage.Model.RequestModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RemoteClient {
    private String IP;
    private int socket;
    private final static String getMethod = "GET";
    private final static String putMethod = "PUT";
    private final static String deleteMethod = "DELETE";


    public RemoteClient(String IP, int socket) {
        this.socket = socket;
        this.IP = IP;
    }


    public void addObject(String key, Object object) throws IOException {
        RequestModel transferObject = new RequestModel(key, object, putMethod);

        Socket socket = new Socket(this.IP, this.socket);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(transferObject);

        objectOutputStream.flush();
        objectOutputStream.close();
    }


    public Object getObject(String key) throws IOException, ClassNotFoundException {
        RequestModel transferObject = new RequestModel(key, null, getMethod);

        Socket socket = new Socket(this.IP, this.socket);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(transferObject);

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Object requested = objectInputStream.readObject();
        objectInputStream.close();
        objectOutputStream.close();

        return requested;

    }
}
