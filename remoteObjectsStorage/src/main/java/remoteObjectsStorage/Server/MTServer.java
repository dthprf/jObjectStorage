package remoteObjectsStorage.Server;

import remoteObjectsStorage.Constant.StatusCode;
import remoteObjectsStorage.Model.ResponseModel;
import remoteObjectsStorage.RequestHandler.RequestHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class MTServer implements Runnable{

    private int serverPort;
    private ServerSocket serverSocket = null;
    private boolean isStopped = false;
    private ConcurrentHashMap<String, Object> database;

    public MTServer(int port){
        this.serverPort = port;
        this.database = new ConcurrentHashMap<>();
    }

    public void run(){
        openServerSocket();
        while(! isStopped()){
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }

            RequestHandler requestHandler = new RequestHandler(this, clientSocket);

            new Thread(
                    new WorkerRunnable(
                            clientSocket, requestHandler)
            ).start();
        }
        System.out.println("Server Stopped.") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized ResponseModel addObject(String key, Object value) {
        if(!database.containsKey(key)) {
            this.database.put(key, value);
            return new ResponseModel(StatusCode.OK.getCode());
        }

        return new ResponseModel(StatusCode.DUPLICATED_KEY.getCode());
    }

    public synchronized ResponseModel getObject(String key) {

        Object object = this.database.get(key);

        if(object == null) {
            return new ResponseModel(StatusCode.KEY_NOT_FOUND.getCode());
        }

        return new ResponseModel(StatusCode.OK.getCode(), object);
    }

    public synchronized ResponseModel removeObject(String key) {
        if(this.database.containsKey(key)) {
            this.database.remove(key);
            return new ResponseModel(StatusCode.OK.getCode());
        }

        return new ResponseModel(StatusCode.KEY_NOT_FOUND.getCode());
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }

}
