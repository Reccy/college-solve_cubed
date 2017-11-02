package ie.aaronmeaney.solvecubed;

import android.os.Bundle;

/**
 * Allows the user to confirm the palette they have chosen in the PalettePickerActivity
 */
public class PaletteConfirmationActivity extends SolveCubedAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette_confirmation);
    }
}
