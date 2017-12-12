package ie.aaronmeaney.utils;

import android.app.Activity;
import android.content.Intent;

import java.io.Serializable;
import java.util.LinkedHashMap;

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

    /**
     * Creates a new intent for the newActivity and starts that activity.
     * Allows objects to be passed to the new activity.
     * @param thisActivity The activity the method is being called from.
     * @param newActivity  The class of the activity to start.
     * @param extraName The name of the serializable object that was passed
     * @param extraLinkedHashMap The object to be passed
     * @return The intent created for the newActivity.
     */
    public static Intent StartActivityWithLinkedHashMap(Activity thisActivity, Class newActivity, String extraName, LinkedHashMap extraLinkedHashMap) {
        Intent intent = new Intent(thisActivity, newActivity);
        intent.putExtra(extraName, new LinkedHashMapSerializable(extraLinkedHashMap));
        thisActivity.startActivity(intent);
        return intent;
    }

    /**
     * Returns the extra LinkedHashMap stored in the Intent.
     * @param intent The intent containing the LinkedHashMap extra.
     * @param extraName The name of the LinkedHashMap.
     * @return The LinkedHashMap
     */
    public static <T,E> LinkedHashMap<T,E> GetExtraLinkedHashMap(Intent intent, String extraName) {
        LinkedHashMapSerializable lhm = (LinkedHashMapSerializable)intent.getExtras().getSerializable(extraName);
        return lhm.get();
    }
}
