package remoteObjectsStorage.Model;

import java.io.Serializable;

public class TransferObject implements Serializable {
    private String key;
    private Object storedObject;

    public TransferObject(String key, Object storedObject) {
        this.key = key;
        this.storedObject = storedObject;
    }
    
    public String getKey() {
        return key;
    }

    public Object getStoredObject() {
        return storedObject;
    }
}
