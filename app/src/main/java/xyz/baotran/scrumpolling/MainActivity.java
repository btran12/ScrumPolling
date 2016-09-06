package xyz.baotran.scrumpolling;

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

public class MainActivity extends AppCompatActivity {
    // TODO
    // 1. Be able to add more numbers
    // 2. Use different polling system (i.e. Tshirt's sizes, bucket, ...)
    // 3. Be able to add their own polling system
    //

    TextView pollNumberTextView;

    int lastXLocation;
    int[] fibonacciArray;
    int fibonacciArrayIndex;
    int scrollSensitivity;
    boolean isCardVisible;
    String pollNumberText;

    final String QUESTION_MARK = "?";
    final String VISIBLE_MODE_STRING = "Visible Mode";
    final String HIDDEN_MODE_STRING = "Hidden Mode";

    SharedPreferences prefs;

    private GestureDetector gestureDetector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){ actionBar.hide(); }

        // Read from xml and set preferences default values
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        pollNumberTextView = (TextView) findViewById(R.id.pollNumberTextView);
        fibonacciArray = new int[]{1,2,3,5,8,13,21,34,55,89};
        fibonacciArrayIndex = 0;    // Starting index
        pollNumberText= String.valueOf(fibonacciArray[fibonacciArrayIndex]);

        isCardVisible = false;

        // Double tap listener to toggle between show/hide card mode
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                isCardVisible = !isCardVisible;
                Toast.makeText(MainActivity.this, getCardVisibilityMode(isCardVisible), Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();

        // Settings changes
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        scrollSensitivity = Integer.parseInt(prefs.getString("sensitivity_preference", "30"));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (gestureDetector.onTouchEvent(event))
            return true;

        int currentXLocation = (int) event.getX();

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                lastXLocation = currentXLocation;
                pollNumberTextView.setText(pollNumberText);
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
                String text = isCardVisible ? pollNumberText : QUESTION_MARK;
                pollNumberTextView.setText(text);

                break;
        }
        return true;
    }

    // --- Conditionals ---
    private boolean lastElement(){
        return fibonacciArrayIndex == fibonacciArray.length-1;
    }
    private boolean firstElement(){
        return fibonacciArrayIndex == 0;
    }

    // --- Getters & Setters ---

    private boolean isPositive(int number){
        return number >= 0;
    }

    private String getCardVisibilityMode(boolean cardVisiblity){
        return isCardVisible ? VISIBLE_MODE_STRING : HIDDEN_MODE_STRING;
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
