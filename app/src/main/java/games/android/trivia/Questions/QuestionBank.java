package games.android.trivia.Questions;

import android.content.res.Resources;
import android.content.res.TypedArray;

import java.util.ArrayList;
import java.util.Collections;

import games.android.trivia.R;
import games.android.trivia.ResourcesManager;


/**
 * Created by LENOVO on 11/02/2016.
 */
public class QuestionBank
{
    private ArrayList<Question> questionsBank;
    private int currentQuestion = 0;
    private Resources res = null;

    public QuestionBank(Resources res) {
        this.res = res;
        this.initQuestions();
    }

    private void initQuestions() {
        TypedArray arr = res.obtainTypedArray(R.array.questions);
        questionsBank = new ArrayList(arr.length());
        for(int i = 0; i < arr.length() ; ++i)
        {
            int id = arr.getResourceId(i, 0);
            TypedArray innerArr = res.obtainTypedArray(id);
            String[] answers = new String[4];
            for(int k = 1; k < innerArr.length() ; ++k)
            {
                answers[k - 1] = innerArr.getString(k);
            }
            questionsBank.add(new Question(innerArr.getString(0),answers,answers[0]));
        }

        Collections.shuffle(questionsBank);
    }

    public Question getNextQuestion()
    {
        if(currentQuestion >= questionsBank.size()){
            this.initQuestions();
        }
        return questionsBank.get(currentQuestion++);

    }

    public void resetQuestions()
    {
        currentQuestion = 0;
    }
}
