package games.android.trivia.Stages;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import games.android.trivia.Questions.QuestionBank;
import games.android.trivia.R;

/**
 * Created by Erez on 04/10/2017.
 */

public class StagesManager
{
    private HashMap<Integer,Stage> stages;
    private Stage currentStage = null;
    private StagesListener stagesListener = null;
    private StagesData stagesData = null;

    public StagesManager() {
        this.stagesData = new StagesData();
        this.initStages();
    }

    public void setStagesListener(StagesListener stagesListener) {
        this.stagesListener = stagesListener;
    }

    public int getStageBackGround(int stage) {
        if(stage <= 2) {
            return R.drawable.stage_background_red;
        }
        if(stage <= 5) {
            return R.drawable.stage_background_grey;
        }
        return R.drawable.stage_background_blue;
    }


    public void onGameStart() {
        this.currentStage = stages.get(1);
    }

    public int getMinInRange() {
        return currentStage.getMin();
    }

    public int getMaxInRange() {
        return currentStage.getMax();
    }

    public int getStageId(){
        return currentStage.getId();
    }

    private void initStages() {
        stages = this.stagesData.getStages();
    }

    public QuestionBank.QuestionDifficulty getDiffculty() {
        return currentStage.getDifficulty();
    }
    public void onQuestionNumChanged(int questionNum){
        boolean isStageChanged = false;
        int lastMax = this.currentStage.getMax();
        Log.d("questionNum" , "" + questionNum);
        Log.d("stageId" , "" + stagesData.getStageIdFromQuestionNum(questionNum));
        this.currentStage = stages.get(stagesData.getStageIdFromQuestionNum(questionNum));
        isStageChanged = (lastMax != this.currentStage.getMax());
        if(isStageChanged){
            this.stagesListener.onStageChanged();
        }
    }

    public interface StagesListener {
        void onStageChanged();
    }

    public boolean isFirstQuestionInStage(int qustionNum) {
        return this.stagesData.getIsFirstQuestionInStage(qustionNum);
    }
}
