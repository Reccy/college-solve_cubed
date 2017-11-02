package ie.aaronmeaney.solvecubed;

import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * AppCompatActivity with pre-configured menu for the app
 */
public abstract class SolveCubedAppCompatActivity extends AppCompatActivity {
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
        Toast.makeText(this, R.string.menu_action_placeholder, Toast.LENGTH_LONG).show();
    }
}