package games.android.trivia.Stages;

/**
 * Created by Erez on 05/10/2017.
 */

public class Stage {
    private int id;
    private int min;
    private int max;
    public Stage(int id, int min, int max){
        this.id = id;
        this.max = max;
        this.min = min;
    }

    public int getId() {
        return id;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }
}
