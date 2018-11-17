package remoteObjectsStorage.Driver;

public interface IConnectionExceptionHandler {
    void handle(Exception e, String errorMessage);
}
