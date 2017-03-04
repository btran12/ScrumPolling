package xyz.baotran.scrumpolling.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import xyz.baotran.scrumpolling.fragments.SettingsFragment;

/**
 * Created by bao on 8/27/16.
 */
public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }
}