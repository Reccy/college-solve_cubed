package ie.aaronmeaney.solvecubed;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import ie.aaronmeaney.solvecubed.R;

/**
 * Generates the layout for the Preferences Activity with the preferences XML layout.
 */
public class PrefsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflates the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}