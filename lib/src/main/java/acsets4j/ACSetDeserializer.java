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

import acsets4j.error.*;

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
            if (elts == null) {
                throw new MissingObjectException(object.name());
            }
            for (int i = 0; i < elts.size(); i++) {
                TreeNode subparts = elts.get(i);
                Part p = acs.addPartUnchecked(object.name());
                for (String f: schema.subpartsByDom(object.name())) {
                    TreeNode subpart = subparts.get(f);
                    if (subpart == null) {
                        throw new MissingSubpartException(f);
                    }
                    if (schema.isHom(f)) {
                        try {
                            int val = (Integer) ((IntNode) subpart).numberValue();
                            acs.setSubpartUnchecked(p, f, new Part(schema.homCodom(f), val - 1));
                        } catch (Exception e) {
                            throw new SubpartTypeException("int", f, subpart.toString());
                        }
                } else {
                        try {
                            Object val = om.treeToValue(subparts.get(f), schema.attrCodom(f));
                            acs.setSubpartUnchecked(p, f, val);
                        } catch (Exception e) {
                            throw new SubpartTypeException(schema.attrCodom(f).toString(), f, subpart.toString(), e);
                        }
                    }
                }
            }
        }
    }
}
