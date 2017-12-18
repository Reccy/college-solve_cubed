package ie.aaronmeaney.rubikscube;

import java.util.Random;

/**
 * Represents a list of legal moves to modify a Rubik's Cube
 */
public enum RubiksMove {

    /**
     * Moves the up face clockwise by 90 degrees
     */
    UP,

    /**
     * Moves the down face clockwise by 90 degrees
     */
    DOWN,

    /**
     * Moves the left face clockwise by 90 degrees
     */
    LEFT,

    /**
     * Moves the right face clockwise by 90 degrees
     */
    RIGHT,

    /**
     * Moves the front face clockwise by 90 degrees
     */
    FRONT,

    /**
     * Moves the back face clockwise by 90 degrees
     */
    BACK,

    /**
     * Moves the up face clockwise by 180 degrees
     */
    UP2,

    /**
     * Moves the down face clockwise by 180 degrees
     */
    DOWN2,

    /**
     * Moves the left face clockwise by 180 degrees
     */
    LEFT2,

    /**
     * Moves the right face clockwise by 180 degrees
     */
    RIGHT2,

    /**
     * Moves the front face clockwise by 180 degrees
     */
    FRONT2,

    /**
     * Moves the back face clockwise by 180 degrees
     */
    BACK2,

    /**
     * Moves the up face anti-clockwise by 90 degrees
     */
    INVERSE_UP,

    /**
     * Moves the down face anti-clockwise by 90 degrees
     */
    INVERSE_DOWN,

    /**
     * Moves the left face anti-clockwise by 90 degrees
     */
    INVERSE_LEFT,

    /**
     * Moves the right face anti-clockwise by 90 degrees
     */
    INVERSE_RIGHT,

    /**
     * Moves the front face anti-clockwise by 90 degrees
     */
    INVERSE_FRONT,

    /**
     * Moves the back face anti-clockwise by 90 degrees
     */
    INVERSE_BACK;

    public static RubiksMove random() {
        Random random = new Random();
        int i = random.nextInt(17);

        return RubiksMove.values()[i];
    }
}
