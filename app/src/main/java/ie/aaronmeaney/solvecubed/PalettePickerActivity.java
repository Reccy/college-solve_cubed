package ie.aaronmeaney.solvecubed;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import java.util.HashMap;
import java.util.LinkedHashMap;

import ie.aaronmeaney.rubikscube.RubiksColor;
import ie.aaronmeaney.utils.IntentUtilities;
import ie.aaronmeaney.utils.SimpleCameraManager;

/**
 * Allows the user to choose the reference color palette with their camera.
 */
public class PalettePickerActivity extends SolveCubedAppCompatActivity {

    // Output SurfaceView for the camera
    private SurfaceView cameraSurfaceView;

    // Wrapper for camera2 API
    private SimpleCameraManager simpleCameraManager;

    // ID of the back camera
    private String backCameraId;

    // Image representing the reference color
    private ImageView referenceColorImage;

    // FAB to capture the color the camera is looking at
    private FloatingActionButton captureColorFAB;

    // FABs for the selected reference color
    private LinkedHashMap<RubiksColor, FloatingActionButton> rubiksColorsToFabsHashMap;

    // Colors for the actual color
    private LinkedHashMap<RubiksColor, Color> rubiksColorToRealColor;

    // The currently selected reference fab
    private FloatingActionButton selectedReferenceColorFAB;

    // The currently selected reference color
    private RubiksColor selectedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHelpDialogue(getResources().getString(R.string.palette_picker_help));

        // Inflate the view
        setContentView(R.layout.activity_palette_picker);

        /*
         * Post view inflation
         */

        // Get references to UI elements
        cameraSurfaceView = findViewById(R.id.palette_picker_surface_view_camera);
        captureColorFAB = findViewById(R.id.palette_picker_fab_capture_color);
        referenceColorImage = findViewById(R.id.palette_picker_image_color_reference);

        // Make Camera render to the SurfaceView
        simpleCameraManager = new SimpleCameraManager(this);
        backCameraId = simpleCameraManager.getBackCameraId();
        simpleCameraManager.streamCameraToTexture(backCameraId, cameraSurfaceView.getHolder().getSurface());

        // Assign Rubik's Color to each color FAB
        rubiksColorsToFabsHashMap = new LinkedHashMap<>();
        rubiksColorsToFabsHashMap.put(RubiksColor.RED, (FloatingActionButton) findViewById(R.id.palette_picker_fab_color_red));
        rubiksColorsToFabsHashMap.put(RubiksColor.GREEN, (FloatingActionButton) findViewById(R.id.palette_picker_fab_color_green));
        rubiksColorsToFabsHashMap.put(RubiksColor.BLUE, (FloatingActionButton) findViewById(R.id.palette_picker_fab_color_blue));
        rubiksColorsToFabsHashMap.put(RubiksColor.YELLOW, (FloatingActionButton) findViewById(R.id.palette_picker_fab_color_yellow));
        rubiksColorsToFabsHashMap.put(RubiksColor.ORANGE, (FloatingActionButton) findViewById(R.id.palette_picker_fab_color_orange));
        rubiksColorsToFabsHashMap.put(RubiksColor.WHITE, (FloatingActionButton) findViewById(R.id.palette_picker_fab_color_white));
        selectColorFab(rubiksColorsToFabsHashMap.get(RubiksColor.RED));

        // Instantiate Rubik's Color to real color
        rubiksColorToRealColor = new LinkedHashMap<>();
        rubiksColorToRealColor.put(RubiksColor.RED, null);
        rubiksColorToRealColor.put(RubiksColor.GREEN, null);
        rubiksColorToRealColor.put(RubiksColor.BLUE, null);
        rubiksColorToRealColor.put(RubiksColor.YELLOW, null);
        rubiksColorToRealColor.put(RubiksColor.ORANGE, null);
        rubiksColorToRealColor.put(RubiksColor.WHITE, null);

        // Setup onClickListener for each color FAB
        for(HashMap.Entry<RubiksColor, FloatingActionButton> entry : rubiksColorsToFabsHashMap.entrySet()) {
            final FloatingActionButton fab = entry.getValue();

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectColorFab(fab);
                }
            });
        }

        // Setup onClickListener for the capture FAB, goes to the next scene if the colors are selected
        captureColorFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Set the selected color
                rubiksColorToRealColor.put(selectedColor, simpleCameraManager.getCenterColorFromCamera());

                // Find the next unset color and select it
                for (HashMap.Entry<RubiksColor, Color> colorEntry : rubiksColorToRealColor.entrySet()) {
                    RubiksColor rubiksReferenceColor = colorEntry.getKey();
                    Color rubiksActualColor = colorEntry.getValue();

                    System.out.println("CHECKING -----> " + rubiksReferenceColor + " || ACTUAL -----> " + rubiksActualColor);

                    if (rubiksActualColor == null) {
                        selectColorFab(rubiksColorsToFabsHashMap.get(rubiksReferenceColor));
                        return;
                    }
                }

                // If all colors have been set, go to the next activity
                IntentUtilities.StartActivity(thisActivity, PaletteConfirmationActivity.class);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Make Camera render to the SurfaceView
        simpleCameraManager.streamCameraToTexture(backCameraId, cameraSurfaceView.getHolder().getSurface());
    }

    /**
     * Selects the Rubik's fab as the new reference color and runs an animation to display this to the user.
     * @param fab The Floating Action Button to set as the reference color
     */
    private void selectColorFab(FloatingActionButton fab) {
        FloatingActionButton oldFab = selectedReferenceColorFAB;
        selectedReferenceColorFAB = fab;

        // Set the selectedColor enum
        for(HashMap.Entry<RubiksColor, FloatingActionButton> entry : rubiksColorsToFabsHashMap.entrySet()) {
            final RubiksColor tempColor = entry.getKey();
            final FloatingActionButton tempFab = entry.getValue();

            if (fab.equals(tempFab)) {
                selectedColor = tempColor;

                System.out.println("SELECTED -----> " + selectedColor);
                break;
            }
        }

        if (oldFab != null) {
            oldFab.animate().translationX(0f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(100);
            oldFab.animate().scaleX(1f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(100);
            oldFab.animate().scaleY(1f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(100);
        }

        fab.animate().translationX(-50f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(100);
        fab.animate().scaleX(1.2f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(100);
        fab.animate().scaleY(1.2f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(100);

        referenceColorImage.setColorFilter(fab.getBackgroundTintList().getDefaultColor());
    }
}
