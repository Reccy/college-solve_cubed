package ie.aaronmeaney.solvecubed;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Locale;

import ie.aaronmeaney.solvecubed.R;

/**
 * AppCompatActivity with pre-configured menu for the app
 */
public abstract class SolveCubedAppCompatActivity extends AppCompatActivity {

    /**
     * The text to speech engine used for the help menu
     */
    private TextToSpeech speechEngine;

    /**
     * The help dialogue written by the help menu
     */
    private String helpDialogue;

    /**
     * Reference to this activity used by anonymous classes
     */
    protected Activity thisActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thisActivity = this;
        helpDialogue = getResources().getString(R.string.menu_action_placeholder);

        speechEngine = new TextToSpeech(thisActivity, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    speechEngine.setLanguage(Locale.UK);
                }
            }
        });
    }

    /**
     *  Setup the ActionBar menu buttons
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.help, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Assign actions to the ActionBar menu buttons
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                onHelpMenuActionSelected();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Class to be overridden in concrete classes to handle the Help action.
     */
    protected void onHelpMenuActionSelected() {
        Toast.makeText(this, helpDialogue, Toast.LENGTH_LONG).show();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isSpeechEnabled = prefs.getBoolean(getString(R.string.pref_text_speech_enabled_key), false);

        if(isSpeechEnabled) {
            speak(helpDialogue);
        }
    }

    /**
     * Uses the text-to-speech engine to speak the passed in dialogue
     * @param dialogue What to speak
     */
    private void speak(String dialogue) {
        speechEngine.speak(dialogue, TextToSpeech.QUEUE_FLUSH, null);
    }
}
