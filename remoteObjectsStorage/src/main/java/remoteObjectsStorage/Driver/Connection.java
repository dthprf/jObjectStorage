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

    @Override
    public boolean isConnected() {
        return clientSocket.isConnected() && !clientSocket.isClosed();
    }

    @Override
    public void stopConnection() {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (clientSocket != null) {
            try {
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object proceedRequest(RequestModel request) {
        Object serverResponse;
        sendRequest(request);
        serverResponse = readResponse();

        return serverResponse;
    }

    private void openInputStream() {
        try {
            inputStream = new ObjectInputStream(clientSocket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendRequest(Serializable requestObject) {
        openOutputStream();

        try {
            outputStream.writeObject(requestObject);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Object readResponse() {
        Object response = null;
        openInputStream();

        try {
            response = inputStream.readObject();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return response;
    }

    private void openOutputStream() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
