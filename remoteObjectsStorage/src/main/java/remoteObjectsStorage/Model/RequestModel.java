package remoteObjectsStorage.Model;

import java.io.Serializable;

public class RequestModel implements Serializable {
    private String key;
    private Object storedObject;
    private String requestType;

    public RequestModel(String key, Object storedObject, String requestType) {
        this.key = key;
        this.storedObject = storedObject;
        this.requestType = requestType;
    }

    public String getKey() {
        return key;
    }

    public Object getStoredObject() {
        return storedObject;
    }

    public String getRequestType() {
        return requestType;
    }
}
