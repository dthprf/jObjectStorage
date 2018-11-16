package remoteObjectsStorage.Driver;

import remoteObjectsStorage.Model.RequestModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connection implements IConnection {

    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    @Override
    public void connect(String host, int port) {
        try {
            clientSocket = new Socket(host, port);

        } catch (UnknownHostException e) {
            System.out.println("Incorrect host address: " + host);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
