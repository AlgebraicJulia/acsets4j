/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package acsets4j;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {
    @Test
    void graphOps() throws Exception {
        Graph g = new Graph();
        Part v1 = g.addPart("V");
        Part v2 = g.addPart("V");
        Part e = g.addPart("E");
        g.setSubpart(e, "src", v1);
        g.setSubpart(e, "tgt", v2);
        assertEquals(g.subpart(e, "src"), v1);
        assertEquals(g.subpart(e, "tgt"), v2);
    }
}
