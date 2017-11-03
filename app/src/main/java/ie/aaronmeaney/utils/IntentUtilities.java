package ie.aaronmeaney.utils;

import android.app.Activity;
import android.content.Intent;

/**
 * Provides convenience methods regarding Intent and Activity Management.
 */
public final class IntentUtilities {

    /**
     * Creates a new intent for the newActivity and starts that activity.
     * @param thisActivity The activity the method is being called from.
     * @param newActivity  The class of the activity to start.
     * @return The intent created for the newActivity.
     */
    public static Intent StartActivity(Activity thisActivity, Class newActivity) {
        Intent intent = new Intent(thisActivity, newActivity);
        thisActivity.startActivity(intent);
        return intent;
    }

    /**
     * Creates a new intent for the newActivity and starts that activity. Also clears the backstack.
     * @param thisActivity The activity the method is being called from.
     * @param newActivity  The class of the activity to start.
     * @return The intent created for the newActivity.
     */
    public static Intent StartActivityAndClearBackstack(Activity thisActivity, Class newActivity) {
        Intent intent = new Intent(thisActivity, newActivity);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        thisActivity.startActivity(intent);
        return intent;
    }
}
