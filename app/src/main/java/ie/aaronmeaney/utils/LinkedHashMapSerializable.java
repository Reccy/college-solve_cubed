package ie.aaronmeaney.utils;

import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * This is a HACK.
 * Used to pass LinkedHashMap between intents.
 */
public class LinkedHashMapSerializable implements Serializable {

    private LinkedHashMap ditto;

    public LinkedHashMapSerializable(LinkedHashMap linkedHashMap) {
        ditto = linkedHashMap;
    }

    public LinkedHashMap get() {
        return ditto;
    }
}
