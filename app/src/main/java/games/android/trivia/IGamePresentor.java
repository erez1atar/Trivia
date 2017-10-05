package games.android.trivia;

import games.android.trivia.Questions.Question;

/**
 * Created by Erez on 04/10/2017.
 */

public interface IGamePresentor {

    void onCorrectAnswer();

    void onIncorrectAnswer();

    void onNewQuestionLoaded(Question question);

    void onMoneyChanged(int money);

    void onLoseGame();

    void showStage(int minValue, int maxValue);

    void showHearts(int hearts);

    void showTime(int seconds);

}
