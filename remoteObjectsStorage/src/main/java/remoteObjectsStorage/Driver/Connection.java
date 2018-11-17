package remoteObjectsStorage.Driver;

import remoteObjectsStorage.Model.RequestModel;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Connection implements IConnection {

    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private IConnectionExceptionHandler exceptionHandler;

    public Connection() {
        this.exceptionHandler = new RemoteObjectStoreWrapper();
    }

    @Override
    public void connect(String host, int port) {
        try {
            clientSocket = new Socket(host, port);
            setOutStreamTimeout(1000);

        } catch (UnknownHostException e) {
            this.exceptionHandler.handle(e, "Incorrect host address: " + host);

        } catch (IOException e) {
            this.exceptionHandler.handle(e,"Error while creating socket.");
        }
    }

    @Override
    public void setOutStreamTimeout(int milis) {
        try {
            clientSocket.setSoTimeout(milis);

        } catch (SocketException e) {
            this.exceptionHandler.handle(e, "Client socket unavailable.");
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
                this.exceptionHandler.handle(e, "Cannot close output stream.");
            }
        }

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                this.exceptionHandler.handle(e, "Cannot close input stream.");
            }
        }

        if (clientSocket != null) {
            try {
                clientSocket.close();

            } catch (IOException e) {
                this.exceptionHandler.handle(e, "Cannot close socket.");
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
            this.exceptionHandler.handle(e, "Cannot open Input stream.");
        }
    }

    private void sendRequest(Serializable requestObject) {
        openOutputStream();

        try {
            outputStream.writeObject(requestObject);
            outputStream.flush();

        } catch (IOException e) {
            this.exceptionHandler.handle(e, "Cannot send request to server.");
        }
    }

    private Object readResponse() {
        Object response = null;
        openInputStream();

        try {
            response = inputStream.readObject();

        } catch (IOException e) {
            this.exceptionHandler.handle(e, "Server is not responding.");

        } catch (ClassNotFoundException e) {
            this.exceptionHandler.handle(e, "Cannot read server respond, object damaged.");
        }

        return response;
    }

    private void openOutputStream() {
        try {
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());

        } catch (IOException e) {
            this.exceptionHandler.handle(e, "Cannot open input stream.");;
        }
    }
}
