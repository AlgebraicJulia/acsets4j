package acsets4j;

import java.util.HashMap;
import java.util.ArrayList;

public abstract class ACSet {
    private HashMap<String, Integer> parts;
    private HashMap<String, ArrayList<Object>> columns;

    public static Schema schema;

    abstract Schema schema();
    
    public ACSet() {
        parts = new HashMap<>();
        for (Ob ob : schema().objects) {
            parts.put(ob.name(), 0);
        }
        columns = new HashMap<>();
        for (Hom f : schema().homs) {
            columns.put(f.name(), new ArrayList<>());
        }
        for (Attr f : schema().attrs) {
            columns.put(f.name(), new ArrayList<>());
        }
    }

    public Part addPart(String object) throws Exception {
        schema().checkObject(object);
        return addPartUnchecked(object);
    }

    public Part addPartUnchecked(String object) {
        int n = parts.get(object);
        parts.put(object, n + 1);
        for (String f : schema().subpartsByDom(object)) {
            columns.get(f).add(null);
        }
        return new Part(object, n);
    }

    public int nparts(String object) throws Exception {
        schema().checkObject(object);
        return parts.get(object);
    }

    public int npartsUnchecked(String object) {
        return parts.get(object);
    }

    public Object subpart(Part i, String f) throws Exception {
        schema().checkSubpart(i.object(), f);
        return columns.get(f).get(i.index());
    }

    public Object subpartUnchecked(Part i, String f) {
        return columns.get(f).get(i.index());
    }

    public void setSubpart(Part i, String f, Object x) throws Exception {
        schema().checkSubpart(i.object(), f, x);
        setSubpartUnchecked(i, f, x);
    }

    public void setSubpartUnchecked(Part i, String f, Object x) {
        columns.get(f).set(i.index(), x);
    }

    public boolean equals(Object otherObj) {
        if (!(this.getClass().isInstance(otherObj))) {
            return false;
        }

        ACSet other = (ACSet) otherObj;

        for (Ob ob : schema().objects) {
            int n = this.npartsUnchecked(ob.name());
            if (!(n == other.npartsUnchecked(ob.name()))) {
                return false;
            }
            for (String f : schema().subpartsByDom(ob.name())) {
                for (int i = 0; i < n; i++) {
                    Part p = new Part(ob.name(), i);
                    if (!(this.subpartUnchecked(p, f).equals(other.subpartUnchecked(p, f)))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
