package aaronmeaney.ie.solvecubed;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

public class SplashActivity extends Activity {

    // Declare UI elements
    Button btnStart;
    Button btnSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Intentional UI blocking code to simulate arbitrary splash screen, only runs in release mode
        pauseOnSplashScreen();

        // Inflate the view
        setContentView(R.layout.activity_splash);

        // Get references to UI elements
        btnStart = findViewById(R.id.main_start_button);
        btnSettings = findViewById(R.id.main_settings_button);

        // Setup Listeners
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCameraCapture();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSettings();
            }
        });
    }

    /**
     * Transitions to the CameraCapture activity.
     */
    private void gotoCameraCapture() {
        Intent intentCameraCapture = new Intent(this, CameraCaptureActivity.class);
        startActivity(intentCameraCapture);
    }

    /**
     * Opens the Settings fragment.
     */
    private void gotoSettings() {
        throw new java.lang.UnsupportedOperationException();
    }

    /**
     * Sleeps on the main thread if the splash screen is enabled in the SharedPreferences.
     */
    private void pauseOnSplashScreen() {
        // Get the 'isSplashEnabled' setting from Shared Preferences
        SharedPreferences prefs = getSharedPreferences(getString(R.string._pref_name), MODE_PRIVATE);
        boolean isSplashEnabled = prefs.getBoolean(getString(R.string._pref_splash_enabled), true);

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
