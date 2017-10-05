package games.android.trivia.Questions;

import java.util.Random;

/**
 * Created by LENOVO on 11/02/2016.
 */
public class Question
{
    private String question;
    private String[] answers;
    private String correctAns;

    public Question(){}

    public Question(String question, String[] answers, String correctAns)
    {
        this.answers = answers;
        this.correctAns = correctAns;
        this.question = question;
    }

    public String getCorrectAns() {
        return correctAns;
    }

    public String[] getAnswers() {
        return answers;
    }

    public String getQuestion() {
        return question;
    }

    public boolean isCorrectAnswer(String answer)
    {
        return answer.compareTo(correctAns) == 0;
    }

    public int idxOfAnswerToHide()
    {
        Random r = new Random();
        int idx = 0;
        do {
            idx = r.nextInt(answers.length);
        }while(answers[idx].compareTo(correctAns) == 0);
        return idx;

    }

    public boolean isAnswerCorrect(String answer) {
        return answer.equals(correctAns);
    }
    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public void setCorrectAns(String correctAns) {
        this.correctAns = correctAns;
    }


}
