package ie.aaronmeaney.solvecubed;

import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.util.Pair;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Timer;
import java.util.TimerTask;

import ie.aaronmeaney.rubikscube.RubiksColor;
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

    // Async task for reading the Rubik's Cube face
    private Handler readHandler;
    private Runnable readRunnable;

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        setHelpDialogue(getResources().getString(R.string.cube_input_help));

        // Setup the color palette
        colorPalette = IntentUtilities.GetExtraLinkedHashMap(getIntent(), getResources().getString(R.string.palette_picker_hash_map));

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

        // Setup onClickListener for the capture FAB
        captureCubeFaceFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtilities.StartActivity(thisActivity, CubeConfirmationActivity.class);
            }
        });

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
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // Set the coordinate values for the squares
        Pair<Integer, Integer> cubeInputGridCenter;

        int cubeInputGridWidth = cubeInputGrid.getWidth();
        int cubeInputGridHalf = cubeInputGridWidth/2;
        int cubeInputGridThird = cubeInputGridWidth/3;

        int[] cubeInputGridAnchor = new int[2];
        cubeInputGrid.getLocationOnScreen(cubeInputGridAnchor);

        cubeInputGridCenter = new Pair<>(cubeInputGridAnchor[0] + cubeInputGridHalf, cubeInputGridAnchor[1] + cubeInputGridHalf);

        coordTopLeft =      new Pair<>(cubeInputGridCenter.first - cubeInputGridThird, cubeInputGridCenter.second - cubeInputGridThird);
        coordTop =          new Pair<>(cubeInputGridCenter.first, cubeInputGridCenter.second - cubeInputGridThird);
        coordTopRight =     new Pair<>(cubeInputGridCenter.first + cubeInputGridThird, cubeInputGridCenter.second - cubeInputGridThird);

        coordLeft =         new Pair<>(cubeInputGridCenter.first - cubeInputGridThird, cubeInputGridCenter.second);
        coordCenter =       new Pair<>(cubeInputGridCenter.first, cubeInputGridCenter.second);
        coordRight =        new Pair<>(cubeInputGridCenter.first + cubeInputGridThird, cubeInputGridCenter.second);

        coordBottomLeft =   new Pair<>(cubeInputGridCenter.first - cubeInputGridThird, cubeInputGridCenter.second + cubeInputGridThird);
        coordBottom =       new Pair<>(cubeInputGridCenter.first, cubeInputGridCenter.second + cubeInputGridThird);
        coordBottomRight =  new Pair<>(cubeInputGridCenter.first + cubeInputGridThird, cubeInputGridCenter.second + cubeInputGridThird);

        // Begin running the UI updater every X seconds
        readRunnable = new Runnable() {
            @Override
            public void run() {
                readRubiksCube();

                readHandler.postDelayed(this, 200);
            }
        };

        readHandler = new Handler();
        readHandler.postDelayed(readRunnable, 200);
    }

    /**
     * Reads the Rubik's cube colors and updates the data model.
     */
    private void readRubiksCube() {
        setIndicatorColor(indicatorTopLeft, getClosestRubiksColorAtPoint(coordTopLeft));
        setIndicatorColor(indicatorTop, getClosestRubiksColorAtPoint(coordTop));
        setIndicatorColor(indicatorTopRight, getClosestRubiksColorAtPoint(coordTopRight));
        setIndicatorColor(indicatorLeft, getClosestRubiksColorAtPoint(coordLeft));
        setIndicatorColor(indicatorRight, getClosestRubiksColorAtPoint(coordRight));
        setIndicatorColor(indicatorBottomLeft, getClosestRubiksColorAtPoint(coordBottomLeft));
        setIndicatorColor(indicatorBottom, getClosestRubiksColorAtPoint(coordBottom));
        setIndicatorColor(indicatorBottomRight, getClosestRubiksColorAtPoint(coordBottomRight));
    }

    private void setFace(RubiksFace.RubiksFacePosition facePosition) {
        switch (facePosition) {
            case FRONT:     // WHITE
                setIndicatorColor(indicatorCenter, RubiksColor.WHITE);
                setRelativeFaceColor(relativeFaceTop, RubiksColor.ORANGE);
                setRelativeFaceColor(relativeFaceLeft, RubiksColor.GREEN);
                break;
            case LEFT:      // GREEN
                setIndicatorColor(indicatorCenter, RubiksColor.GREEN);
                setRelativeFaceColor(relativeFaceTop, RubiksColor.ORANGE);
                setRelativeFaceColor(relativeFaceLeft, RubiksColor.YELLOW);
                break;
            case RIGHT:     // BLUE
                setIndicatorColor(indicatorCenter, RubiksColor.BLUE);
                setRelativeFaceColor(relativeFaceTop, RubiksColor.ORANGE);
                setRelativeFaceColor(relativeFaceLeft, RubiksColor.WHITE);
                break;
            case BACK:      // YELLOW
                setIndicatorColor(indicatorCenter, RubiksColor.YELLOW);
                setRelativeFaceColor(relativeFaceTop, RubiksColor.ORANGE);
                setRelativeFaceColor(relativeFaceLeft, RubiksColor.BLUE);
                break;
            case TOP:       // ORANGE
                setIndicatorColor(indicatorCenter, RubiksColor.ORANGE);
                setRelativeFaceColor(relativeFaceTop, RubiksColor.YELLOW);
                setRelativeFaceColor(relativeFaceLeft, RubiksColor.GREEN);
                break;
            case BOTTOM:    // RED
                setIndicatorColor(indicatorCenter, RubiksColor.RED);
                setRelativeFaceColor(relativeFaceTop, RubiksColor.WHITE);
                setRelativeFaceColor(relativeFaceLeft, RubiksColor.GREEN);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Make Camera render to the SurfaceView
        if (surfaceTexture != null) {
            simpleCameraManager.streamCameraToTexture(backCameraId, new Surface(surfaceTexture));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        readHandler.removeCallbacks(readRunnable);
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
        return Math.abs(Color.red(a) - Color.red(b)) + Math.abs(Color.green(a) - Color.green(b)) + Math.abs(Color.blue(b) - Color.blue(b));
    }
}