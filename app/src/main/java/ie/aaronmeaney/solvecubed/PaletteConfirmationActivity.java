package ie.aaronmeaney.solvecubed;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.LinkedHashMap;

import ie.aaronmeaney.rubikscube.RubiksColor;
import ie.aaronmeaney.utils.IntentUtilities;

/**
 * Allows the user to confirm the palette they have chosen in the PalettePickerActivity
 */
public class PaletteConfirmationActivity extends SolveCubedAppCompatActivity {

    // Buttons
    private Button btnCancel;
    private Button btnConfirm;

    // View icons
    private ImageView redImage;
    private ImageView greenImage;
    private ImageView blueImage;
    private ImageView yellowImage;
    private ImageView orangeImage;
    private ImageView whiteImage;

    // HashMap of chosen colours
    private HashMap<RubiksColor, Integer> rubiksColorToRealColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHelpDialogue(getResources().getString(R.string.palette_confirmation_help));

        // Inflate the view
        setContentView(R.layout.activity_palette_confirmation);

        /*
         * Post view inflation
         */

        // Get the chosen colours
        rubiksColorToRealColor = (HashMap<RubiksColor, Integer>)getIntent().getSerializableExtra(getResources().getString(R.string.palette_picker_hash_map));

        // Get references to UI elements
        btnCancel = findViewById(R.id.palette_confirmation_btn_cancel);
        btnConfirm = findViewById(R.id.palette_confirmation_btn_confirm);

        redImage = findViewById(R.id.palette_confirmation_image_red);
        greenImage = findViewById(R.id.palette_confirmation_image_green);
        blueImage = findViewById(R.id.palette_confirmation_image_blue);
        yellowImage = findViewById(R.id.palette_confirmation_image_yellow);
        orangeImage = findViewById(R.id.palette_confirmation_image_orange);
        whiteImage = findViewById(R.id.palette_confirmation_image_white);

        // Set the UI elements colours
        redImage.setColorFilter(rubiksColorToRealColor.get(RubiksColor.RED).hashCode());
        greenImage.setColorFilter(rubiksColorToRealColor.get(RubiksColor.GREEN).hashCode());
        blueImage.setColorFilter(rubiksColorToRealColor.get(RubiksColor.BLUE).hashCode());
        yellowImage.setColorFilter(rubiksColorToRealColor.get(RubiksColor.YELLOW).hashCode());
        orangeImage.setColorFilter(rubiksColorToRealColor.get(RubiksColor.ORANGE).hashCode());
        whiteImage.setColorFilter(rubiksColorToRealColor.get(RubiksColor.WHITE).hashCode());

        // Setup button onClick listeners
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtilities.StartActivity(thisActivity, CubeInputActivity.class);
            }
        });
    }
}
