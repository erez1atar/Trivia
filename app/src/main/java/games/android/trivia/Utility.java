package games.android.trivia;

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
}
