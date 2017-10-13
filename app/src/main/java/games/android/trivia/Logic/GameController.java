package games.android.trivia.Logic;

import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import games.android.trivia.App;
import games.android.trivia.GlobalHighScore.GlobalHighScoreManager;
import games.android.trivia.HighScores.WinnerAdapter;
import games.android.trivia.HighScores.WinnerData;
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
    private int currntPrize;
    private GlobalHighScoreManager globalHighScoreManager;


    public GameController(IGamePresentor presentor) {
        questionBank = new QuestionBank(App.getInstance().getResources());
        this.presentor = presentor;
        stagesManager = new StagesManager();
        stagesManager.setStagesListener(this);
        timer = new Timer(this);
        globalHighScoreManager = new GlobalHighScoreManager();
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
        currentQuestion = questionBank.getNextQuestion(this.stagesManager.getDiffculty());
        presentor.onNewQuestionLoaded(currentQuestion);
        presentor.showStage(this.stagesManager.getMinInRange(), this.stagesManager.getMaxInRange(), this.stagesManager.getStageId());
        this.currntPrize = this.getRandomPrize();
        presentor.setPrize(this.stagesManager.getMinInRange(), this.stagesManager.getMaxInRange(), this.currntPrize);
        timer.endTimer();
        timer.start(30);
        this.questionNum++;
        this.stagesManager.onQuestionNumChanged(this.questionNum);
    }

    @Override
    public void onAnswerPicked(String answer) {
        boolean isAnswerCorrect = currentQuestion.isAnswerCorrect(answer);
        Log.d("GameController", "onAnswerPicked");
        if(isAnswerCorrect){
            wallet.addMoney(this.currntPrize);
            presentor.onCorrectAnswer();
            presentor.onMoneyChanged(wallet.getMoney());
        }
        else {
            this.hearts--;
            this.presentor.showHearts(this.hearts);
            presentor.onIncorrectAnswer();
            presentor.onMoneyChanged(wallet.getMoney());
            if(this.hearts == 0){
                this.actFinishGame();
            }
        }
    }

    private void actFinishGame() {
        globalHighScoreManager.tryAddWinner(App.getUserDefaultManager().getUserName(), wallet.getMoney());
        this.presentor.onLoseGame(wallet.getMoney());
        updateDataBase(wallet.getMoney(), 0);
        addToHallOfFame();
        timer.endTimer();
    }

    @Override
    public void onShowCurrectQuestionfinished() {
        this.loadNewQuestion();
    }

    @Override
    public void onShowIncurrectQuestionfinished() {
        this.loadNewQuestion();
    }

    private int getRandomPrize() {
        int min = this.stagesManager.getMinInRange();
        int max = this.stagesManager.getMaxInRange();

        return (int)(min + (max - min) * Math.random());
    }

    @Override
    public void onStageChanged() {
        this.presentor.showStage(this.stagesManager.getMinInRange(), this.stagesManager.getMaxInRange(), this.stagesManager.getStageId());
    }


    @Override
    public void onTickUpdated(int seconds) {
        this.presentor.showTime(seconds);
    }

    @Override
    public void onTimeFinished() {
        this.actFinishGame();
    }

    private void addToHallOfFame()
    {
        WinnerData winner  = new WinnerData(this.wallet.getMoney(),App.getUserDefaultManager().getUserName());
        WinnerAdapter adpt = App.getWinAdapter();
        adpt.add(winner);
    }

    private void updateDataBase(int score, int helpsLeft)
    {
        App.getDataBase().add(new WinnerData(score , "ארז"));
    }
}
