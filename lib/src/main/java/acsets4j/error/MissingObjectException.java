package acsets4j.error;

import com.fasterxml.jackson.core.JsonProcessingException;

public class MissingObjectException extends JsonProcessingException {
    String object;

    public MissingObjectException(String object) {
        super("object \"" + object  + "\" is not found within provided JSON. Expected { ..., \"" + object + "\": [...], ... }");
        this.object = object;
    } 
}
