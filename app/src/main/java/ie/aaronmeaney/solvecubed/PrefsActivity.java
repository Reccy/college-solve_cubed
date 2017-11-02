package ie.aaronmeaney.solvecubed;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Loads the Preference Fragment to replace the screen content with the preferences XML layout.
 */
public class PrefsActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.preferences_activity_title);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new PrefsFragment())
                .commit();
    }
}
