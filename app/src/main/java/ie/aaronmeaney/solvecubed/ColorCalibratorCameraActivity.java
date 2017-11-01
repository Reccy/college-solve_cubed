package ie.aaronmeaney.solvecubed;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import java.util.HashMap;

public class ColorCalibratorCameraActivity extends AppCompatActivity {

    // The output view for the camera
    SurfaceView cameraOutput;

    // Simple Camera Manager wrapper for Android camera2 API
    SimpleCameraManager simpleCameraManager;

    // The ID of the back camera
    String backCameraId;

    // Reference Color indicator
    ImageView referenceColorIndicator;

    // The FAB for the camera
    FloatingActionButton capturePhotoButton;

    // The FABs for the selected color
    HashMap<RubiksColor, FloatingActionButton> selectedRubiksColors;
    FloatingActionButton activeRubiksColorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_calibrator_camera);

        cameraOutput = findViewById(R.id.surface_color_calibrator);
        capturePhotoButton = findViewById(R.id.camera_color_capture_button);

        referenceColorIndicator = findViewById(R.id.camera_color_reference);

        // Make Camera render to the SurfaceView
        simpleCameraManager = new SimpleCameraManager(ColorCalibratorCameraActivity.this);
        backCameraId = simpleCameraManager.getBackCameraId();
        simpleCameraManager.streamCameraToTexture(backCameraId, cameraOutput.getHolder().getSurface());

        // Setup Rubiks Color buttons
        selectedRubiksColors = new HashMap<>();
        selectedRubiksColors.put(RubiksColor.RED, (FloatingActionButton) findViewById(R.id.color_picker_selection_red));
        selectedRubiksColors.put(RubiksColor.GREEN, (FloatingActionButton) findViewById(R.id.color_picker_selection_green));
        selectedRubiksColors.put(RubiksColor.BLUE, (FloatingActionButton) findViewById(R.id.color_picker_selection_blue));
        selectedRubiksColors.put(RubiksColor.YELLOW, (FloatingActionButton) findViewById(R.id.color_picker_selection_yellow));
        selectedRubiksColors.put(RubiksColor.ORANGE, (FloatingActionButton) findViewById(R.id.color_picker_selection_orange));
        selectedRubiksColors.put(RubiksColor.WHITE, (FloatingActionButton) findViewById(R.id.color_picker_selection_white));
        setSelectedColorButton(selectedRubiksColors.get(RubiksColor.RED));

        for(HashMap.Entry<RubiksColor, FloatingActionButton> entry : selectedRubiksColors.entrySet()) {
            final FloatingActionButton fab = entry.getValue();

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setSelectedColorButton(fab);
                }
            });
        }

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

    /**
     * Selects the rubiks color fab passed in
     * @param fab The Floating Action Button to set as active
     */
    private void setSelectedColorButton(FloatingActionButton fab) {
        FloatingActionButton oldFab = activeRubiksColorButton;
        activeRubiksColorButton = fab;

        // TODO: Fix clipping from animation
        if (oldFab != null) {
            oldFab.animate().translationX(0f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(100);
            oldFab.animate().scaleX(1f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(100);
            oldFab.animate().scaleY(1f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(100);
        }

        fab.animate().translationX(-50f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(100);
        fab.animate().scaleX(1.2f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(100);
        fab.animate().scaleY(1.2f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(100);

        referenceColorIndicator.setColorFilter(fab.getBackgroundTintList().getDefaultColor());
    }
}