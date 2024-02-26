package acsets4j.error;

import com.fasterxml.jackson.core.JsonProcessingException;

public class SubpartTypeException extends JsonProcessingException {
    public SubpartTypeException(String expectedType, String subpart, String json) {
        super("expected type " + expectedType + " for subpart " + subpart + ", got " + json + " instead");
    } 


    public SubpartTypeException(String expectedType, String subpart, String json, Exception e) {
        super("expected type " + expectedType + " for subpart " + subpart + ", got " + json + " instead.\nParsing threw Exception " + e.toString());
    } 
}
