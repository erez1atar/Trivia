package games.android.trivia.HighScores;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by LENOVO on 15/02/2016.
 */
public class HallOfFameBuilder
{
    private static ArrayList<WinnerData> list;

    public static ArrayList<WinnerData> getWinners()
    {
        list = new ArrayList<>(20);
        for(int i = 0 ; i < 20 ; i ++)
        {
            list.add(new WinnerData(i, "" + i));
        }
        return list;
    }

}
