package xyz.baotran.scrumpolling;

import android.content.Intent;
import android.os.Bundle;
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
    final int SWIPE_DIFFERENCE = 10; // Change poll number for every n DPI
    String pollNumberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Hide the Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){ actionBar.hide(); }

        pollNumberTextView = (TextView) findViewById(R.id.pollNumberTextView);
        fibonacciArray = new int[]{1,2,3,5,8,13,21,34,55};
        fibonacciArrayIndex = 0;
        pollNumberText= String.valueOf(1);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int currentXLocation = (int) event.getX();
        // int timeInSeconds = (int) (System.currentTimeMillis() / 1000) % 60 ;

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                lastXLocation = currentXLocation;
                pollNumberTextView.setText(pollNumberText);
                break;
            case MotionEvent.ACTION_MOVE:
                int locationDifference = currentXLocation - lastXLocation;

                if (isPositive(locationDifference) && locationDifference > SWIPE_DIFFERENCE){ // Move right

                    if (fibonacciArrayIndex < fibonacciArray.length-1) { fibonacciArrayIndex++; }

                    pollNumberText = String.valueOf(fibonacciArray[fibonacciArrayIndex]);
                    pollNumberTextView.setText(pollNumberText);

                    lastXLocation = currentXLocation;
                }else if (!isPositive(locationDifference) && (locationDifference * -1) > SWIPE_DIFFERENCE){

                    if (fibonacciArrayIndex > 0) { fibonacciArrayIndex--; }

                    pollNumberText = String.valueOf(fibonacciArray[fibonacciArrayIndex]);
                    pollNumberTextView.setText(pollNumberText);

                    lastXLocation = currentXLocation;
                }

                break;
            case MotionEvent.ACTION_UP:
                pollNumberTextView.setText("?");
                break;
        }
        return true;
    }

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
