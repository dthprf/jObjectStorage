package remoteObjectsStorage.Exception;

public class RemoteObjectStoreOperationException extends Throwable {
    public RemoteObjectStoreOperationException() {};
    public RemoteObjectStoreOperationException(String message) {super(message);}
    public RemoteObjectStoreOperationException(String message, Throwable t) {super(t);}
}
