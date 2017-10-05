package games.android.trivia.Stages;

import java.util.HashMap;

/**
 * Created by Erez on 05/10/2017.
 */

public class StagesData{

    public StagesData() {

    }

    public int getStageIdFromQuestionNum(int questionNum) {
        int stageId = (int)Math.ceil(0.5 + questionNum / 3);
        if(stageId > 10) {
            return 10;
        }
        return stageId;
    }
    HashMap<Integer,Stage> getStages() {
        HashMap<Integer,Stage> stages = new HashMap<Integer,Stage>();
        stages.put(1,new Stage(1, 0, 1000));
        stages.put(2,new Stage(2, 1000, 5000));
        stages.put(3,new Stage(3, 5000, 10000));
        stages.put(4,new Stage(4, 10000, 20000));
        stages.put(5,new Stage(5, 20000, 50000));
        stages.put(6,new Stage(6, 50000, 100000));
        stages.put(7,new Stage(7, 100000, 200000));
        stages.put(8,new Stage(8, 200000, 300000));
        stages.put(9,new Stage(9, 300000, 500000));
        stages.put(10,new Stage(10, 500000, 1000000));
        return stages;
    }
}
