package ie.aaronmeaney.solvecubed;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import ie.aaronmeaney.utils.IntentUtilities;

/**
 * Splash Screen and Main Menu for the app.
 */
public class MainActivity extends Activity {

    // Buttons
    private Button btnStart;
    private Button btnPreferences;

    // Declare reference to this activity for use by anonymous classes
    private Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Assign this activity
        thisActivity = this;

        // Setup the Preference Manager to set the default values from the preferences XML resource.
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        // Intentional UI blocking code to simulate arbitrary splash screen
        pauseOnSplashScreenIfEnabled();

        // Inflate the view
        setContentView(R.layout.activity_main);


        /*
         * Post view inflation
         */

        // Get references to UI elements
        btnStart = findViewById(R.id.main_start_button);
        btnPreferences = findViewById(R.id.main_preferences_button);

        // Setup button onClick listeners
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtilities.StartActivity(thisActivity, PalettePickerActivity.class);
            }
        });

        btnPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtilities.StartActivity(thisActivity, PrefsActivity.class);
            }
        });
    }

    /**
     * Sleeps on the main thread if the splash screen is enabled in Shared Preferences.
     * When used before layout inflation, gives the illusion that the app is still loading.
     */
    private void pauseOnSplashScreenIfEnabled() {
        // Get the 'isSplashEnabled' setting from Shared Preferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isSplashEnabled = prefs.getBoolean(getString(R.string.pref_splash_enabled_key), true);

        // Sleep
        if (isSplashEnabled)
        {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.print(e);
                System.exit(1);
            }
        }
    }
}
