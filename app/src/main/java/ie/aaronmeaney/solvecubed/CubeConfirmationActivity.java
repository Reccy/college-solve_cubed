package ie.aaronmeaney.solvecubed;

import android.os.Bundle;

/**
 * Allows the user to confirm their cube configuration from the CubeInputActivity.
 */
public class CubeConfirmationActivity extends SolveCubedAppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        // Inflate the view
        setContentView(R.layout.activity_cube_confirmation);

        /*
         * Post view inflation
         */

        System.out.println("Inflation complete.");
    }
}
