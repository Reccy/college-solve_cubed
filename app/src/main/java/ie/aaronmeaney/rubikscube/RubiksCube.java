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

    /**
     * Checks if the Rubik's cube is valid, and it solveable
     * @return True if solvable, False otherwise
     */
    public boolean isValidConfiguration() {
        // Check if the cube has the correct amount of squares

        if (!isSquareQuantityValid()) {
            return false;
        }

        return true;
    }

    /**
     * Checks if there are 9 squares per color
     * @return True if there are 9 squares
     */
    private boolean isSquareQuantityValid() {
        int blueSquares = 0;
        int yellowSquares = 0;
        int greenSquares = 0;
        int whiteSquares = 0;
        int orangeSquares = 0;
        int redSquares = 0;

        for (RubiksFace face : rubiksFaceList) {
            for (RubiksColor color : face.getRubiksColors()) {
                switch(color) {
                    case RED:
                        redSquares++;
                        break;
                    case GREEN:
                        greenSquares++;
                        break;
                    case BLUE:
                        blueSquares++;
                        break;
                    case YELLOW:
                        yellowSquares++;
                        break;
                    case ORANGE:
                        orangeSquares++;
                        break;
                    case WHITE:
                        whiteSquares++;
                        break;
                }
            }
        }

        System.out.println("BLUE:   " + blueSquares);
        System.out.println("YELLOW: " + yellowSquares);
        System.out.println("GREEN:  " + greenSquares);
        System.out.println("WHITE:  " + whiteSquares);
        System.out.println("ORANGE: " + orangeSquares);
        System.out.println("RED:    " + redSquares);

        return (blueSquares == 9 && yellowSquares == 9 && greenSquares == 9 && whiteSquares == 9 && orangeSquares == 9 && redSquares == 9);
    }
}
