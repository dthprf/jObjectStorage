package remoteObjectsStorage.Constant;

public enum StatusCode {
    OK("OK", "Request completed"),
    KEY_NOT_FOUND("KEY_NOT_FOUND", "No object under requested key"),
    DUPLICATED_KEY("DUPLICATED_KEY", "Could not add object, key already exists");

    private final String code;
    private final String description;

    StatusCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return code + ": " + description;
    }

}
