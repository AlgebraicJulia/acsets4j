package acsets4j;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Schema {
    ArrayList<Ob> objects;
    ArrayList<Hom> homs;
    ArrayList<AttrType> attrtypes;
    ArrayList<Attr> attrs;

    HashMap<String, Hom> homLookup;
    HashMap<String, Attr> attrLookup;
    HashMap<String, ArrayList<String>> subpartLookup;
    HashMap<String, AttrType> attrTypeLookup;

    public Schema(ArrayList<Ob> objects, ArrayList<Hom> homs, ArrayList<AttrType> attrtypes, ArrayList<Attr> attrs) {
        this.objects = objects;
        this.homs = homs;
        this.attrtypes = attrtypes;
        this.attrs = attrs;

        homLookup = new HashMap<>();
        subpartLookup = new HashMap<>();
        attrLookup = new HashMap<>();
        attrTypeLookup = new HashMap<>();
        for (Ob ob : objects) {
            subpartLookup.put(ob.name(), new ArrayList<>());
        }
        for (Hom f : homs) {
            homLookup.put(f.name(), f);
            subpartLookup.get(f.dom()).add(f.name());
            
        }
        for (Attr f : attrs) {
            attrLookup.put(f.name(), f);
            subpartLookup.get(f.dom()).add(f.name());
        }
        for (AttrType t : attrtypes) {
            attrTypeLookup.put(t.name(), t);
        }
    }

    public List<String> subpartsByDom(String name) {
        return subpartLookup.get(name);
    }

    public AttrType attrTypeByName(String name) {
        return attrTypeLookup.get(name);
    }

    public boolean isHom(String f) {
        return homLookup.containsKey(f);
    }
    public boolean isAttr(String f) {
        return attrLookup.containsKey(f);
    }

    public String dom(String f) {
        if (isHom(f)) {
            return homLookup.get(f).dom();
        } else {
            return attrLookup.get(f).codom();
        }
    }

    public String homCodom(String f) {
        return homLookup.get(f).codom();
    }

    public Class<?> attrCodom(String f) {
        return attrTypeLookup.get(attrLookup.get(f).codom()).type();
    }

    public void checkObject(String object) throws Exception {
        if (objects.contains(new Ob(object))) {
            return;
        } else {
            throw new Exception("no object with name " + object + " found");
        }
    }

    public void checkSubpart(String ob, String f) throws Exception {
        if (dom(f) == ob) {
            return;
        } else {
            throw new Exception("no subpart with name " + f + " found for object " + ob);
        }
    }

    public void checkSubpart(String ob, String f, Object x) throws Exception {
        if (isHom(f)) {
            Part p = (Part) x;
            if (p.object() == homCodom(f)) {
                return;
            } else {
                throw new Exception("wrong codomain for hom " + f + ": " + p.object() + ", expected: " + homCodom(f));
            }
        } else if (isAttr(f)) {
            if (attrCodom(f).isInstance(x)) {
                return;
            } else {
                throw new Exception("wrong codomain for attribute " + f + ": " + x.getClass().getName() + ", expected: " + attrCodom(f).getName());
            }
        }
    }
}
