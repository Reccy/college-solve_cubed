package ie.aaronmeaney.solvecubed;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ie.aaronmeaney.utils.IntentUtilities;

/**
 * Displays the solution for solving the Rubik's cube configured by the previous activities.
 */
public class CubeSolverActivity extends SolveCubedAppCompatActivity {

    // Buttons
    private Button btnPrevious;
    private Button btnNext;

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);

        setHelpDialogue(getResources().getString(R.string.cube_solver_help));

        // Inflate the view
        setContentView(R.layout.activity_cube_solver);

        /*
         * Post view inflation
         */

        // Get references to button UI elements
        btnPrevious = findViewById(R.id.cube_solver_btn_previous);
        btnNext = findViewById(R.id.cube_solver_btn_next);

        // Setup button onClick listeners
        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtilities.StartActivityAndClearBackstack(thisActivity, MainActivity.class);
            }
        });
    }
}
