package aaronmeaney.ie.solvecubed;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        startMainMenuActivity(5);
    }

    /**
     * Loads the main menu activity after duration 'loadingTimeSeconds' elapses and
     * then finishes the splash screen activity
     */
    private void startMainMenuActivity(int loadingTimeSeconds)
    {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainMenuActivity.class));
                finish();
            }
        }, loadingTimeSeconds * 1000);
    }
}
