package acsets4j;

import java.util.ArrayList;
import java.util.Arrays;

public class SchGraph {
   public static Schema schema = new Schema(
        new ArrayList<>(Arrays.asList(new Ob("V"), new Ob("E"))),
        new ArrayList<>(Arrays.asList(
            new Hom("src", "E", "V"),
            new Hom("tgt", "E", "V")
        )),
        new ArrayList<>(Arrays.asList()),
        new ArrayList<>(Arrays.asList())
   );
}
