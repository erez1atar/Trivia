package games.android.trivia;

import games.android.trivia.Questions.Question;

/**
 * Created by Erez on 04/10/2017.
 */

public interface IGamePresentor {

    void onCorrectAnswer();

    void onIncorrectAnswer(String correctAnswer);

    void onNewQuestionLoaded(Question question);

    void onMoneyChanged(int money);

    void onLoseGame(int score);

    void showStage(int minValue, int maxValue, int id);

    void showHearts(int hearts);

    void showTurnTime(int seconds);

    void showGameTime(int seconds);

    void startRadomPrizeLottery(int minValue, int maxValue, int prize);

}
