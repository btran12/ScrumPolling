package xyz.baotran.scrumpolling;

/**
 * Created by bao on 8/21/2016.
 */
public class Settings {

    private int scrollSensitivity;
    private static final int DEFAULT_SENSITIVITY = 30;

    public void Settings(){
        scrollSensitivity = DEFAULT_SENSITIVITY;

    }


    public int getScrollSensitivity() {
        return scrollSensitivity;
    }

    public void setScrollSensitivity(int sensitivity){
        scrollSensitivity = sensitivity;
    }
}
