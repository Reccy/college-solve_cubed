package ie.aaronmeaney.solvecubed;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceView;
import android.widget.ImageView;

import ie.aaronmeaney.rubikscube.RubiksColor;
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

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

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

        // Make Camera render to the SurfaceView
        simpleCameraManager = new SimpleCameraManager(this);
        backCameraId = simpleCameraManager.getBackCameraId();
        simpleCameraManager.streamCameraToTexture(backCameraId, cameraSurfaceView.getHolder().getSurface());

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
        int newColor;

        // Set the newColor resource based on the RubiksColor enum.
        switch (color) {
            case RED:
                newColor = R.color.rubiks_red;
                break;
            case GREEN:
                newColor = R.color.rubiks_green;
                break;
            case BLUE:
                newColor = R.color.rubiks_blue;
                break;
            case YELLOW:
                newColor = R.color.rubiks_yellow;
                break;
            case ORANGE:
                newColor = R.color.rubiks_orange;
                break;
            case WHITE:
                newColor = R.color.rubiks_white;
                break;
            default:
                newColor = R.color.rubiks_red;
                break;
        }

        // Set indicator color to newColor
        indicator.setBackgroundColor(ContextCompat.getColor(thisActivity, newColor));
    }

    /**
     * Sets the relative face's color
     * @param face The relative face to change the color of
     * @param color The color to change the relative face to
     */
    private void setRelativeFaceColor(ImageView face, RubiksColor color) {
        int newColor;

        // Set the newColor resource based on the RubiksColor enum.
        switch (color) {
            case RED:
                newColor = R.color.rubiks_red;
                break;
            case GREEN:
                newColor = R.color.rubiks_green;
                break;
            case BLUE:
                newColor = R.color.rubiks_blue;
                break;
            case YELLOW:
                newColor = R.color.rubiks_yellow;
                break;
            case ORANGE:
                newColor = R.color.rubiks_orange;
                break;
            case WHITE:
                newColor = R.color.rubiks_white;
                break;
            default:
                newColor = R.color.rubiks_red;
                break;
        }

        // Set indicator color to newColor
        face.setColorFilter(ContextCompat.getColor(thisActivity, newColor));
    }
}
