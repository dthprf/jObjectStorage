package remoteObjectsStorage.Server;

import remoteObjectsStorage.Model.RequestModel;
import remoteObjectsStorage.RequestHandler.RequestHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class WorkerRunnable implements Runnable{

    private Socket clientSocket;
    private RequestHandler requestHandler;

    WorkerRunnable(Socket clientSocket, RequestHandler requestHandler) {
        this.clientSocket = clientSocket;
        this.requestHandler = requestHandler;
    }

    public void run() {
        try {
            InputStream input  = clientSocket.getInputStream();
            OutputStream output = clientSocket.getOutputStream();
            long time = System.currentTimeMillis();

            Object object = deserilize(clientSocket);
            RequestModel requestModel;

            if(object != null) {
                requestModel = (RequestModel) object;
                requestHandler.handleRequest(requestModel);
            }

            output.close();
            input.close();
            System.out.println("Request processed: " + time);
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println(e.toString() + "\n" + "Only Serializable objects allowed.");
        }
    }

    private Object deserilize(Socket clientSocket) {
        try{
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            return objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
