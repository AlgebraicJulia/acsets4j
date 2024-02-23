package acsets4j.graphs;

import java.io.IOException;

import acsets4j.*;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

public class GraphDeserializer extends ACSetDeserializer<Graph> {
    public Graph deserialize(JsonParser jp, DeserializationContext ctxt) 
      throws IOException, JsonProcessingException {
        Graph g = new Graph();
        deserializeInto(g, jp, ctxt);
        return g;
    }
}