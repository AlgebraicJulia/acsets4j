package acsets4j.error;

import com.fasterxml.jackson.core.JsonProcessingException;

public class MissingSubpartException extends JsonProcessingException {
    String subpart;

    public MissingSubpartException(String subpart) {
        super("subpart \"" + subpart  + "\" is not found within provided JSON. Expected [ ..., { ..., \"" + subpart + "\", ...}, ... ]");
        this.subpart = subpart;
    }
}
