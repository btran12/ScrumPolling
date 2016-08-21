package xyz.baotran.scrumpolling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.NumberPicker;

/**
 * Created by bao on 8/2/16.
 */
public class SettingsActivity extends AppCompatActivity{
    NumberPicker sensitivityPicker;
    Intent backIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Intent intent = getIntent();
        sensitivityPicker = (NumberPicker) findViewById(R.id.sensitivityPicker);
        sensitivityPicker.setMinValue(1);
        sensitivityPicker.setMaxValue(3);
        int sensitivity = intent.getIntExtra("scroll_sensitivity", 30);

        sensitivityPicker.setValue(convertSPSensitivityToSmallNumber(sensitivity));

        sensitivityPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra("scroll_sensitivity_result",convertSmallNumbersToSPSensitivity(newVal));
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }

    private int convertSPSensitivityToSmallNumber(int value){
        switch(value){
            case 30:
                return 1;
            case 22:
                return 2;
            case 15:
                return 3;
            default:
                return 1;
        }
    }

    private int convertSmallNumbersToSPSensitivity(int value){
        switch(value){
            case 1:
                return 30;
            case 2:
                return 22;
            case 3:
                return 15;
            default:
                return 30;
        }
    }
}
