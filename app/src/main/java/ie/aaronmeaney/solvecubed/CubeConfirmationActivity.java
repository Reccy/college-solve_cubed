package ie.aaronmeaney.solvecubed;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ie.aaronmeaney.utils.IntentUtilities;

/**
 * Allows the user to confirm their cube configuration from the CubeInputActivity.
 */
public class CubeConfirmationActivity extends SolveCubedAppCompatActivity {

    // Buttons
    private Button btnCancel;
    private Button btnConfirm;

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        setHelpDialogue(getResources().getString(R.string.cube_confirmation_help));

        // Inflate the view
        setContentView(R.layout.activity_cube_confirmation);

        /*
         * Post view inflation
         */

        // Get references to button UI elements
        btnCancel = findViewById(R.id.cube_confirmation_btn_cancel);
        btnConfirm = findViewById(R.id.cube_confirmation_btn_confirm);

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
                IntentUtilities.StartActivity(thisActivity, CubeSolverActivity.class);
            }
        });
    }
}
