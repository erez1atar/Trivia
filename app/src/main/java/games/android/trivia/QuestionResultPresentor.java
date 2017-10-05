package games.android.trivia;

import android.view.View;

/**
 * Created by Erez on 04/10/2017.
 */

public interface QuestionResultPresentor {

    void onAnswerCorrect(View v);

    void onAnswerIncorrect(View v);
}
