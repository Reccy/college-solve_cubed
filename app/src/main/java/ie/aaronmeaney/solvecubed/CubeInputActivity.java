package ie.aaronmeaney.solvecubed;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Pair;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import ie.aaronmeaney.rubikscube.RubiksColor;
import ie.aaronmeaney.rubikscube.RubiksCube;
import ie.aaronmeaney.rubikscube.RubiksFace;
import ie.aaronmeaney.utils.IntentUtilities;
import ie.aaronmeaney.utils.SimpleCameraManager;

/**
 * Creates a representation of a Rubik's cube based on the input from the user's camera.
 */
public class CubeInputActivity extends SolveCubedAppCompatActivity implements TextureView.SurfaceTextureListener {

    // Output SurfaceView for the camera
    private TextureView cameraTextureView;

    // Surface Texture for the camera
    private SurfaceTexture surfaceTexture;

    // Wrapper for camera2 API
    private SimpleCameraManager simpleCameraManager;

    // ID of the back camera
    private String backCameraId;

    // Parent object for indicators
    private LinearLayout cubeInputGrid;

    // Indicators for the detected Rubik's Colors
    private ImageView indicatorTopLeft;
    private ImageView indicatorTop;
    private ImageView indicatorTopRight;
    private ImageView indicatorLeft;
    private ImageView indicatorCenter;
    private ImageView indicatorRight;
    private ImageView indicatorBottomLeft;
    private ImageView indicatorBottom;
    private ImageView indicatorBottomRight;

    // Display to orient the cube relative to adjacent faces
    private ImageView relativeFaceTop;
    private ImageView relativeFaceLeft;

    // FAB to capture the cube face configuration the camera is looking at
    private FloatingActionButton captureCubeFaceFAB;

    // Screen Space co-ordinates for the Rubik's Cube face squares
    private Pair<Integer, Integer> coordTopLeft;
    private Pair<Integer, Integer> coordTop;
    private Pair<Integer, Integer> coordTopRight;
    private Pair<Integer, Integer> coordLeft;
    private Pair<Integer, Integer> coordCenter;
    private Pair<Integer, Integer> coordRight;
    private Pair<Integer, Integer> coordBottomLeft;
    private Pair<Integer, Integer> coordBottom;
    private Pair<Integer, Integer> coordBottomRight;

    // The color mappings set by the color palette picker
    private LinkedHashMap<RubiksColor, Integer> colorPalette;

    // Data representation of Rubik's Cube
    private RubiksCube rubiksCube;

    // Currently displayed Rubik's Cube face
    private RubiksColor currentFaceColor;

    // Timer to schedule indicator UI updates
    private Timer readTimer;

    // Alert Dialog for misconfigured Rubiks Cube
    private AlertDialog misconfiguredCubeDialog;

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        setHelpDialogue(getResources().getString(R.string.cube_input_help));

        // Setup the color palette
        colorPalette = IntentUtilities.GetExtraLinkedHashMap(getIntent(), getResources().getString(R.string.palette_picker_hash_map));

        // Init Rubik's Cube
        rubiksCube = new RubiksCube(colorPalette);

        // Inflate the view
        setContentView(R.layout.activity_cube_input);

        /*
         * Post view inflation
         */

        // Get references to UI elements
        cameraTextureView = findViewById(R.id.cube_input_texture_view_camera);

        cubeInputGrid = findViewById(R.id.cube_input_grid);

        indicatorTopLeft = findViewById(R.id.cube_input_indicator_top_left);
        indicatorTop = findViewById(R.id.cube_input_indicator_top);
        indicatorTopRight = findViewById(R.id.cube_input_indicator_top_right);
        indicatorLeft = findViewById(R.id.cube_input_indicator_left);
        indicatorCenter = findViewById(R.id.cube_input_indicator_center);
        indicatorRight = findViewById(R.id.cube_input_indicator_right);
        indicatorBottomLeft = findViewById(R.id.cube_input_indicator_bottom_left);
        indicatorBottom = findViewById(R.id.cube_input_indicator_bottom);
        indicatorBottomRight = findViewById(R.id.cube_input_indicator_bottom_right);

        relativeFaceTop = findViewById(R.id.cube_input_relative_face_top);
        relativeFaceLeft = findViewById(R.id.cube_input_relative_face_left);

        captureCubeFaceFAB = findViewById(R.id.cube_input_fab_capture_cube_face);

        // Setup SimpleCameraManager
        simpleCameraManager = new SimpleCameraManager(this);
        backCameraId = simpleCameraManager.getBackCameraId();
        cameraTextureView.setSurfaceTextureListener(this);

        // Initialize colors to RED, will change on setFace call
        setIndicatorColor(indicatorTopLeft, RubiksColor.RED);
        setIndicatorColor(indicatorTop, RubiksColor.RED);
        setIndicatorColor(indicatorTopRight, RubiksColor.RED);
        setIndicatorColor(indicatorLeft, RubiksColor.RED);
        setIndicatorColor(indicatorCenter, RubiksColor.RED);
        setIndicatorColor(indicatorRight, RubiksColor.RED);
        setIndicatorColor(indicatorBottomLeft, RubiksColor.RED);
        setIndicatorColor(indicatorBottom, RubiksColor.RED);
        setIndicatorColor(indicatorBottomRight, RubiksColor.RED);
        setRelativeFaceColor(relativeFaceLeft, RubiksColor.RED);
        setRelativeFaceColor(relativeFaceTop, RubiksColor.RED);

        setFace(RubiksFace.RubiksFacePosition.RIGHT);

        // Setup onClickListener for the capture FAB
        captureCubeFaceFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentFaceColor) {
                    case BLUE:
                        saveCubeReadings(RubiksColor.BLUE);
                        setFace(RubiksFace.RubiksFacePosition.BACK);
                        break;
                    case YELLOW:
                        saveCubeReadings(RubiksColor.YELLOW);
                        setFace(RubiksFace.RubiksFacePosition.LEFT);
                        break;
                    case GREEN:
                        saveCubeReadings(RubiksColor.GREEN);
                        setFace(RubiksFace.RubiksFacePosition.FRONT);
                        break;
                    case WHITE:
                        saveCubeReadings(RubiksColor.WHITE);
                        setFace(RubiksFace.RubiksFacePosition.UP);
                        break;
                    case ORANGE:
                        saveCubeReadings(RubiksColor.ORANGE);
                        setFace(RubiksFace.RubiksFacePosition.DOWN);
                        break;
                    case RED:
                        saveCubeReadings(RubiksColor.RED);

                        if (rubiksCube.isValidConfiguration()) {
                            IntentUtilities.StartActivityWithRubiksCube(CubeInputActivity.this, CubeConfirmationActivity.class, getResources().getString(R.string.cube_input_cube_data), rubiksCube);
                        } else {
                            misconfiguredCubeDialog = new AlertDialog.Builder(CubeInputActivity.this).setTitle(R.string.err_misconfigured_cube)
                                    .setMessage(R.string.err_misconfigured_cube_desc)
                                    .setCancelable(false)
                                    .setPositiveButton(R.string.btn_okay, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                            misconfiguredCubeDialog = null;
                                            CubeInputActivity.this.recreate();
                                        }
                                    })
                                    .create();
                            misconfiguredCubeDialog.show();
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // Set the coordinate values for the squares
        final Pair<Integer, Integer> cubeInputGridCenter;

        int cubeInputGridWidth = cubeInputGrid.getWidth();
        int cubeInputGridHalf = cubeInputGridWidth/2;
        int cubeInputGridThird = cubeInputGridWidth/3;

        final int[] cubeInputGridAnchor = new int[2];
        cubeInputGrid.getLocationOnScreen(cubeInputGridAnchor);
        cubeInputGridAnchor[1] = cubeInputGridAnchor[1] - 320; // HACK: This is offset by 320 for some reason and I need to fix it

        cubeInputGridCenter = new Pair<>(cubeInputGridAnchor[0] + cubeInputGridHalf, cubeInputGridAnchor[1] + cubeInputGridHalf);

        coordTopLeft = new Pair<>(cubeInputGridCenter.first - cubeInputGridThird, cubeInputGridCenter.second - cubeInputGridThird);
        coordTop = new Pair<>(cubeInputGridCenter.first, cubeInputGridCenter.second - cubeInputGridThird);
        coordTopRight = new Pair<>(cubeInputGridCenter.first + cubeInputGridThird, cubeInputGridCenter.second - cubeInputGridThird);

        coordLeft = new Pair<>(cubeInputGridCenter.first - cubeInputGridThird, cubeInputGridCenter.second);
        coordCenter = new Pair<>(cubeInputGridCenter.first, cubeInputGridCenter.second);
        coordRight = new Pair<>(cubeInputGridCenter.first + cubeInputGridThird, cubeInputGridCenter.second);

        coordBottomLeft = new Pair<>(cubeInputGridCenter.first - cubeInputGridThird, cubeInputGridCenter.second + cubeInputGridThird);
        coordBottom = new Pair<>(cubeInputGridCenter.first, cubeInputGridCenter.second + cubeInputGridThird);
        coordBottomRight = new Pair<>(cubeInputGridCenter.first + cubeInputGridThird, cubeInputGridCenter.second + cubeInputGridThird);

        // Update the indicator UI in the background
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                new ReadRubiksCube().execute();
            }
        };

        readTimer = new Timer();
        readTimer.schedule(timerTask, 250, 250);
    }



    /**
     * Reads the Rubik's cube colors and updates the data model.
     */
    private class ReadRubiksCube extends AsyncTask<Void, Void, List<RubiksColor>> {
        @Override
        protected List<RubiksColor> doInBackground(Void... voids) {
            List<RubiksColor> rubiksColors = new ArrayList<>();

            rubiksColors.add(getClosestRubiksColorAtPoint(coordTopLeft));
            rubiksColors.add(getClosestRubiksColorAtPoint(coordTop));
            rubiksColors.add(getClosestRubiksColorAtPoint(coordTopRight));
            rubiksColors.add(getClosestRubiksColorAtPoint(coordLeft));
            rubiksColors.add(getClosestRubiksColorAtPoint(coordRight));
            rubiksColors.add(getClosestRubiksColorAtPoint(coordBottomLeft));
            rubiksColors.add(getClosestRubiksColorAtPoint(coordBottom));
            rubiksColors.add(getClosestRubiksColorAtPoint(coordBottomRight));

            return rubiksColors;
        }

        @Override
        protected void onPostExecute(List<RubiksColor> rubiksColors) {
            setIndicatorColor(indicatorTopLeft, rubiksColors.get(0));
            setIndicatorColor(indicatorTop, rubiksColors.get(1));
            setIndicatorColor(indicatorTopRight, rubiksColors.get(2));
            setIndicatorColor(indicatorLeft, rubiksColors.get(3));
            setIndicatorColor(indicatorRight, rubiksColors.get(4));
            setIndicatorColor(indicatorBottomLeft, rubiksColors.get(5));
            setIndicatorColor(indicatorBottom, rubiksColors.get(6));
            setIndicatorColor(indicatorBottomRight, rubiksColors.get(7));
        }
    }

    /**
     * Set the current face that the app is reading from.
     * @param facePosition The absolute position of the face.
     */
    private void setFace(RubiksFace.RubiksFacePosition facePosition) {
        switch (facePosition) {
            case FRONT:     // WHITE
                setIndicatorColor(indicatorCenter, RubiksColor.WHITE);
                setRelativeFaceColor(relativeFaceTop, RubiksColor.ORANGE);
                setRelativeFaceColor(relativeFaceLeft, RubiksColor.GREEN);
                currentFaceColor = RubiksColor.WHITE;
                break;
            case LEFT:      // GREEN
                setIndicatorColor(indicatorCenter, RubiksColor.GREEN);
                setRelativeFaceColor(relativeFaceTop, RubiksColor.ORANGE);
                setRelativeFaceColor(relativeFaceLeft, RubiksColor.YELLOW);
                currentFaceColor = RubiksColor.GREEN;
                break;
            case RIGHT:     // BLUE
                setIndicatorColor(indicatorCenter, RubiksColor.BLUE);
                setRelativeFaceColor(relativeFaceTop, RubiksColor.ORANGE);
                setRelativeFaceColor(relativeFaceLeft, RubiksColor.WHITE);
                currentFaceColor = RubiksColor.BLUE;
                break;
            case BACK:      // YELLOW
                setIndicatorColor(indicatorCenter, RubiksColor.YELLOW);
                setRelativeFaceColor(relativeFaceTop, RubiksColor.ORANGE);
                setRelativeFaceColor(relativeFaceLeft, RubiksColor.BLUE);
                currentFaceColor = RubiksColor.YELLOW;
                break;
            case UP:       // ORANGE
                setIndicatorColor(indicatorCenter, RubiksColor.ORANGE);
                setRelativeFaceColor(relativeFaceTop, RubiksColor.YELLOW);
                setRelativeFaceColor(relativeFaceLeft, RubiksColor.GREEN);
                currentFaceColor = RubiksColor.ORANGE;
                break;
            case DOWN:    // RED
                setIndicatorColor(indicatorCenter, RubiksColor.RED);
                setRelativeFaceColor(relativeFaceTop, RubiksColor.WHITE);
                setRelativeFaceColor(relativeFaceLeft, RubiksColor.GREEN);
                currentFaceColor = RubiksColor.RED;
                break;
        }
    }

    /**
     * Saves the current read configuration of the cube face to the Rubik's Cube object.
     * @param color The color of the current face to save to.
     */
    private void saveCubeReadings(RubiksColor color) {
        RubiksFace face = rubiksCube.getRubiksFace(color);

        face.setSquare(getIndicatorColor(indicatorTopLeft),     1,1);       // UP LEFT
        face.setSquare(getIndicatorColor(indicatorTop),         2,1);       // UP CENTER
        face.setSquare(getIndicatorColor(indicatorTopRight),    3,1);       // UP RIGHT
        face.setSquare(getIndicatorColor(indicatorLeft),        1,2);       // CENTER LEFT
        face.setSquare(getIndicatorColor(indicatorRight),       3,2);       // CENTER RIGHT
        face.setSquare(getIndicatorColor(indicatorBottomLeft),  1,3);       // DOWN LEFT
        face.setSquare(getIndicatorColor(indicatorBottom),      2,3);       // DOWN CENTER
        face.setSquare(getIndicatorColor(indicatorBottomRight), 3,3);       // DOWN RIGHT
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Reset current face
        setFace(RubiksFace.RubiksFacePosition.RIGHT);

        // Make Camera render to the SurfaceView
        if (surfaceTexture != null) {
            simpleCameraManager.streamCameraToTexture(backCameraId, new Surface(surfaceTexture));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        readTimer.cancel();
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {

        this.surfaceTexture = surfaceTexture;

        // Make Camera render to the SurfaceView
        simpleCameraManager.streamCameraToTexture(backCameraId, new Surface(surfaceTexture));
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {}

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        this.surfaceTexture = surfaceTexture;
    }

    /**
     * Changes the indicator's color.
     * @param indicator Indicator to change the color of
     * @param color Color to change the indicator to
     */
    private void setIndicatorColor(ImageView indicator, RubiksColor color) {

        // Set indicator color to newColor
        indicator.setBackgroundColor(colorPalette.get(color));
        indicator.setTag(color);
    }

    /**
     * Returns the indicator's Rubik's Cube color
     * @param indicator Indicator to get the color of
     * @return The RubiksColor of the indicator
     */
    private RubiksColor getIndicatorColor(ImageView indicator) {
        RubiksColor color = (RubiksColor)indicator.getTag();

        System.out.println("CLOSEST C: " + color);

        return color;
    }

    /**
     * Sets the relative face's color
     * @param face The relative face to change the color of
     * @param color The color to change the relative face to
     */
    private void setRelativeFaceColor(ImageView face, RubiksColor color) {
        // Set indicator color to newColor
        face.setColorFilter(colorPalette.get(color));
    }

    /**
     * Returns the closest RubiksColor value from the color at the point position.
     * @param point The point position to check on the surfaceView
     * @return The RubiksColor value that's the closest match
     */
    private RubiksColor getClosestRubiksColorAtPoint(Pair<Integer, Integer> point) {
        int actualColor = simpleCameraManager.getPixelFromTextureView(cameraTextureView, point.first, point.second);

        RubiksColor closestRubiksColor = RubiksColor.RED;
        int closestDistance = Integer.MAX_VALUE;

        for (HashMap.Entry<RubiksColor, Integer> entry : colorPalette.entrySet()) {
            int distance = getColorDifference(entry.getValue(), actualColor);

            if (distance < closestDistance) {
                closestRubiksColor = entry.getKey();
                closestDistance = distance;
            }
        }

        return closestRubiksColor;
    }

    /**
     * Gets how different 2 color values are
     * Adapted from: https://stackoverflow.com/a/23991007
     * @param a The first color to compare
     * @param b The second color to compare
     * @return The difference as an int, between the two colors
     */
    private int getColorDifference(int a, int b) {
        return (int)Math.sqrt(Math.pow(Color.red(a) - Color.red(b), 2) + Math.pow(Color.green(a) - Color.green(b), 2) + Math.pow(Color.blue(a) - Color.blue(b), 2));
    }
}