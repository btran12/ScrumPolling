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
    // 1. Be able to remove numbers
    // When numbers has 3 digits or greater, rotate screen to landscape.
    // 2. Use different polling system (i.e. Tshirt's sizes, bucket, ...)
    // 3. Be able to add their own polling system (Whatever they desires <T>)
    //

    TextView pollNumberTextView;

    int lastXLocation;
    int fibonacciArrayIndex;
    int scrollSensitivity;
    boolean isCardVisible;
    String pollNumberText;
    Fibonacci fibArray;

    final String QUESTION_MARK = "?";
    final String VISIBLE_MODE_STRING = "Card Visible";
    final String HIDDEN_MODE_STRING = "Card Hidden";

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
        fibArray = new Fibonacci();
        fibonacciArrayIndex = 0;    // Starting index
        pollNumberText= String.valueOf(fibArray.getValueAt(fibonacciArrayIndex));

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

                    pollNumberText = String.valueOf(fibArray.getValueAt(fibonacciArrayIndex));
                    pollNumberTextView.setText(pollNumberText);

                    lastXLocation = currentXLocation;
                }else if (!isPositive(locationDifference) && (locationDifference * -1) > scrollSensitivity){

                    if (!firstElement()) { fibonacciArrayIndex--; }

                    pollNumberText = String.valueOf(fibArray.getValueAt(fibonacciArrayIndex));
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
        scrollSensitivity = Integer.parseInt(prefs.getString("sensitivity_preference", "30"));

        SharedPreferences dynamicPrefs = getSharedPreferences("dynamic-prefs", MODE_PRIVATE);
        int numberOfNewValues = dynamicPrefs.getInt("addNewValue", 0);
        for (int i = 0; i < numberOfNewValues; i++) {
            fibArray.add();
        }

        if (numberOfNewValues > 0){
            Toast.makeText(this, "New List: " + fibArray.toString(), Toast.LENGTH_SHORT).show();
        }
        dynamicPrefs.edit().putInt("addNewValue", 0).apply();
    }

    private boolean lastElement(){
        return fibonacciArrayIndex == fibArray.size()-1;
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

    public void openSettingsActivity(View view){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
