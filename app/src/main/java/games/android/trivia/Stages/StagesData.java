package games.android.trivia.Stages;

import java.util.HashMap;

import games.android.trivia.Questions.QuestionBank;

/**
 * Created by Erez on 05/10/2017.
 */

public class StagesData{

    private final int maxQuestionsInStage = 3;

    public StagesData() {

    }

    public int getStageIdFromQuestionNum(int questionNum) {
        int stageId = (int)Math.ceil(0.5 + (questionNum - 1) / maxQuestionsInStage);
        if(stageId > 10) {
            return 10;
        }
        return stageId;
    }

    public boolean getIsFirstQuestionInStage(int questionNum) {
        return ((questionNum - 1) % 3) == 0;
    }

    HashMap<Integer,Stage> getStages() {
        HashMap<Integer,Stage> stages = new HashMap<Integer,Stage>();
        stages.put(1,new Stage(1, 0, 1000, QuestionBank.QuestionDifficulty.EASY));
        stages.put(2,new Stage(2, 1000, 5000, QuestionBank.QuestionDifficulty.EASY));
        stages.put(3,new Stage(3, 5000, 10000, QuestionBank.QuestionDifficulty.MEDIUM));
        stages.put(4,new Stage(4, 10000, 20000, QuestionBank.QuestionDifficulty.MEDIUM));
        stages.put(5,new Stage(5, 20000, 50000, QuestionBank.QuestionDifficulty.MEDIUM));
        stages.put(6,new Stage(6, 50000, 100000, QuestionBank.QuestionDifficulty.HRAD));
        stages.put(7,new Stage(7, 100000, 200000, QuestionBank.QuestionDifficulty.HRAD));
        stages.put(8,new Stage(8, 200000, 300000, QuestionBank.QuestionDifficulty.HRAD));
        stages.put(9,new Stage(9, 300000, 500000, QuestionBank.QuestionDifficulty.HRAD));
        stages.put(10,new Stage(10, 500000, 1000000, QuestionBank.QuestionDifficulty.HRAD));
        return stages;
    }
}
