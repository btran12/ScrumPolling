package xyz.baotran.scrumpolling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView pollNumberTextView;
    int lastXLocation;
    int[] fibonacciArray;
    int fibonacciArrayIndex;
    int scrollSensitivity;
    String pollNumberText;
    final String QUESTION_MARK = "?";
    boolean isActivityActive;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){ actionBar.hide(); }

        isActivityActive = true;

        // Read from xml and set preferences default values
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
//        prefs = PreferenceManager

        pollNumberTextView = (TextView) findViewById(R.id.pollNumberTextView);
        fibonacciArray = new int[]{1,2,3,5,8,13,21,34,55,89};
        fibonacciArrayIndex = 0;    // Starting index
        pollNumberText= String.valueOf(fibonacciArray[fibonacciArrayIndex]);

    }

    @Override
    public void onPause(){
        super.onPause();
        isActivityActive = false;
    }

    @Override
    public void onResume(){
        super.onResume();

        if (!isActivityActive){
            isActivityActive = true;

        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int currentXLocation = (int) event.getX();

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                lastXLocation = currentXLocation;
                pollNumberTextView.setText(pollNumberText);
                scrollSensitivity = 30;
                break;
            case MotionEvent.ACTION_MOVE:
                int locationDifference = currentXLocation - lastXLocation;

                if (isPositive(locationDifference) && locationDifference > scrollSensitivity){ // Move right

                    if (!lastElement()) { fibonacciArrayIndex++; }

                    pollNumberText = String.valueOf(fibonacciArray[fibonacciArrayIndex]);
                    pollNumberTextView.setText(pollNumberText);

                    lastXLocation = currentXLocation;
                }else if (!isPositive(locationDifference) && (locationDifference * -1) > scrollSensitivity){

                    if (!firstElement()) { fibonacciArrayIndex--; }

                    pollNumberText = String.valueOf(fibonacciArray[fibonacciArrayIndex]);
                    pollNumberTextView.setText(pollNumberText);

                    lastXLocation = currentXLocation;
                }

                break;
            case MotionEvent.ACTION_UP:
                pollNumberTextView.setText(QUESTION_MARK);
                break;
        }
        return true;
    }

    // --- Conditionals ---
    public boolean lastElement(){
        return fibonacciArrayIndex == fibonacciArray.length-1;
    }
    public boolean firstElement(){
        return fibonacciArrayIndex == 0;
    }

    // --- Getters & Setters ---

    public boolean isPositive(int number){
        return number >= 0;
    }

    /**
     * Settings button actionlistener
     *
     * @param view
     */
    public void openSettingsActivity(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
