package ie.aaronmeaney.solvecubed;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;

public class ColorCalibratorCameraActivity extends AppCompatActivity {

    // The output view for the camera
    SurfaceView cameraOutput;

    // Simple Camera Manager wrapper for Android camera2 API
    SimpleCameraManager simpleCameraManager;

    // The ID of the back camera
    String backCameraId;

    // The FAB for the camera
    FloatingActionButton capturePhotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_calibrator_camera);

        cameraOutput = findViewById(R.id.surface_color_calibrator);
        capturePhotoButton = findViewById(R.id.camera_color_capture_button);

        // Make Camera render to the SurfaceView
        simpleCameraManager = new SimpleCameraManager(ColorCalibratorCameraActivity.this);
        backCameraId = simpleCameraManager.getBackCameraId();
        simpleCameraManager.streamCameraToTexture(backCameraId, cameraOutput.getHolder().getSurface());

        // Setup capture button
        capturePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Click!");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Create new stream onResume
        simpleCameraManager.streamCameraToTexture(backCameraId, cameraOutput.getHolder().getSurface());
    }
}
