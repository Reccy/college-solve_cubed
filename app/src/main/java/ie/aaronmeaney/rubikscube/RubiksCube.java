package ie.aaronmeaney.rubikscube;

import java.io.Serializable;
import java.nio.file.ClosedFileSystemException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import ie.aaronmeaney.utils.RubiksColorReference;

/**
 * Data representation of a Rubik's Cube
 */
public class RubiksCube implements Serializable {

    private List<RubiksFace> rubiksFaceList;

    private LinkedHashMap<RubiksColor, Integer> rubiksColorMap;

    public RubiksCube(RubiksCube rubiksCube) {
        rubiksFaceList = new ArrayList<>();
        rubiksColorMap = new LinkedHashMap<>();

        for (RubiksFace f : rubiksCube.rubiksFaceList) {
            RubiksFace newFace = new RubiksFace(f);
            f.setParentCube(this);
            rubiksFaceList.add(newFace);
        }

        for (LinkedHashMap.Entry<RubiksColor, Integer> entry : rubiksCube.rubiksColorMap.entrySet()) {
            rubiksColorMap.put(entry.getKey(), entry.getValue());
        }

        rubiksColorMap.putAll(rubiksCube.rubiksColorMap);
    }

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
        RubiksCube initialState = new RubiksCube(this);

        printCube("PRE: " + move);

        switch (move) {
            case UP:

                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.UP);

                break;
            case DOWN:

                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.DOWN);

                break;
            case LEFT:

                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.LEFT);

                break;
            case RIGHT:

                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.RIGHT);

                break;
            case FRONT:

                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.FRONT);

                break;
            case BACK:
                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.BACK);

                break;
            case UP2:

                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.UP);
                initialState = new RubiksCube(this);
                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.UP);

                break;
            case DOWN2:

                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.DOWN);
                initialState = new RubiksCube(this);
                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.DOWN);

                break;
            case LEFT2:

                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.LEFT);
                initialState = new RubiksCube(this);
                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.LEFT);

                break;
            case RIGHT2:

                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.RIGHT);
                initialState = new RubiksCube(this);
                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.RIGHT);

                break;
            case FRONT2:

                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.FRONT);
                initialState = new RubiksCube(this);
                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.FRONT);

                break;
            case BACK2:

                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.BACK);
                initialState = new RubiksCube(this);
                moveFaceClockwise(initialState, RubiksFace.RubiksFacePosition.BACK);

                break;
            case INVERSE_UP:

                moveFaceAntiClockwise(initialState, RubiksFace.RubiksFacePosition.UP);

                break;
            case INVERSE_DOWN:

                moveFaceAntiClockwise(initialState, RubiksFace.RubiksFacePosition.DOWN);

                break;
            case INVERSE_LEFT:

                moveFaceAntiClockwise(initialState, RubiksFace.RubiksFacePosition.LEFT);

                break;
            case INVERSE_RIGHT:

                moveFaceAntiClockwise(initialState, RubiksFace.RubiksFacePosition.RIGHT);

                break;
            case INVERSE_FRONT:

                moveFaceAntiClockwise(initialState, RubiksFace.RubiksFacePosition.FRONT);

                break;
            case INVERSE_BACK:

                moveFaceAntiClockwise(initialState, RubiksFace.RubiksFacePosition.BACK);

                break;
        }

        printCube("POST: " + move);

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

        // Registry to store color outside of cube when turning
        RubiksColorReference registry1;
        RubiksColorReference registry2;
        RubiksColorReference registry3;

        switch (position) {
            case FRONT:
                upEdgeFace = RubiksFace.RubiksFacePosition.UP;
                downEdgeFace = RubiksFace.RubiksFacePosition.DOWN;
                leftEdgeFace = RubiksFace.RubiksFacePosition.LEFT;
                rightEdgeFace = RubiksFace.RubiksFacePosition.RIGHT;

                // Registry is used to store rows so they don't get overwritten
                registry1 = new RubiksColorReference(getRubiksFace(leftEdgeFace).getSquare(3,1));
                registry2 = new RubiksColorReference(getRubiksFace(leftEdgeFace).getSquare(3,2));
                registry3 = new RubiksColorReference(getRubiksFace(leftEdgeFace).getSquare(3,3));

                // Change the up edge
                swapColors(upEdgeFace, 1,3, registry1);
                swapColors(upEdgeFace, 2,3, registry2);
                swapColors(upEdgeFace, 3,3, registry3);

                // Change the right edge
                swapColors(rightEdgeFace, 1,1, registry1);
                swapColors(rightEdgeFace, 1,2, registry2);
                swapColors(rightEdgeFace, 1,3, registry3);

                // Change the down edge
                swapColors(downEdgeFace, 1,1, registry1);
                swapColors(downEdgeFace, 2,1, registry2);
                swapColors(downEdgeFace, 3,1, registry3);

                // Change the left edge
                swapColors(leftEdgeFace, 3,1, registry1);
                swapColors(leftEdgeFace, 3,2, registry2);
                swapColors(leftEdgeFace, 3,3, registry3);

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
                registry1 = new RubiksColorReference(getRubiksFace(rightEdgeFace).getSquare(1,1));
                registry2 = new RubiksColorReference(getRubiksFace(rightEdgeFace).getSquare(2,1));
                registry3 = new RubiksColorReference(getRubiksFace(rightEdgeFace).getSquare(3,1));

                // Change the down edge
                swapColors(downEdgeFace, 1, 1, registry1);
                swapColors(downEdgeFace, 2, 1, registry2);
                swapColors(downEdgeFace, 3, 1, registry3);

                // Change the left edge
                swapColors(leftEdgeFace, 1, 1, registry1);
                swapColors(leftEdgeFace, 2, 1, registry2);
                swapColors(leftEdgeFace, 3, 1, registry3);

                // Change the up edge
                swapColors(upEdgeFace, 1, 1, registry1);
                swapColors(upEdgeFace, 2, 1, registry2);
                swapColors(upEdgeFace, 3, 1, registry3);

                // Change the right edge
                swapColors(rightEdgeFace, 1, 1, registry1);
                swapColors(rightEdgeFace, 2, 1, registry2);
                swapColors(rightEdgeFace, 3, 1, registry3);

                break;
            case DOWN:
                upEdgeFace = RubiksFace.RubiksFacePosition.FRONT;
                downEdgeFace = RubiksFace.RubiksFacePosition.BACK;
                leftEdgeFace = RubiksFace.RubiksFacePosition.LEFT;
                rightEdgeFace = RubiksFace.RubiksFacePosition.RIGHT;

                // Registry is used to store rows so they don't get overwritten
                registry1 = new RubiksColorReference(getRubiksFace(rightEdgeFace).getSquare(1,3));
                registry2 = new RubiksColorReference(getRubiksFace(rightEdgeFace).getSquare(2,3));
                registry3 = new RubiksColorReference(getRubiksFace(rightEdgeFace).getSquare(3,3));

                // Change the down edge
                swapColors(downEdgeFace, 1, 3, registry1);
                swapColors(downEdgeFace, 2, 3, registry2);
                swapColors(downEdgeFace, 3, 3, registry3);

                // Change the left edge
                swapColors(leftEdgeFace, 1, 3, registry1);
                swapColors(leftEdgeFace, 2, 3, registry2);
                swapColors(leftEdgeFace, 3, 3, registry3);

                // Change the up edge
                swapColors(upEdgeFace, 1, 3, registry1);
                swapColors(upEdgeFace, 2, 3, registry2);
                swapColors(upEdgeFace, 3, 3, registry3);

                // Change the right edge
                swapColors(rightEdgeFace, 1, 3, registry1);
                swapColors(rightEdgeFace, 2, 3, registry2);
                swapColors(rightEdgeFace, 3, 3, registry3);

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

        // Registry to store color outside of cube when turning
        RubiksColorReference registry1;
        RubiksColorReference registry2;
        RubiksColorReference registry3;

        switch (position) {
            case FRONT:
                upEdgeFace = RubiksFace.RubiksFacePosition.UP;
                downEdgeFace = RubiksFace.RubiksFacePosition.DOWN;
                leftEdgeFace = RubiksFace.RubiksFacePosition.LEFT;
                rightEdgeFace = RubiksFace.RubiksFacePosition.RIGHT;

                // Registry is used to store rows so they don't get overwritten
                registry1 = new RubiksColorReference(getRubiksFace(rightEdgeFace).getSquare(1,1));
                registry2 = new RubiksColorReference(getRubiksFace(rightEdgeFace).getSquare(1,2));
                registry3 = new RubiksColorReference(getRubiksFace(rightEdgeFace).getSquare(1,3));

                // Change the up edge
                swapColors(upEdgeFace, 1,3, registry1);
                swapColors(upEdgeFace, 2,3, registry2);
                swapColors(upEdgeFace, 3,3, registry3);

                // Change the left edge
                swapColors(leftEdgeFace, 3,1, registry1);
                swapColors(leftEdgeFace, 3,2, registry2);
                swapColors(leftEdgeFace, 3,3, registry3);

                // Change the down edge
                swapColors(downEdgeFace, 1,1, registry1);
                swapColors(downEdgeFace, 2,1, registry2);
                swapColors(downEdgeFace, 3,1, registry3);

                // Change the right edge
                swapColors(rightEdgeFace, 1,1, registry1);
                swapColors(rightEdgeFace, 1,2, registry2);
                swapColors(rightEdgeFace, 1,3, registry3);

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
                registry1 = new RubiksColorReference(getRubiksFace(leftEdgeFace).getSquare(1,1));
                registry2 = new RubiksColorReference(getRubiksFace(leftEdgeFace).getSquare(2,1));
                registry3 = new RubiksColorReference(getRubiksFace(leftEdgeFace).getSquare(3,1));

                // Change the down edge
                swapColors(downEdgeFace, 1, 1, registry1);
                swapColors(downEdgeFace, 2, 1, registry2);
                swapColors(downEdgeFace, 3, 1, registry3);

                // Change the right edge
                swapColors(rightEdgeFace, 1, 1, registry1);
                swapColors(rightEdgeFace, 2, 1, registry2);
                swapColors(rightEdgeFace, 3, 1, registry3);

                // Change the up edge
                swapColors(upEdgeFace, 1, 1, registry1);
                swapColors(upEdgeFace, 2, 1, registry2);
                swapColors(upEdgeFace, 3, 1, registry3);

                // Change the left edge
                swapColors(leftEdgeFace, 1, 1, registry1);
                swapColors(leftEdgeFace, 2, 1, registry2);
                swapColors(leftEdgeFace, 3, 1, registry3);

                break;
            case DOWN:
                upEdgeFace = RubiksFace.RubiksFacePosition.FRONT;
                downEdgeFace = RubiksFace.RubiksFacePosition.BACK;
                leftEdgeFace = RubiksFace.RubiksFacePosition.LEFT;
                rightEdgeFace = RubiksFace.RubiksFacePosition.RIGHT;

                // Registry is used to store rows so they don't get overwritten
                registry1 = new RubiksColorReference(getRubiksFace(leftEdgeFace).getSquare(1,3));
                registry2 = new RubiksColorReference(getRubiksFace(leftEdgeFace).getSquare(2,3));
                registry3 = new RubiksColorReference(getRubiksFace(leftEdgeFace).getSquare(3,3));

                // Change the down edge
                swapColors(downEdgeFace, 1, 3, registry1);
                swapColors(downEdgeFace, 2, 3, registry2);
                swapColors(downEdgeFace, 3, 3, registry3);

                // Change the right edge
                swapColors(rightEdgeFace, 1, 3, registry1);
                swapColors(rightEdgeFace, 2, 3, registry2);
                swapColors(rightEdgeFace, 3, 3, registry3);

                // Change the up edge
                swapColors(upEdgeFace, 1, 3, registry1);
                swapColors(upEdgeFace, 2, 3, registry2);
                swapColors(upEdgeFace, 3, 3, registry3);

                // Change the left edge
                swapColors(leftEdgeFace, 1, 3, registry1);
                swapColors(leftEdgeFace, 2, 3, registry2);
                swapColors(leftEdgeFace, 3, 3, registry3);

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
        setSquare(toFace, toColumn, toRow, initialState.getSquare(fromFace, fromColumn, fromRow));
    }

    /**
     * Swamps the two RubiksColors
     * @param face The face to perform the swap on
     * @param col The col to perform the swap on
     * @param row The row to perform the swap on
     * @param registry The registry to swap
     */
    private void swapColors(RubiksFace.RubiksFacePosition face, int col, int row, RubiksColorReference registry) {
        RubiksColor colorTemp = getSquare(face, col, row);
        setSquare(face, col, row, registry.rubiksColorReference);
        registry.rubiksColorReference = colorTemp;
    }

    /**
     * Sets the square
     * @param face The face containing the square to set
     * @param col The column of the square to set
     * @param row The row of the square to set
     * @param color The color to set the square
     */
    public void setSquare(RubiksFace.RubiksFacePosition face, int col, int row, RubiksColor color) {
        getRubiksFace(face).setSquare(color, col, row);
    }

    /**
     * Sets the square
     * @param face The face containing the square to set
     * @param col The column of the square to set
     * @param row The row of the square to set
     */
    public RubiksColor getSquare(RubiksFace.RubiksFacePosition face, int col, int row) {
        return getRubiksFace(face).getSquare(col, row);
    }

    /**
     * Prints the Rubik's Cube flattened map to the console
     */
    public void printCube(String title) {
        System.out.print("\n");
        System.out.println("[ " + title + " ]");
        System.out.println("   " + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(1,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(2,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(3,1)));
        System.out.println("   " + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(1,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(2,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(3,2)));
        System.out.println("   " + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(1,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(2,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(3,3)));
        System.out.println(singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(1,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(2,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(3,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(1,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(2,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(3,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(1,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(2,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(3,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(1,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(2,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(3,1)));
        System.out.println(singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(1,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(2,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(3,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(1,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(2,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(3,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(1,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(2,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(3,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(1,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(2,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(3,2)));
        System.out.println(singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(1,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(2,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(3,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(1,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(2,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(3,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(1,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(2,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(3,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(1,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(2,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(3,3)));
        System.out.println("   " + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(1,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(2,1)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(3,1)));
        System.out.println("   " + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(1,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(2,2)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(3,2)));
        System.out.println("   " + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(1,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(2,3)) + singleChar(getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(3,3)));
    }

    public void printCube() {
        printCube("RUBIK'S CUBE");
    }

    /**
     * Returns the RubiksColor's first letter
     * @param color The color
     * @return The first character of the color
     */
    private String singleChar(RubiksColor color) {
        switch(color) {
            case RED:
                return "R";
            case GREEN:
                return "G";
            case BLUE:
                return "B";
            case YELLOW:
                return "Y";
            case ORANGE:
                return "O";
            case WHITE:
                return "W";
        }

        return "R";
    }
}
