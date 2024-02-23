package acsets4j;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public abstract class ACSetSerializer<T extends ACSet> extends StdSerializer<T> {
    public ACSetSerializer() {
        this(null);
    }
  
    public ACSetSerializer(Class<T> t) {
        super(t);
    }

    @Override
    public void serialize(T val, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        Schema schema = val.schema(); 
        jgen.writeStartObject();
        for (Ob object : schema.objects) {
            jgen.writeArrayFieldStart(object.name());
            int n = val.npartsUnchecked(object.name());
            for (int i = 0; i < n; i++) {
                Part p = new Part(object.name(), i);
                jgen.writeStartObject();
                for (String f: schema.subpartsByDom(object.name())) {
                    if (schema.isHom(f)) {
                        jgen.writeNumberField(f, ((Part) val.subpartUnchecked(p, f)).index() + 1);
                    } else {
                        jgen.writeObjectField(f, val.subpartUnchecked(p, f));
                    }
                }
                jgen.writeEndObject();
            }
            jgen.writeEndArray();
        }
        jgen.writeEndObject();
    }
}
