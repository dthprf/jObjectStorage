package remoteObjectsStorage.Driver;

import remoteObjectsStorage.Exception.RemoteObjectStoreConnectionException;

public class ROSExceptionWrapper implements IConnectionExceptionHandler {
    @Override
    public void handle(Exception e, String errorMessage) {
        throw new RemoteObjectStoreConnectionException(errorMessage, e);
    }
}
