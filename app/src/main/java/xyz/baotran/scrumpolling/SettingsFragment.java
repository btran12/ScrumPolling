package xyz.baotran.scrumpolling;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by bao on 8/27/16.
 */
public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences.xml from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
