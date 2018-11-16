package remoteObjectsStorage.Factory;

import remoteObjectsStorage.Constant.MethodType;
import remoteObjectsStorage.Model.RequestModel;

public class RequestModelFactory {

    public RequestModel generateRequestModel(String requestType, String key)
            throws NullPointerException, UnsupportedOperationException{

        RequestModel model = null;

        if (requestType == null | key == null) {
            throw new NullPointerException();
        }

        switch (requestType) {
            case MethodType.GET_METHOD:
                model = new RequestModel(key, null, requestType);
                break;

            case MethodType.DELETE_METHOD:
                model = new RequestModel(key, null, requestType);
                break;

            default:
                throw new UnsupportedOperationException(requestType + " is not supported request type");
        }

        return model;
    }

    public RequestModel generateRequestModel(String requestType, String key, Object objectData)
            throws NullPointerException, UnsupportedOperationException {

        RequestModel model = null;

        if (requestType == null | key == null) {
            throw new NullPointerException();

        } else if (requestType.equals(MethodType.PUT_METHOD)) {
            model = new RequestModel(key, objectData, requestType);

        } else {
            throw new UnsupportedOperationException();
        }

        return model;
    }
}
