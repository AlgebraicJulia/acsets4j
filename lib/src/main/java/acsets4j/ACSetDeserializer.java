package acsets4j;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;

public abstract class ACSetDeserializer<T extends ACSet> extends StdDeserializer<T> {
    public ACSetDeserializer() { 
        this(null); 
    } 

    public ACSetDeserializer(Class<?> vc) { 
        super(vc); 
    }

    public void deserializeInto(T acs, JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        Schema schema = acs.schema();
        JsonNode node = jp.getCodec().readTree(jp);
        ObjectMapper om = new ObjectMapper();
        for (Ob object : schema.objects) {
            TreeNode elts = node.get(object.name());
            for (int i = 0; i < elts.size(); i++) {
                TreeNode subparts = elts.get(i);
                Part p = acs.addPartUnchecked(object.name());
                for (String f: schema.subpartsByDom(object.name())) {
                    if (schema.isHom(f)) {
                        int val = (Integer) ((IntNode) subparts.get(f)).numberValue();
                        acs.setSubpartUnchecked(p, f, new Part(schema.homCodom(f), val - 1));
                    } else {
                        acs.setSubpartUnchecked(p, f, om.treeToValue(subparts.get(f), schema.attrCodom(f)));
                    }
                }
            }
        }
    }
}
