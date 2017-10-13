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
    private ArrayList<Question> questionsBankEasy;
    private ArrayList<Question> questionsBankMedium;
    private ArrayList<Question> questionsBankHard;
    private int currentQuestionEasy = 0;
    private int currentQuestionMedium = 0;
    private int currentQuestionHard = 0;
    private Resources res = null;

    public QuestionBank(Resources res) {
        this.res = res;
        this.initQuestions(QuestionDifficulty.EASY);
        this.initQuestions(QuestionDifficulty.HRAD);
        this.initQuestions(QuestionDifficulty.MEDIUM);
    }

    private void initQuestions(QuestionDifficulty difficulty) {
        TypedArray arr = null;
        ArrayList<Question> questions = null;
        switch (difficulty){
            case EASY:
                arr = res.obtainTypedArray(R.array.questions_easy);
                questionsBankEasy = new ArrayList(arr.length());
                questions = questionsBankEasy;
                currentQuestionEasy = 0;
                break;
            case MEDIUM:
                arr = res.obtainTypedArray(R.array.questions_medium);
                questionsBankMedium = new ArrayList(arr.length());
                questions = questionsBankMedium;
                currentQuestionMedium = 0;
                break;
            case HRAD:
                arr = res.obtainTypedArray(R.array.questions_hard);
                questionsBankHard = new ArrayList(arr.length());
                questions = questionsBankHard;
                currentQuestionHard = 0;
                break;
        }

        for(int i = 0; i < arr.length() ; ++i)
        {
            int id = arr.getResourceId(i, 0);
            TypedArray innerArr = res.obtainTypedArray(id);
            String[] answers = new String[4];
            for(int k = 1; k < innerArr.length() ; ++k)
            {
                answers[k - 1] = innerArr.getString(k);
            }
            questions.add(new Question(innerArr.getString(0),answers,answers[0]));
        }

        Collections.shuffle(questions);

    }

    public Question getNextQuestion(QuestionDifficulty difficulty)
    {
        switch (difficulty){
            case EASY:
                if(currentQuestionEasy >= questionsBankEasy.size()){
                    this.initQuestions(difficulty);
                }
                return questionsBankEasy.get(currentQuestionEasy++);
            case MEDIUM:
                if(currentQuestionMedium >= questionsBankMedium.size()){
                    this.initQuestions(difficulty);
                }
                return questionsBankMedium.get(currentQuestionMedium++);
            case HRAD:
            default:
                    if(currentQuestionHard >= questionsBankHard.size()){
                        this.initQuestions(difficulty);
                    }
                return questionsBankHard.get(currentQuestionHard++);
        }
    }

    public enum QuestionDifficulty {
        EASY,
        MEDIUM,
        HRAD
    }
}
