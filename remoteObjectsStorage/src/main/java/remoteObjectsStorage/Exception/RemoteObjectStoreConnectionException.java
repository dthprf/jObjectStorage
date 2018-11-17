package remoteObjectsStorage.Exception;

public class RemoteObjectStoreConnectionException extends Throwable {
    public RemoteObjectStoreConnectionException() {};
    public RemoteObjectStoreConnectionException(String message) {super(message);}
    public RemoteObjectStoreConnectionException(String message, Throwable t) {super(message, t);}
}
