package ie.aaronmeaney.rubikscube;

import org.junit.Test;

import java.util.LinkedHashMap;

import static org.junit.Assert.*;

public class RubiksCubeTest {
    @Test
    public void isValidConfiguration() throws Exception {
        RubiksCube rubiksCube = getDefaultCube();

        printCube(rubiksCube, "Initial Config");

        // Test UP clockwise
        rubiksCube.performMove(RubiksMove.UP);

        printCube(rubiksCube, "UP MOVE");

        assertEquals(RubiksColor.BLUE, rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(2,1));
    }

    @Test
    public void performMove() throws Exception {
    }

    /**
     * Prints the Rubik's Cube flattened map to the console
     * @param rubiksCube The cube to print
     */
    private void printCube(RubiksCube rubiksCube, String title) {
        System.out.print("\n");
        System.out.println("[ " + title + " ]");
        System.out.println("   " + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(1,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(2,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(3,1)));
        System.out.println("   " + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(1,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(2,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(3,2)));
        System.out.println("   " + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(1,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(2,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.UP).getSquare(3,3)));
        System.out.println(singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(1,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(2,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(3,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(1,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(2,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(3,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(1,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(2,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(3,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(1,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(2,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(3,1)));
        System.out.println(singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(1,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(2,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(3,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(1,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(2,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(3,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(1,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(2,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(3,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(1,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(2,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(3,2)));
        System.out.println(singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(1,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(2,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.LEFT).getSquare(3,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(1,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(2,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.FRONT).getSquare(3,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(1,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(2,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.RIGHT).getSquare(3,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(1,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(2,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.BACK).getSquare(3,3)));
        System.out.println("   " + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(1,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(2,1)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(3,1)));
        System.out.println("   " + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(1,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(2,2)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(3,2)));
        System.out.println("   " + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(1,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(2,3)) + singleChar(rubiksCube.getRubiksFace(RubiksFace.RubiksFacePosition.DOWN).getSquare(3,3)));
        System.out.print("\n");
    }

    private void printCube(RubiksCube rubiksCube) {
        printCube(rubiksCube, "RUBIK'S CUBE");
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