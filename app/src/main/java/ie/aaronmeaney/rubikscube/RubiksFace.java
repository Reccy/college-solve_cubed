package ie.aaronmeaney.rubikscube;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Data represenatation of a single Rubik's cube face
 */
public class RubiksFace implements Serializable {

    private RubiksCube parentCube;

    public enum RubiksFacePosition {
        FRONT,
        LEFT,
        RIGHT,
        BACK,
        UP,
        DOWN
    }

    private RubiksFacePosition facePosition;

    /**
     * The colors of each square in the face.
     * The center square is that face's main color.
     */
    private RubiksColor[][] squares = new RubiksColor[3][3];

    /**
     * The main color of the face.
     */
    private RubiksColor faceColor;

    public RubiksFace (RubiksFace originalFace) {
        this.facePosition = originalFace.facePosition;
        this.parentCube = originalFace.parentCube;
        this.faceColor = originalFace.faceColor;

        squares[0][0] = originalFace.squares[0][0];
        squares[1][0] = originalFace.squares[1][0];
        squares[2][0] = originalFace.squares[2][0];
        squares[0][1] = originalFace.squares[0][1];
        squares[1][1] = originalFace.squares[1][1];
        squares[2][1] = originalFace.squares[2][1];
        squares[0][2] = originalFace.squares[0][2];
        squares[1][2] = originalFace.squares[1][2];
        squares[2][2] = originalFace.squares[2][2];
    }

    /**
     * Constructs the RubiksFace.
     * @param facePosition The face position relative to front.
     */
    public RubiksFace (RubiksFacePosition facePosition, RubiksCube parentCube) {
        this.facePosition = facePosition;
        this.parentCube = parentCube;

        switch (facePosition) {
            case FRONT:
                faceColor = RubiksColor.WHITE;
                break;
            case LEFT:
                faceColor = RubiksColor.GREEN;
                break;
            case RIGHT:
                faceColor = RubiksColor.BLUE;
                break;
            case BACK:
                faceColor = RubiksColor.YELLOW;
                break;
            case UP:
                faceColor = RubiksColor.ORANGE;
                break;
            case DOWN:
                faceColor = RubiksColor.RED;
                break;
        }

        squares[1][1] = faceColor;
    }

    /**
     * Set the square color at position x, y.
     * Will not allow the center square (2,2) to be changed.
     * @param rubiksColor The RubiksColor value to set the square as.
     * @param col The square column.
     * @param row The square row.
     * @return true if successful. false otherwise.
     */
    public boolean setSquare(RubiksColor rubiksColor, int col, int row) {
        int x = col - 1;
        int y = row - 1;

        if (x == 1 && y == 1) {
            return false;
        }

        if (x < 0 && y < 0 && x > 2 && y > 2) {
            return false;
        }

        squares[x][y] = rubiksColor;
        return true;
    }

    /**
     * Returns the position of the face
     * @return Returns the RubiksFacePosition value of this face.
     */
    public RubiksFacePosition getFacePosition() {
        return facePosition;
    }

    /**
     * Returns the square color at position x, y.
     * @param col The square column.
     * @param row The square row.
     * @return The RubiksColor value. Null if IndexOutOfBounds.
     */
    public RubiksColor getSquare(int col, int row) {
        int x = col - 1;
        int y = row - 1;

        if (x < 0 && y < 0 && x > 2 && y > 2) {
            return null;
        }

        return squares[x][y];
    }

    public int getSquareAsColor(int col, int row) {
        RubiksColor rColor = getSquare(col, row);

        if (rColor == null) {
            return 0;
        }

        return parentCube.convertRubiksColorToInteger(rColor);
    }

    /**
     * Returns the RubiksColor of this face.
     * Synonymous with getSquare(2,2).
     * @return The RubiksColor value.
     */
    public RubiksColor getFaceColor() {
        return faceColor;
    }

    /**
     * Returns a flattened List of the RubiksCube Square Colors.
     * @return The list of RubiksColors in the face.
     */
    public List<RubiksColor> getRubiksColors() {
        List<RubiksColor> flattenedList = new ArrayList<>();

        flattenedList.add(getSquare(1,1));
        flattenedList.add(getSquare(2,1));
        flattenedList.add(getSquare(3,1));
        flattenedList.add(getSquare(1,2));
        flattenedList.add(getSquare(2,2));
        flattenedList.add(getSquare(3,2));
        flattenedList.add(getSquare(1,3));
        flattenedList.add(getSquare(2,3));
        flattenedList.add(getSquare(3,3));

        return flattenedList;
    }

    /**
     * Set the parent cube of the face
     * @param parent The RubiksCube to set as parent
     */
    public void setParentCube(RubiksCube parent) {
        this.parentCube = parent;
    }
}
