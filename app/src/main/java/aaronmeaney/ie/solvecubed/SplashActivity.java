package aaronmeaney.ie.solvecubed;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Intentional UI blocking code to simulate arbitrary splash screen, only runs in release mode
        if (!BuildConfig.DEBUG)
        {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.print(e);
                System.exit(1);
            }
        }

        setContentView(R.layout.activity_splash);
    }
}
