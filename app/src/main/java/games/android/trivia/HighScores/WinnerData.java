package games.android.trivia.HighScores;

/**
 * Created by LENOVO on 15/02/2016.
 */
public class WinnerData implements Comparable
{
    private String date;
    private int score;
    private String name;

    public WinnerData(String date, int score, String name)
    {
        this.date = date;
        this.name = name;
        this.score = score;

    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public String getDate() {
        return date;
    }

    @Override
    public int compareTo(Object another) {
        return this.score - ((WinnerData)another).score;
    }
}
