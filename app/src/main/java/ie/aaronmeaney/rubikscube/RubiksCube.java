package ie.aaronmeaney.rubikscube;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Data representation of a Rubik's Cube
 */
public class RubiksCube implements Serializable {

    private List<RubiksFace> rubiksFaceList;

    private LinkedHashMap<RubiksColor, Integer> rubiksColorMap;

    public RubiksCube(LinkedHashMap rubiksColorMap) {
        rubiksFaceList = new ArrayList<>();
        this.rubiksColorMap = rubiksColorMap;

        rubiksFaceList.add(new RubiksFace(RubiksFace.RubiksFacePosition.FRONT, this));    // WHITE
        rubiksFaceList.add(new RubiksFace(RubiksFace.RubiksFacePosition.BACK, this));     // YELLOW
        rubiksFaceList.add(new RubiksFace(RubiksFace.RubiksFacePosition.LEFT, this));     // GREEN
        rubiksFaceList.add(new RubiksFace(RubiksFace.RubiksFacePosition.RIGHT, this));    // BLUE
        rubiksFaceList.add(new RubiksFace(RubiksFace.RubiksFacePosition.TOP, this));      // ORANGE
        rubiksFaceList.add(new RubiksFace(RubiksFace.RubiksFacePosition.BOTTOM, this));   // RED
    }

    /**
     * Gets the rubiksFaceList.
     * @return The rubiksFaceList.
     */
    public List<RubiksFace> getAllFaces() {
        return rubiksFaceList;
    }

    /**
     * Returns the RubiksFace at the position passed in.
     * @param position The position of the face.
     * @return The RubiksFace. Null if the face doesn't exist.
     */
    public RubiksFace getRubiksFace(RubiksFace.RubiksFacePosition position) {
        for (RubiksFace face : rubiksFaceList) {
            if (face.getFacePosition() == position) {
                return face;
            }
        }

        return null;
    }

    /**
     * Returns the RubiksFace matching the color passed in.
     * @param color The color of the face.
     * @return The RubiksFace. Null if the face doesn't exist.
     */
    public RubiksFace getRubiksFace(RubiksColor color) {
        for (RubiksFace face: rubiksFaceList) {
            if (face.getFaceColor() == color) {
                return face;
            }
        }

        return null;
    }

    /**
     * Returns the calibrated integer representation of the Rubiks Color
     * @param color The RubiksColor to convert
     * @return The integer representation of the RubiksColor
     */
    public Integer convertRubiksColorToInteger(RubiksColor color) {
        return rubiksColorMap.get(color);
    }
}
