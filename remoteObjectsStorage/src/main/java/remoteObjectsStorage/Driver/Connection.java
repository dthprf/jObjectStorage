package remoteObjectsStorage.Driver;

import remoteObjectsStorage.Model.RequestModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Connection {

    private final int port;
    private final String IP;

    public Connection(int port, String IP) {
        this.port = port;
        this.IP = IP;
    }

    public Object sendRequest(RequestModel transferObject) {
        Object serverResponse = null;

        try {
            Socket socket = new Socket(this.IP, this.port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(transferObject);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            serverResponse = objectInputStream.readObject();

            objectOutputStream.flush();
            objectOutputStream.close();
            objectInputStream.close();

        } catch (IOException | java.lang.ClassNotFoundException e) {
            e.printStackTrace();
        }

        return serverResponse;
    }
}
