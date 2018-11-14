package remoteObjectsStorage.Server;

import remoteObjectsStorage.RequestHandler.RequestHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class MTServer implements Runnable{

    private int serverPort = 8080;
    private ServerSocket serverSocket;
    private boolean isStopped    = false;
//    private Thread runningThread= null;
    private ConcurrentHashMap<String, Object> database;

    public MTServer(int port){
        this.serverPort = port;
        this.database = new ConcurrentHashMap<>();
    }

    public void run(){
//        synchronized(this){
//            Thread runningThread = Thread.currentThread();
//        }
        openServerSocket();
        while(! isStopped()){
            Socket clientSocket = null;
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

    public synchronized boolean addObject(String key, Object value) {
        if(!database.containsKey(key)) {
            this.database.put(key, value);
            return true;
        }

        return false;
    }

    public synchronized Object getObject(String key) {

        Object object = this.database.get(key);

        if(object == null) {
            throw new IllegalArgumentException("There is no object connected to key");
        }
       return object;
    }

    public synchronized boolean removeObject(String key) {
        if(this.database.containsKey(key)) {
            this.database.remove(key);
            return true;
        }

        return false;
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
