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
        rubiksFaceList.add(new RubiksFace(RubiksFace.RubiksFacePosition.UP, this));      // ORANGE
        rubiksFaceList.add(new RubiksFace(RubiksFace.RubiksFacePosition.DOWN, this));   // RED
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

    /**
     * Performs a move on the Rubiks Cube
     * @param move
     */
    public void performMove(RubiksMove move) {
        switch (move) {
            case UP:

                moveFaceClockwise(this, RubiksFace.RubiksFacePosition.UP);

                break;
            case DOWN:

                moveFaceClockwise(this, RubiksFace.RubiksFacePosition.DOWN);

                break;
            case LEFT:
                break;
            case RIGHT:
                break;
            case FRONT:
                break;
            case BACK:
                break;
            case UP2:
                break;
            case DOWN2:
                break;
            case LEFT2:
                break;
            case RIGHT2:
                break;
            case FRONT2:
                break;
            case BACK2:
                break;
            case INVERSE_UP:
                break;
            case INVERSE_DOWN:
                break;
            case INVERSE_LEFT:
                break;
            case INVERSE_RIGHT:
                break;
            case INVERSE_FRONT:
                break;
            case INVERSE_BACK:
                break;
        }

    }

    /**
     * Moves the squares on a face clockwise
     * @param initialState The initial state of the Rubik's Cube
     * @param position The face position of the face to move clockwise
     */
    private void moveFaceClockwise(RubiksCube initialState, RubiksFace.RubiksFacePosition position) {
        moveSquare(initialState, position, 1, 1, position, 3, 1);
        moveSquare(initialState, position, 2, 1, position, 3, 2);
        moveSquare(initialState, position, 3, 1, position, 3, 3);
        moveSquare(initialState, position, 3, 2, position, 2, 3);
        moveSquare(initialState, position, 3, 3, position, 1, 3);
        moveSquare(initialState, position, 2, 3, position, 1, 2);
        moveSquare(initialState, position, 1, 3, position, 1, 1);
        moveSquare(initialState, position, 1, 2, position, 2, 1);

        // Relative faces to the selected position
        RubiksFace.RubiksFacePosition upEdgeFace;
        RubiksFace.RubiksFacePosition downEdgeFace;
        RubiksFace.RubiksFacePosition leftEdgeFace;
        RubiksFace.RubiksFacePosition rightEdgeFace;

        switch (position) {
            case FRONT:
                upEdgeFace = RubiksFace.RubiksFacePosition.UP;
                downEdgeFace = RubiksFace.RubiksFacePosition.DOWN;
                leftEdgeFace = RubiksFace.RubiksFacePosition.LEFT;
                rightEdgeFace = RubiksFace.RubiksFacePosition.RIGHT;

                break;
            case LEFT:
                upEdgeFace = RubiksFace.RubiksFacePosition.UP;
                downEdgeFace = RubiksFace.RubiksFacePosition.DOWN;
                leftEdgeFace = RubiksFace.RubiksFacePosition.BACK;
                rightEdgeFace = RubiksFace.RubiksFacePosition.FRONT;

                break;
            case RIGHT:
                upEdgeFace = RubiksFace.RubiksFacePosition.UP;
                downEdgeFace = RubiksFace.RubiksFacePosition.DOWN;
                leftEdgeFace = RubiksFace.RubiksFacePosition.FRONT;
                rightEdgeFace = RubiksFace.RubiksFacePosition.BACK;

                break;
            case BACK:
                upEdgeFace = RubiksFace.RubiksFacePosition.UP;
                downEdgeFace = RubiksFace.RubiksFacePosition.DOWN;
                leftEdgeFace = RubiksFace.RubiksFacePosition.RIGHT;
                rightEdgeFace = RubiksFace.RubiksFacePosition.LEFT;

                break;
            case UP:
                upEdgeFace = RubiksFace.RubiksFacePosition.BACK;
                downEdgeFace = RubiksFace.RubiksFacePosition.FRONT;
                leftEdgeFace = RubiksFace.RubiksFacePosition.LEFT;
                rightEdgeFace = RubiksFace.RubiksFacePosition.RIGHT;

                // Registry is used to store rows so they don't get overwritten
                RubiksColor registry1 = getRubiksFace(rightEdgeFace).getSquare(1,1);
                RubiksColor registry2 = getRubiksFace(rightEdgeFace).getSquare(2,1);
                RubiksColor registry3 = getRubiksFace(rightEdgeFace).getSquare(3,1);

                // Change the down edge
                swapColors(getSquare(downEdgeFace, 1, 1), registry1);
                swapColors(getSquare(downEdgeFace, 2, 1), registry2);
                swapColors(getSquare(downEdgeFace, 3, 1), registry3);

                // Change the left edge
                swapColors(getSquare(leftEdgeFace, 1, 1), registry1);
                swapColors(getSquare(leftEdgeFace, 2, 1), registry2);
                swapColors(getSquare(leftEdgeFace, 3, 1), registry3);

                // Change the up edge
                swapColors(getSquare(upEdgeFace, 1, 1), registry1);
                swapColors(getSquare(upEdgeFace, 2, 1), registry2);
                swapColors(getSquare(upEdgeFace, 3, 1), registry3);

                // Change the right edge
                swapColors(getSquare(rightEdgeFace, 1, 1), registry1);
                swapColors(getSquare(rightEdgeFace, 2, 1), registry2);
                swapColors(getSquare(rightEdgeFace, 3, 1), registry3);

                break;
            case DOWN:
                upEdgeFace = RubiksFace.RubiksFacePosition.FRONT;
                downEdgeFace = RubiksFace.RubiksFacePosition.BACK;
                leftEdgeFace = RubiksFace.RubiksFacePosition.LEFT;
                rightEdgeFace = RubiksFace.RubiksFacePosition.RIGHT;

                break;
        }
    }

    /**
     * Moves the squares on a face anti-clockwise
     * @param initialState The initial state of the Rubik's Cube
     * @param position The face position of the face to move clockwise
     */
    private void moveFaceAntiClockwise(RubiksCube initialState, RubiksFace.RubiksFacePosition position) {
        moveSquare(initialState, position, 1, 1, position, 1, 3);
        moveSquare(initialState, position, 2, 1, position, 1, 2);
        moveSquare(initialState, position, 3, 1, position, 1, 1);
        moveSquare(initialState, position, 3, 2, position, 2, 1);
        moveSquare(initialState, position, 3, 3, position, 3, 1);
        moveSquare(initialState, position, 2, 3, position, 3, 2);
        moveSquare(initialState, position, 1, 3, position, 3, 3);
        moveSquare(initialState, position, 1, 2, position, 2, 3);

        // Relative faces to the selected position
        RubiksFace.RubiksFacePosition upEdgeFace;
        RubiksFace.RubiksFacePosition downEdgeFace;
        RubiksFace.RubiksFacePosition leftEdgeFace;
        RubiksFace.RubiksFacePosition rightEdgeFace;

        switch (position) {
            case FRONT:
                upEdgeFace = RubiksFace.RubiksFacePosition.UP;
                downEdgeFace = RubiksFace.RubiksFacePosition.DOWN;
                leftEdgeFace = RubiksFace.RubiksFacePosition.LEFT;
                rightEdgeFace = RubiksFace.RubiksFacePosition.RIGHT;

                break;
            case LEFT:
                upEdgeFace = RubiksFace.RubiksFacePosition.UP;
                downEdgeFace = RubiksFace.RubiksFacePosition.DOWN;
                leftEdgeFace = RubiksFace.RubiksFacePosition.BACK;
                rightEdgeFace = RubiksFace.RubiksFacePosition.FRONT;

                break;
            case RIGHT:
                upEdgeFace = RubiksFace.RubiksFacePosition.UP;
                downEdgeFace = RubiksFace.RubiksFacePosition.DOWN;
                leftEdgeFace = RubiksFace.RubiksFacePosition.FRONT;
                rightEdgeFace = RubiksFace.RubiksFacePosition.BACK;

                break;
            case BACK:
                upEdgeFace = RubiksFace.RubiksFacePosition.UP;
                downEdgeFace = RubiksFace.RubiksFacePosition.DOWN;
                leftEdgeFace = RubiksFace.RubiksFacePosition.RIGHT;
                rightEdgeFace = RubiksFace.RubiksFacePosition.LEFT;

                break;
            case UP:
                upEdgeFace = RubiksFace.RubiksFacePosition.BACK;
                downEdgeFace = RubiksFace.RubiksFacePosition.FRONT;
                leftEdgeFace = RubiksFace.RubiksFacePosition.LEFT;
                rightEdgeFace = RubiksFace.RubiksFacePosition.RIGHT;

                break;
            case DOWN:
                upEdgeFace = RubiksFace.RubiksFacePosition.FRONT;
                downEdgeFace = RubiksFace.RubiksFacePosition.BACK;
                leftEdgeFace = RubiksFace.RubiksFacePosition.LEFT;
                rightEdgeFace = RubiksFace.RubiksFacePosition.RIGHT;

                break;
        }
    }

    /**
     * Move a square
     * @param initialState The initial state of the Rubik's Cube
     * @param fromFace The face to move the square from
     * @param fromColumn The column to move the square from
     * @param fromRow The row to move the square from
     * @param toFace The face to move the square to
     * @param toColumn The column to move the square to
     * @param toRow The row to move the square to
     */
    private void moveSquare(RubiksCube initialState, RubiksFace.RubiksFacePosition fromFace, int fromColumn, int fromRow, RubiksFace.RubiksFacePosition toFace, int toColumn, int toRow) {
        getRubiksFace(toFace).setSquare(initialState.getRubiksFace(fromFace).getSquare(fromColumn, fromRow), toColumn,toRow);
    }

    /**
     * Swamps the two RubiksColors
     * @param colorOne The first color to swap
     * @param colorTwo The second color to swap
     */
    private void swapColors(RubiksColor colorOne, RubiksColor colorTwo) {
        RubiksColor tempColor = colorTwo;
        colorTwo = colorOne;
        colorOne = tempColor;
    }

    /**
     * Sets the square
     * @param face The face containing the square to set
     * @param col The column of the square to set
     * @param row The row of the square to set
     * @param color The color to set the square
     */
    private void setSquare(RubiksFace.RubiksFacePosition face, int col, int row, RubiksColor color) {
        getRubiksFace(face).setSquare(color, col, row);
    }

    /**
     * Sets the square
     * @param face The face containing the square to set
     * @param col The column of the square to set
     * @param row The row of the square to set
     */
    private RubiksColor getSquare(RubiksFace.RubiksFacePosition face, int col, int row) {
        return getRubiksFace(face).getSquare(col, row);
    }
}
