package xyz.baotran.scrumpolling.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import xyz.baotran.scrumpolling.helpers.Fibonacci;
import xyz.baotran.scrumpolling.helpers.FileModifications;
import xyz.baotran.scrumpolling.R;

public class MainActivity extends AppCompatActivity {

    TextView pollValueTextView;

    int lastXLocation;
    int currentArrayIndex;
    int scrollSensitivity;
    boolean isCardVisible;
    String pollValueAsStr;
    Fibonacci fibArray;

    final String QUESTION_MARK = "?";
    final String VISIBLE_MODE_STRING = "Card Visible";
    final String HIDDEN_MODE_STRING = "Card Hidden";
    final String CONFIG_FILE_NAME = "configs.txt";

    SharedPreferences prefs;

    private GestureDetector gestureDetector;

    FileModifications fileMods;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        // Hide the Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){ actionBar.hide(); }

        // Read from xml and set preferences default values
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        loadData();

        pollValueTextView = (TextView) findViewById(R.id.pollNumberTextView);
        currentArrayIndex = 0;    // The current chosen element
        pollValueAsStr = fibArray.getValueAt(currentArrayIndex);

        isCardVisible = false;

        // Double tap listener to toggle between show/hide card mode
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                isCardVisible = !isCardVisible;
                Toast.makeText(MainActivity.this, getCardVisibilityModeStr(isCardVisible), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    /**
     * Load any existing data
     */
    private void loadData(){
        fileMods = new FileModifications(CONFIG_FILE_NAME, this);
        String readData = fileMods.read();

        if (readData.length()>0){
            ArrayList<Integer> arrayOfInts = new ArrayList<>(
                    Arrays.asList(parseStringToIntArray(readData)));
            fibArray = new Fibonacci(arrayOfInts);
        }else{
            fibArray = new Fibonacci();
        }
    }
// ----------- EVENTS -----------------------------
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (gestureDetector.onTouchEvent(event))
            return true;

        int currentXLocation = (int) event.getX();

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                lastXLocation = currentXLocation;
                pollValueTextView.setText(pollValueAsStr);
                break;
            case MotionEvent.ACTION_MOVE:
                int locationDifference = currentXLocation - lastXLocation;

                if (isPositive(locationDifference) && locationDifference > scrollSensitivity){ // Move right

                    if (!lastElement()) { currentArrayIndex++; }

                    pollValueAsStr = fibArray.getValueAt(currentArrayIndex);
                    pollValueTextView.setText(pollValueAsStr);

                    lastXLocation = currentXLocation;
                }else if (!isPositive(locationDifference) && (locationDifference * -1) > scrollSensitivity){

                    if (!firstElement()) { currentArrayIndex--; }

                    pollValueAsStr = fibArray.getValueAt(currentArrayIndex);
                    pollValueTextView.setText(pollValueAsStr);

                    lastXLocation = currentXLocation;
                }

                break;
            case MotionEvent.ACTION_UP:
                String text = isCardVisible ? pollValueAsStr : QUESTION_MARK;
                pollValueTextView.setText(text);

                break;
        }
        return true;
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    /**
     * This method is invoked when the current activity is active, or when it first start up
     * For instance from the Settings activity back to the Main activity.
     */
    @Override
    public void onResume(){
        super.onResume();

        // Get settings changes
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Field updated for events
        scrollSensitivity = Integer.parseInt(prefs.getString("sensitivity_preference", "30"));

        SharedPreferences dynamicPrefs = getSharedPreferences("dynamic-prefs", MODE_PRIVATE);

        //On preference change - add any new value
        int numberOfNewValues = dynamicPrefs.getInt("addNewValue", 0);
        for (int i = 0; i < numberOfNewValues; i++) {
            fibArray.add();
        }
        dynamicPrefs.edit().putInt("addNewValue", 0).apply();

        //On preference change - remove any old value
        int countOfValuesToRemove = dynamicPrefs.getInt("removeLastValue", 0);
        for (int i = 0; i < countOfValuesToRemove; i++){
            // Keep at least 1 value remaining
            if (fibArray.size() > 1){
                fibArray.remove();
            }

        }
        // Check for out of bound index after removal
        // Smaller index to jump to if the larger indexes was removed
        if (fibArray.size()-1 < currentArrayIndex) {
            currentArrayIndex = fibArray.size()-1;
        }

        dynamicPrefs.edit().putInt("removeLastValue", 0).apply();

        //Prompt if changes were made to the list
        if (numberOfNewValues > 0 || countOfValuesToRemove > 0){
            String listAsString = fibArray.toString();
            Toast.makeText(this, "New List: [" + listAsString +"]", Toast.LENGTH_SHORT).show();
            //Save new list
            fileMods.write(listAsString);
        }

    }

    private Integer[] parseStringToIntArray(String str){
        String[] strValue = str.split(",");
        Integer[] ints = new Integer[strValue.length];
        for(int i=0; i < strValue.length; i++)
        {
            try {
                ints[i] = Integer.parseInt(strValue[i]);
            } catch (NumberFormatException nfe) {}
        }

        return ints;
    }

    private boolean lastElement(){
        return currentArrayIndex == fibArray.size()-1;
    }
    private boolean firstElement(){
        return currentArrayIndex == 0;
    }

    // --- Getters & Setters ---

    private boolean isPositive(int number){
        return number >= 0;
    }

    private String getCardVisibilityModeStr(boolean cardVisiblity){
        return isCardVisible ? VISIBLE_MODE_STRING : HIDDEN_MODE_STRING;
    }

    public void openSettingsActivity(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
