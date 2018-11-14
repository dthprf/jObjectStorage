package remoteObjectsStorage.Client;

import remoteObjectsStorage.Model.TransferObject;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private String IP;
    private int socket;

    public Client(String IP, int socket) {
        this.socket = socket;
        this.IP = IP;
    }

    public void addObject(String key, Object object) throws IOException {
        TransferObject transferObject = new TransferObject(key, object);
        Socket socket = new Socket(this.IP, this.socket);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(transferObject);
        objectOutputStream.flush();
        objectOutputStream.close();
    }
}
