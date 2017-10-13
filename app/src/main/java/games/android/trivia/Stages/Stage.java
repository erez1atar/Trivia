package games.android.trivia.Stages;

import games.android.trivia.Questions.QuestionBank;

/**
 * Created by Erez on 05/10/2017.
 */

public class Stage {
    private int id;
    private int min;
    private int max;
    private QuestionBank.QuestionDifficulty difficulty;

    public Stage(int id, int min, int max, QuestionBank.QuestionDifficulty difficulty){
        this.id = id;
        this.max = max;
        this.min = min;
        this.difficulty = difficulty;
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

    public QuestionBank.QuestionDifficulty getDifficulty() {
        return difficulty;
    }
}
