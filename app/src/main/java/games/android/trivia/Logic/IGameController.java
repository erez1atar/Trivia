package games.android.trivia.Logic;

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


}
