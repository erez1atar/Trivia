package games.android.trivia;

import android.graphics.Paint;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by Erez on 05/11/2017.
 */

public class Utility {

    static public  String getNumberFormat(int number) {
        String formatStr = NumberFormat.getNumberInstance(Locale.US).format(number);
        return formatStr;
    }

     public static TextWatcher textAutoResizeWatcher(final TextView view, final int MIN_SP, final int MAX_SP) {
         return new TextWatcher() {
             @Override
             public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

             @Override
             public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

             @Override
             public void afterTextChanged(Editable editable) {

                 View parent = (View)(view.getParent());
                 float height = parent.getMeasuredHeight();
                 float sp = dpToSp(height);
                 view.setTextSize(sp);
             }
         };
     }

    private static float pixelsToSp(float px) {
        float scaledDensity = App.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return px/scaledDensity;
    }

    private static float spToPixels(float sp) {
        float scaledDensity = App.getInstance().getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }

    private static int dpToSp(float dp) {
        float px = dpToPx(dp);
        return (int) (px / App.getInstance().getResources().getDisplayMetrics().scaledDensity);
    }

    private static int dpToPx(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, App.getInstance().getResources().getDisplayMetrics());
    }
}
