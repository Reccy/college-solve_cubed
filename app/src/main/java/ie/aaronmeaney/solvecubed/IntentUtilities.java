package ie.aaronmeaney.solvecubed;

import android.app.Activity;
import android.content.Intent;

/**
 * Provides convenience methods regarding Intent and Activity Management.
 */
final class IntentUtilities {

    /**
     * Creates a new intent for the newActivity and starts that activity.
     * @param thisActivity The activity the method is being called from.
     * @param newActivity  The class of the activity to start.
     * @return The intent created for the newActivity.
     */
    static Intent StartActivity(Activity thisActivity, Class newActivity) {
        Intent intent = new Intent(thisActivity, newActivity);
        thisActivity.startActivity(intent);
        return intent;
    }
}
