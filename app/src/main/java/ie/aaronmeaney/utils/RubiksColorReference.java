package ie.aaronmeaney.utils;

import ie.aaronmeaney.rubikscube.RubiksColor;

/**
 * Hack to pass enum as reference
 */
public class RubiksColorReference {

    public RubiksColorReference(RubiksColor col) {
        rubiksColorReference = col;
    }

    public RubiksColor rubiksColorReference;
}
