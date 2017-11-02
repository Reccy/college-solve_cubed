package ie.aaronmeaney.solvecubed;

import android.os.Bundle;
import android.view.SurfaceView;

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

        // Make Camera render to the SurfaceView
        simpleCameraManager = new SimpleCameraManager(this);
        backCameraId = simpleCameraManager.getBackCameraId();
        simpleCameraManager.streamCameraToTexture(backCameraId, cameraSurfaceView.getHolder().getSurface());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Make Camera render to the SurfaceView
        simpleCameraManager.streamCameraToTexture(backCameraId, cameraSurfaceView.getHolder().getSurface());
    }
}
