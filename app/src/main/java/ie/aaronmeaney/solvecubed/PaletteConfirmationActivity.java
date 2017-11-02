package ie.aaronmeaney.solvecubed;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ie.aaronmeaney.utils.IntentUtilities;

/**
 * Allows the user to confirm the palette they have chosen in the PalettePickerActivity
 */
public class PaletteConfirmationActivity extends SolveCubedAppCompatActivity {

    // Buttons
    private Button btnCancel;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the view
        setContentView(R.layout.activity_palette_confirmation);

        /*
         * Post view inflation
         */

        // Get references to UI elements
        btnCancel = findViewById(R.id.palette_confirmation_btn_cancel);
        btnConfirm = findViewById(R.id.palette_confirmation_btn_confirm);

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
