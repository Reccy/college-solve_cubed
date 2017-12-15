package ie.aaronmeaney.rubikscube;

import java.io.Serializable;

/**
 * Data represenatation of a single Rubik's cube face
 */
public class RubiksFace implements Serializable {

    public enum RubiksFacePosition {
        FRONT,
        LEFT,
        RIGHT,
        BACK,
        TOP,
        BOTTOM
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

    /**
     * Constructs the RubiksFace.
     * @param facePosition The face position relative to front.
     */
    public RubiksFace (RubiksFacePosition facePosition) {
        this.facePosition = facePosition;

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
            case TOP:
                faceColor = RubiksColor.ORANGE;
                break;
            case BOTTOM:
                faceColor = RubiksColor.RED;
                break;
        }
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

    /**
     * Returns the RubiksColor of this face.
     * Synonymous with getSquare(1,1).
     * @return The RubiksColor value.
     */
    public RubiksColor getFaceColor() {
        return faceColor;
    }
}
