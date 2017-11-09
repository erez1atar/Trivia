package games.android.trivia;

import android.graphics.Typeface;

/**
 * Created by Erez on 06/10/2017.
 */

public class ResourcesManager {

    public Typeface getNumbersFont() {
        Typeface numbersFont = Typeface.createFromAsset(App.getInstance().getAssets(),"fonts/hebrew.ttf");
        return numbersFont;
    }

    public Typeface getTimerFont() {
        Typeface numbersFont = Typeface.createFromAsset(App.getInstance().getAssets(),"fonts/alarm_clock.ttf");
        return numbersFont;
    }
}
