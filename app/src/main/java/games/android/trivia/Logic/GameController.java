package games.android.trivia.Logic;

import android.util.Log;

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
    private Timer singleTurnTimer = null;
    private Timer gameTimer = null;
    private int currntPrize;
    private GlobalHighScoreManager globalHighScoreManager;


    public GameController(IGamePresentor presentor) {
        questionBank = new QuestionBank(App.getInstance().getResources());
        this.presentor = presentor;
        stagesManager = new StagesManager();
        stagesManager.setStagesListener(this);
        singleTurnTimer = new Timer(this);
        final GameController instance = this;
        gameTimer = new Timer(new Timer.TimerListener() {
            @Override
            public void onTickUpdated(int seconds) {
                Log.d("tick", "gamr tick");
                instance.presentor.showGameTime(seconds);
            }

            @Override
            public void onTimeFinished() {
                instance.actFinishGame();
            }
        });
        globalHighScoreManager = GlobalHighScoreManager.getInstance();
    }
    @Override
    public void onNewGameStart() {
        App.getAnalyticsManager().logStartGameEvent();
        App.getUserDefaultManager().increaseGameNumber();
        gameTimer.start(210);
        stagesManager.onGameStart();
        wallet = new Wallet(0);
        questionBank = new QuestionBank(App.getInstance().getResources());
        this.hearts = 3;
        this.presentor.showHearts(this.hearts);
        this.questionNum = 0;
        this.prepareNewQuestion();
    }

    public void prepareNewQuestion() {
        singleTurnTimer.endTimer();
        this.questionNum++;
        this.stagesManager.onQuestionNumChanged(this.questionNum);
        presentor.showStage(this.stagesManager.getMinInRange(), this.stagesManager.getMaxInRange(), this.stagesManager.getStageId(), this.stagesManager.getStageBackGround(this.stagesManager.getStageId()));
        if(stagesManager.isFirstQuestionInStage(this.questionNum)){
            presentor.startRadomPrizeLottery(this.stagesManager.getMinInRange(), this.stagesManager.getMaxInRange(), this.currntPrize);
        }
        else {
            this.startNewQuestion();
        }
    }

    private void startNewQuestion() {
        currentQuestion = questionBank.getNextQuestion(this.stagesManager.getDiffculty());
        presentor.onNewQuestionLoaded(currentQuestion);
        singleTurnTimer.start(20);
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
            presentor.onIncorrectAnswer(currentQuestion.getCorrectAnswer());
            presentor.onMoneyChanged(wallet.getMoney());
        }
    }

    private void actFinishGame() {
        App.getAnalyticsManager().logEndGameEvent();
        singleTurnTimer.endTimer();
        gameTimer.endTimer();
        globalHighScoreManager.tryAddWinner(App.getUserDefaultManager().getUserName(), wallet.getMoney());
        this.presentor.onLoseGame(wallet.getMoney());
        updateDataBase(wallet.getMoney(), 0);
        addToHallOfFame();
    }

    @Override
    public void onShowCurrectQuestionfinished() {
        this.prepareNewQuestion();
    }

    @Override
    public void onShowIncurrectQuestionfinished() {
        if(this.hearts == 0){
            this.actFinishGame();
        }
        else {
            this.prepareNewQuestion();
        }
    }

    @Override
    public void onPrizeReadyPrize(int prize) {
        this.currntPrize = prize;
        this.startNewQuestion();
    }

    @Override
    public void forceEndGame() {
        this.actFinishGame();
    }

    private int getRandomPrize() {
        int min = this.stagesManager.getMinInRange();
        int max = this.stagesManager.getMaxInRange();

        return (int)(min + (max - min) * Math.random());
    }

    @Override
    public void onStageChanged() {
        this.presentor.showStage(this.stagesManager.getMinInRange(), this.stagesManager.getMaxInRange(), this.stagesManager.getStageId(), this.stagesManager.getStageBackGround(this.stagesManager.getStageId()));
    }


    @Override
    public void onTickUpdated(int seconds) {
        this.presentor.showTurnTime(seconds);
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
        App.getDataBase().add(new WinnerData(score ,App.getUserDefaultManager().getUserName()));
    }
}
