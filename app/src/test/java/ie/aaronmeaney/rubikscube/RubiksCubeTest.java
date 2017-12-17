package ie.aaronmeaney.rubikscube;

import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class RubiksCubeTest {
    @Test
    public void isValidConfiguration() throws Exception {

        /*
         * TEST UP MOVE AND DEEP CLONE
         */

        RubiksCube rubiksCube = getDefaultCube();

        rubiksCube.setSquare(RubiksFace.RubiksFacePosition.UP, 1,1, RubiksColor.GREEN);

        rubiksCube.setSquare(RubiksFace.RubiksFacePosition.UP, 3, 2, RubiksColor.YELLOW);

        // Test UP clockwise face
        rubiksCube.performMove(RubiksMove.UP);

        assertEquals(RubiksColor.ORANGE, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.UP, 1,1));

        assertEquals(RubiksColor.GREEN, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.UP, 3, 1));

        assertEquals(RubiksColor.ORANGE, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.UP, 3, 2));

        assertEquals(RubiksColor.YELLOW, rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(2,3));

        //Test UP clockwise edge

        assertEquals(RubiksColor.BLUE, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.FRONT, 2, 1));

        assertEquals(RubiksColor.YELLOW, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.RIGHT, 2, 1));

        assertEquals(RubiksColor.WHITE, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.LEFT, 2, 1));

        assertEquals(RubiksColor.GREEN, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.BACK, 2, 1));


        /*
         * TEST INVERSE UP MOVE
         */

        rubiksCube.performMove(RubiksMove.INVERSE_UP);

        assertEquals(RubiksColor.BLUE, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.RIGHT, 2, 1));

        assertEquals(RubiksColor.YELLOW, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.BACK, 2, 1));

        assertEquals(RubiksColor.WHITE, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.FRONT, 2, 1));

        assertEquals(RubiksColor.GREEN, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.LEFT, 2, 1));

        /*
         * TEST DOWN MOVE
         */

        rubiksCube.performMove(RubiksMove.DOWN);

        assertEquals(RubiksColor.GREEN, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.FRONT, 2, 3));

        assertEquals(RubiksColor.BLUE, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.BACK, 2, 3));

        assertEquals(RubiksColor.YELLOW, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.LEFT, 2, 3));

        assertEquals(RubiksColor.WHITE, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.RIGHT, 2, 3));

        /*
         * TEST INVERSE DOWN MOVE
         */

        rubiksCube.performMove(RubiksMove.INVERSE_DOWN);

        assertEquals(RubiksColor.WHITE, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.FRONT, 2, 3));

        assertEquals(RubiksColor.YELLOW, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.BACK, 2, 3));

        assertEquals(RubiksColor.GREEN, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.LEFT, 2, 3));

        assertEquals(RubiksColor.BLUE, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.RIGHT, 2, 3));

        /*
         * FRONT MOVE
         */

        rubiksCube.performMove(RubiksMove.FRONT);

        assertEquals(RubiksColor.GREEN, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.UP, 2, 3));

        assertEquals(RubiksColor.RED, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.LEFT, 3, 2));

        assertEquals(RubiksColor.BLUE, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.DOWN, 2, 1));

        assertEquals(RubiksColor.ORANGE, rubiksCube.getSquare(RubiksFace.RubiksFacePosition.RIGHT, 1, 2));
    }

    /**
     * Get a solved Rubik's Cube
     * @return Default Cube Config
     */
    private RubiksCube getDefaultCube() {
        LinkedHashMap<RubiksColor, Integer> colorPalette = new LinkedHashMap<>();

        colorPalette.put(RubiksColor.RED, 0);
        colorPalette.put(RubiksColor.GREEN, 0);
        colorPalette.put(RubiksColor.BLUE, 0);
        colorPalette.put(RubiksColor.YELLOW, 0);
        colorPalette.put(RubiksColor.ORANGE, 0);
        colorPalette.put(RubiksColor.WHITE, 0);

        RubiksCube rubiksCube = new RubiksCube(colorPalette);

        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).setSquare(RubiksColor.BLUE, 1, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).setSquare(RubiksColor.BLUE, 2, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).setSquare(RubiksColor.BLUE, 3, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).setSquare(RubiksColor.BLUE, 1, 2);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).setSquare(RubiksColor.BLUE, 3, 2);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).setSquare(RubiksColor.BLUE, 1, 3);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).setSquare(RubiksColor.BLUE, 2, 3);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).setSquare(RubiksColor.BLUE, 3, 3);

        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).setSquare(RubiksColor.YELLOW, 1, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).setSquare(RubiksColor.YELLOW, 2, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).setSquare(RubiksColor.YELLOW, 3, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).setSquare(RubiksColor.YELLOW, 1, 2);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).setSquare(RubiksColor.YELLOW, 3, 2);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).setSquare(RubiksColor.YELLOW, 1, 3);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).setSquare(RubiksColor.YELLOW, 2, 3);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).setSquare(RubiksColor.YELLOW, 3, 3);

        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).setSquare(RubiksColor.GREEN, 1, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).setSquare(RubiksColor.GREEN, 2, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).setSquare(RubiksColor.GREEN, 3, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).setSquare(RubiksColor.GREEN, 1, 2);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).setSquare(RubiksColor.GREEN, 3, 2);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).setSquare(RubiksColor.GREEN, 1, 3);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).setSquare(RubiksColor.GREEN, 2, 3);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).setSquare(RubiksColor.GREEN, 3, 3);

        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).setSquare(RubiksColor.WHITE, 1, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).setSquare(RubiksColor.WHITE, 2, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).setSquare(RubiksColor.WHITE, 3, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).setSquare(RubiksColor.WHITE, 1, 2);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).setSquare(RubiksColor.WHITE, 3, 2);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).setSquare(RubiksColor.WHITE, 1, 3);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).setSquare(RubiksColor.WHITE, 2, 3);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).setSquare(RubiksColor.WHITE, 3, 3);

        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).setSquare(RubiksColor.ORANGE, 1, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).setSquare(RubiksColor.ORANGE, 2, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).setSquare(RubiksColor.ORANGE, 3, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).setSquare(RubiksColor.ORANGE, 1, 2);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).setSquare(RubiksColor.ORANGE, 3, 2);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).setSquare(RubiksColor.ORANGE, 1, 3);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).setSquare(RubiksColor.ORANGE, 2, 3);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).setSquare(RubiksColor.ORANGE, 3, 3);

        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).setSquare(RubiksColor.RED, 1, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).setSquare(RubiksColor.RED, 2, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).setSquare(RubiksColor.RED, 3, 1);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).setSquare(RubiksColor.RED, 1, 2);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).setSquare(RubiksColor.RED, 3, 2);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).setSquare(RubiksColor.RED, 1, 3);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).setSquare(RubiksColor.RED, 2, 3);
        rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).setSquare(RubiksColor.RED, 3, 3);

        return rubiksCube;
    }
}