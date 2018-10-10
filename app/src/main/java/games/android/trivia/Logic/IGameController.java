package games.android.trivia.Logic;

import java.util.ArrayList;

/**
 * Created by Erez on 04/10/2017.
 */

public interface IGameController {

    void onNewGameStart();

    void onAnswerPicked(String answer);

    void onShowCurrectQuestionfinished();

    void onShowIncurrectQuestionfinished();

    void onPrizeReadyPrize(int prize);

    void forceEndGame();

    void onVideoStart();

    ArrayList<String> getOptionsToFiftyFifty();

    void onVideoEnd();

    void fiftyFiftyReward();

    int getQuestionNum();


}
