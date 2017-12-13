package ie.aaronmeaney.solvecubed;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import java.util.LinkedHashMap;

import ie.aaronmeaney.rubikscube.RubiksColor;
import ie.aaronmeaney.rubikscube.RubiksFace;
import ie.aaronmeaney.utils.IntentUtilities;
import ie.aaronmeaney.utils.SimpleCameraManager;

/**
 * Creates a representation of a Rubik's cube based on the input from the user's camera.
 */
public class CubeInputActivity extends SolveCubedAppCompatActivity {

    // Output SurfaceView for the camera
    private SurfaceView cameraSurfaceView;

    // Wrapper for camera2 API
    private SimpleCameraManager simpleCameraManager;

    // ID of the back camera
    private String backCameraId;

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

    // The color mappings set by the color palette picker
    private LinkedHashMap<RubiksColor, Integer> colorPalette;

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
        cameraSurfaceView = findViewById(R.id.cube_input_surface_view_camera);

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

        // Make Camera render to the SurfaceView
        simpleCameraManager = new SimpleCameraManager(this);
        backCameraId = simpleCameraManager.getBackCameraId();
        simpleCameraManager.streamCameraToTexture(backCameraId, cameraSurfaceView.getHolder().getSurface());

        // Setup onClickListener for the capture FAB
        captureCubeFaceFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtilities.StartActivity(thisActivity, CubeConfirmationActivity.class);
            }
        });

        // TEMP: Colors only used for demonstration purposes
        setIndicatorColor(indicatorTopLeft, RubiksColor.ORANGE);
        setIndicatorColor(indicatorTop, RubiksColor.GREEN);
        setIndicatorColor(indicatorTopRight, RubiksColor.YELLOW);
        setIndicatorColor(indicatorLeft, RubiksColor.ORANGE);
        setIndicatorColor(indicatorCenter, RubiksColor.ORANGE);
        setIndicatorColor(indicatorRight, RubiksColor.BLUE);
        setIndicatorColor(indicatorBottomLeft, RubiksColor.ORANGE);
        setIndicatorColor(indicatorBottom, RubiksColor.WHITE);
        setIndicatorColor(indicatorBottomRight, RubiksColor.YELLOW);
        setRelativeFaceColor(relativeFaceLeft, RubiksColor.WHITE);
        setRelativeFaceColor(relativeFaceTop, RubiksColor.GREEN);

        setFace(RubiksFace.RubiksFacePosition.RIGHT);
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
        simpleCameraManager.streamCameraToTexture(backCameraId, cameraSurfaceView.getHolder().getSurface());
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
}
