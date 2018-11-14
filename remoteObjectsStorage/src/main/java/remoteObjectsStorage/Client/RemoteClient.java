package remoteObjectsStorage.Client;

import remoteObjectsStorage.Model.RequestModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class RemoteClient {
    private String IP;
    private int port;
    private final static String getMethod = "GET";
    private final static String putMethod = "PUT";
    private final static String deleteMethod = "DELETE";


    public RemoteClient(String IP, int port) {
        this.port = port;
        this.IP = IP;
    }


    public void addObject(String key, Object object) throws IOException {
        RequestModel transferObject = new RequestModel(key, object, putMethod);

        Socket socket = new Socket(this.IP, this.port);

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(transferObject);

        objectOutputStream.flush();
        objectOutputStream.close();
    }


    public Object getObject(String key) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(this.IP, this.port);
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(key);
        objectOutputStream.flush();

        Object requested = objectInputStream.readObject();

        objectInputStream.close();
        objectOutputStream.close();

        return requested;

    }
}
