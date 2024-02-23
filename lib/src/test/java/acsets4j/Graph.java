package acsets4j;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = GraphSerializer.class)
@JsonDeserialize(using = GraphDeserializer.class)
public class Graph extends ACSet {
    public static Schema schema = SchGraph.schema;

    public Schema schema() {
        return schema;
    }
}
