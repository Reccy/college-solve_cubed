package ie.aaronmeaney.rubikscube;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data representation of a Rubik's Cube
 */
public class RubiksCube implements Serializable {

    private List<RubiksFace> rubiksFaceList;

    public RubiksCube() {
        rubiksFaceList = new ArrayList<>();

        rubiksFaceList.add(new RubiksFace(RubiksFace.RubiksFacePosition.FRONT));    // WHITE
        rubiksFaceList.add(new RubiksFace(RubiksFace.RubiksFacePosition.BACK));     // YELLOW
        rubiksFaceList.add(new RubiksFace(RubiksFace.RubiksFacePosition.LEFT));     // GREEN
        rubiksFaceList.add(new RubiksFace(RubiksFace.RubiksFacePosition.RIGHT));    // BLUE
        rubiksFaceList.add(new RubiksFace(RubiksFace.RubiksFacePosition.TOP));      // ORANGE
        rubiksFaceList.add(new RubiksFace(RubiksFace.RubiksFacePosition.BOTTOM));   // RED
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
}
