package remoteObjectsStorage.Model;

import java.io.Serializable;

public class ResponseModel implements Serializable {
    private String status;
    private Object requestedObject;

    public ResponseModel(String code, Object requestedObject) {
        this.status = code;
        this.requestedObject = requestedObject;
    }

    public ResponseModel(String code) {
        this.status = code;
        this.requestedObject = null;
    }

    public Object getRequestedObject() {
        return requestedObject;
    }

    public String getStatus() {
        return status;
    }
}
