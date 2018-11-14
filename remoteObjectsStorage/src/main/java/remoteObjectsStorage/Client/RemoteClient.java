package remoteObjectsStorage.Client;

import remoteObjectsStorage.Model.TransferObject;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketOption;
import java.net.UnknownHostException;

public class RemoteClient {
    private String IP;
    private int socket;

    public RemoteClient(String IP, int socket) {
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


    public Object getObject(String key) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(this.IP, this.socket);
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
