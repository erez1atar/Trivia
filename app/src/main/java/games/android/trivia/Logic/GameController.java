package games.android.trivia.Logic;

import android.util.Log;

import games.android.trivia.App;
import games.android.trivia.IGamePresentor;
import games.android.trivia.Questions.Question;
import games.android.trivia.Questions.QuestionBank;
import games.android.trivia.Stages.StagesManager;
import games.android.trivia.Timer;
import games.android.trivia.Wallet;

/**
 * Created by Erez on 04/10/2017.
 */

public class GameController implements IGameController,StagesManager.StagesListener,Timer.TimerListener {
    private QuestionBank questionBank = null;
    private Question currentQuestion = null;
    private IGamePresentor presentor = null;
    private StagesManager stagesManager = null;
    private Wallet wallet = null;
    private int hearts;
    private int questionNum = 0;
    private Timer timer = null;


    public GameController(IGamePresentor presentor) {
        questionBank = new QuestionBank(App.getInstance().getResources());
        this.presentor = presentor;
        stagesManager = new StagesManager();
        stagesManager.setStagesListener(this);
        timer = new Timer(this);
    }
    @Override
    public void onNewGameStart() {
        stagesManager.onGameStart();
        wallet = new Wallet(100);
        questionBank = new QuestionBank(App.getInstance().getResources());
        this.hearts = 3;
        this.presentor.showHearts(this.hearts);
        this.questionNum = 1;
        this.loadNewQuestion();
    }

    public void loadNewQuestion() {
        currentQuestion = questionBank.getNextQuestion();
        presentor.onNewQuestionLoaded(currentQuestion);
        presentor.showStage(this.stagesManager.getMinInRange(), this.stagesManager.getMaxInRange());
        timer.endTimer();
        timer.start(30);
    }

    @Override
    public void onAnswerPicked(String answer) {
        boolean isAnswerCorrect = currentQuestion.isAnswerCorrect(answer);
        Log.d("GameController", "onAnswerPicked");
        if(isAnswerCorrect){
            wallet.addMoney(this.getRandomPrize());
            presentor.onCorrectAnswer();
            presentor.onMoneyChanged(wallet.getMoney());
            this.questionNum++;
            this.stagesManager.onQuestionNumChanged(this.questionNum);
        }
        else {
            this.hearts--;
            this.presentor.showHearts(this.hearts);
            presentor.onIncorrectAnswer();
            presentor.onMoneyChanged(wallet.getMoney());
            if(this.hearts == 0){
                this.presentor.onLoseGame();
                timer.endTimer();
            }
        }
    }

    @Override
    public void onShowCurrectQuestionfinished() {
        this.loadNewQuestion();
    }

    private int getRandomPrize() {
        int min = this.stagesManager.getMinInRange();
        int max = this.stagesManager.getMaxInRange();

        return (int)(min + (max - min) * Math.random());
    }

    @Override
    public void onStageChanged() {
        this.presentor.showStage(this.stagesManager.getMinInRange(), this.stagesManager.getMaxInRange());
    }


    @Override
    public void onTickUpdated(int seconds) {
        this.presentor.showTime(seconds);
    }

    @Override
    public void onTimeFinished() {
        this.presentor.onLoseGame();
        timer.endTimer();
    }
}
