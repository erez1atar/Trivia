package games.android.trivia.HighScores;

import java.io.Serializable;

/**
 * Created by LENOVO on 15/02/2016.
 */
public class WinnerData implements Comparable,Serializable
{
    private int score;
    private String name;

    public WinnerData() {}

    public WinnerData(int score, String name)
    {
        this.name = name;
        this.score = score;

    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "FirebaseWinnerData{name='" + name + "\', score='" + score + "\'}";
    }

    @Override
    public int compareTo(Object another) {
        return this.score - ((WinnerData)another).score;
    }
}
