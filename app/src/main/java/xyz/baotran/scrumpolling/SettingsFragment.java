package xyz.baotran.scrumpolling;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.Toast;

/**
 * Created by bao on 8/27/16.
 */
public class SettingsFragment extends PreferenceFragment {

    long timeOfLastTap;
    long timeOfLastSuccessfulTaps;
    final int BUFFER_TIME = 500;
    final int DOUBLE_TAPS_SPEED = 300;
    int countOfNewValuesToBeAdded;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences.xml from an XML resource
        addPreferencesFromResource(R.xml.preferences);

        timeOfLastTap = 0;
        timeOfLastSuccessfulTaps = 0;
        countOfNewValuesToBeAdded = 0;




        // --- Add New Voting Value Preference ---
        final Preference addNewValuePref = findPreference("addNewValue");
        addNewValuePref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                boolean canRegisterTapsAgain = System.currentTimeMillis() > (timeOfLastSuccessfulTaps + BUFFER_TIME); // 500ms buffer before a new value can be added again
                boolean isItDoubleTaps = System.currentTimeMillis() - timeOfLastTap < DOUBLE_TAPS_SPEED; // Check if single or double taps

                // Implementation of a double tap
                if (isItDoubleTaps && canRegisterTapsAgain){
                    countOfNewValuesToBeAdded++;

                    // Update preference with value
                    SharedPreferences.Editor preferencesEditor = getActivity()
                            .getSharedPreferences("dynamic-prefs", Context.MODE_PRIVATE)
                            .edit()
                            .putInt("addNewValue", countOfNewValuesToBeAdded);
                    preferencesEditor.apply();

                    Toast.makeText(getActivity(), "New voting value added", Toast.LENGTH_SHORT).show();
                    timeOfLastSuccessfulTaps = System.currentTimeMillis();
                }

                timeOfLastTap = System.currentTimeMillis();
                return false;
            }
        });
    }
}
