package ie.aaronmeaney.solvecubed;

import android.app.Activity;
import android.os.Bundle;
import android.view.SurfaceView;

public class ColorCalibratorCameraActivity extends Activity {

    // The output view for the camera
    SurfaceView cameraOutput;

    // Simple Camera Manager wrapper for Android camera2 API
    SimpleCameraManager simpleCameraManager;

    // The ID of the back camera
    String backCameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_calibrator_camera);

        cameraOutput = findViewById(R.id.surface_color_calibrator);

        // Make Camera render to the SurfaceView
        simpleCameraManager = new SimpleCameraManager(ColorCalibratorCameraActivity.this);
        backCameraId = simpleCameraManager.getBackCameraId();
        simpleCameraManager.streamCameraToTexture(backCameraId, cameraOutput.getHolder().getSurface());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Create new stream onResume
        simpleCameraManager.streamCameraToTexture(backCameraId, cameraOutput.getHolder().getSurface());
    }
}
