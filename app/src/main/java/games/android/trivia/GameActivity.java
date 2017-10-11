package games.android.trivia;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import games.android.trivia.Bars.BottomBar;
import games.android.trivia.Bars.TopBarFragment;
import games.android.trivia.Logic.GameController;
import games.android.trivia.Logic.IGameController;
import games.android.trivia.Questions.Question;

public class GameActivity extends AppCompatActivity implements IGamePresentor,TopBarFragment.TopBarListener,BottomBar.BottomBarListener{

    private Button answer1 = null;
    private Button answer2 = null;
    private Button answer3 = null;
    private Button answer4 = null;
    private TextView questionTxt = null;
    private AnswerListener answerListener = null;
    private View lastAnswerPicked = null;
    private IGameController gameController = null;
    private TopBarFragment topBar = null;
    private BottomBar bottomBar = null;
    private boolean topBarReady = false;
    private boolean bottomBarReady = false;
    private Typeface typeface = null;
    private Animation scaleInAnim = null;
    private Drawable incorrectBackground = null;
    private Drawable correctBackground = null;
    private Drawable regularBackground = null;
    private Drawable watingBackground1 = null;
    private Drawable watingBackground2 = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        this.initTopBar();
        this.initBottomBar();

        scaleInAnim =  AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale_in);
        incorrectBackground = ContextCompat.getDrawable(this, R.drawable.button_incorrect_style);
        correctBackground = ContextCompat.getDrawable(this, R.drawable.button_correct_style);
        regularBackground = ContextCompat.getDrawable(this, R.drawable.button_style);
        watingBackground1 = ContextCompat.getDrawable(this, R.drawable.button_waiting);
        questionTxt = (TextView)findViewById(R.id.question);
        answer1 = (Button) findViewById(R.id.answer_1);
        answer2 = (Button)findViewById(R.id.answer_2);
        answer3 = (Button)findViewById(R.id.answer_3);
        answer4 = (Button)findViewById(R.id.answer_4);

        this.gameController = new GameController(this);
        this.answerListener = new AnswerListener(this.gameController);

        this.initButtons();

    }

    private void onGameStart() {
        gameController.onNewGameStart();
    }

    private void initButtons() {
        questionTxt = (TextView)findViewById(R.id.question);
        answer1 = (Button) findViewById(R.id.answer_1);
        answer2 = (Button)findViewById(R.id.answer_2);
        answer3 = (Button)findViewById(R.id.answer_3);
        answer4 = (Button)findViewById(R.id.answer_4);

        typeface = Typeface.createFromAsset(getAssets(),"fonts/hebrew.ttf");
        answer1.setTypeface(typeface);
        answer2.setTypeface(typeface);
        answer3.setTypeface(typeface);
        answer4.setTypeface(typeface);
        questionTxt.setTypeface(typeface);


        Log.d("GameActivity", "initButtons");
        answer1.setOnClickListener(answerListener);
        answer2.setOnClickListener(answerListener);
        answer3.setOnClickListener(answerListener);
        answer4.setOnClickListener(answerListener);
    }

    private void initTopBar() {

        this.topBar = new TopBarFragment();
        this.topBar.setListener(this);
        final FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_top_bar, topBar);
        fragmentTransaction.commit();
    }

    private void initBottomBar() {

        this.bottomBar = new BottomBar();
        this.bottomBar.setListener(this);
        final FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_bottom_bar, this.bottomBar);
        fragmentTransaction.commit();
    }

    @Override
    public void onCorrectAnswer() {
        Log.d("GameActivity", "onCorrectAnswer");
        this.showcorrectAnim();
    }

    private void showcorrectAnim() {
        Log.d("GameActivity", "showCurrectAnim");
        new Handler().postDelayed(new ResultAnswerRunnable(this,gameController,lastAnswerPicked,true), 300);
        new Handler().postDelayed(new FinishQuestionRunnable(gameController, true), 1500);
    }

    void showCorrectAnswerColor() {
        this.lastAnswerPicked.setBackground(correctBackground);
    }

    void  showWaitingColor() {
        this.lastAnswerPicked.setBackground(watingBackground1);
    }

    @Override
    public void onIncorrectAnswer() {
        Log.d("GameActivity", "onInCorrectAnswer");
        new Handler().postDelayed(new ResultAnswerRunnable(this,gameController,lastAnswerPicked,false), 1000);
        new Handler().postDelayed(new FinishQuestionRunnable(gameController, false), 1500);
    }

    @Override
    public void onNewQuestionLoaded(Question question) {
        this.setEnabledAllAnswers(true);
        questionTxt.setText(question.getQuestion());
        answer1.setText(question.getAnswers()[0]);
        answer2.setText(question.getAnswers()[1]);
        answer3.setText(question.getAnswers()[2]);
        answer4.setText(question.getAnswers()[3]);

        answer1.startAnimation(scaleInAnim);
        answer2.startAnimation(scaleInAnim);
        answer3.startAnimation(scaleInAnim);
        answer4.startAnimation(scaleInAnim);

        answer1.setBackground(regularBackground);
        answer2.setBackground(regularBackground);
        answer3.setBackground(regularBackground);
        answer4.setBackground(regularBackground);
    }

    @Override
    public void onMoneyChanged(int money) {
        Log.d("game", ("score now is " +  money));
        this.topBar.setScoreTxt(String.valueOf(money));
    }

    @Override
    public void onLoseGame(int score) {
        Log.d("game", "You Lose " + score);
        Intent intent =  new Intent(GameActivity.this, ActivityGameResult.class);
        intent.putExtra(ActivityGameResult.INTENT_SCORE_KEY, score);
        startActivity(intent);
    }

    @Override
    public void showStage(int minValue, int maxValue, int id) {
        this.bottomBar.showRange(minValue, maxValue, id);
    }

    @Override
    public void showHearts(int hearts) {
        this.topBar.setHearts(hearts);
    }

    @Override
    public void showTime(int seconds) {
        this.topBar.setTime(seconds);
    }

    @Override
    public void setPrize(final int minValue, final int maxValue, final int prize) {

        ValueAnimator animator = ValueAnimator.ofInt(minValue, maxValue);

        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                int random = (int)(minValue + Math.random() * (maxValue - minValue));
                bottomBar.setPrize(String.valueOf(random));
            }
        });
        animator.start();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1020);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bottomBar.setPrize(String.valueOf(prize));
                    }
                });
            }
        });
        thread.start();
    }

    @Override
    public void onTopBarCreatedFinished() {
        this.topBarReady = true;
        if(this.bottomBarReady){
            this.gameController.onNewGameStart();
            this.setEnabledAllAnswers(true);
            this.topBarReady = false;
            this.bottomBarReady = false;
        }
    }

    @Override
    public void onBottomBarCreatedFinished() {
        this.bottomBarReady = true;
        if(this.topBarReady){
            this.gameController.onNewGameStart();
            this.setEnabledAllAnswers(true);
            this.topBarReady = false;
            this.bottomBarReady = false;
        }
    }

    private class AnswerListener implements View.OnClickListener {
        IGameController controller = null;

        public AnswerListener(IGameController controller) {
            this.controller = controller;
        }

        @Override
        public void onClick(View v) {
            Log.d("answerListener", "onClick");
            lastAnswerPicked = v;
            setEnabledAllAnswers(false);
            v.setBackground(watingBackground1);
            controller.onAnswerPicked(((Button)v).getText().toString());
        }
    }

    private void btnWaitingAnimation(final View v) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 0 ; i < 4 ; i++){
                    try {
                        Log.d("btn wating" , "sleep");
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    final int idx = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("btn wating" , "running");
                            if(idx % 2 == 0){
                                v.setBackground(watingBackground1);
                            }
                            else {
                                v.setBackground(watingBackground2);
                            }
                        }
                    });
                }
            }
        });
        thread.start();
    }

    private void setEnabledAllAnswers(boolean enable) {
        this.answer1.setEnabled(enable);
        this.answer2.setEnabled(enable);
        this.answer3.setEnabled(enable);
        this.answer4.setEnabled(enable);
    }

    public class ResultAnswerRunnable implements Runnable {
        private WeakReference<Activity> activity;
        private IGameController controller;
        private View view;
        private boolean isCorrectAns = false;

        public ResultAnswerRunnable(Activity activity, IGameController controller, View view, boolean isCorrectAns) {
            this.activity = new WeakReference<>(activity);
            this.controller = controller;
            this.view = view;
            this.isCorrectAns = isCorrectAns;
        }

        @Override
        public void run() {
            Activity activity = this.activity.get();
            if (activity != null) {
                if(isCorrectAns){
                    view.setBackground(correctBackground);
                }
                else {
                    view.setBackground(incorrectBackground);
                }
            }
        }
    }

    private class FinishQuestionRunnable implements Runnable {
        private WeakReference<IGameController> controllerWeakRef;
        private boolean isCorrectAns = false;

        public FinishQuestionRunnable(IGameController controller, boolean isCorrectAns) {
            this.controllerWeakRef = new WeakReference<>(controller);
            this.isCorrectAns = isCorrectAns;
        }

        @Override
        public void run() {
            IGameController controller = this.controllerWeakRef.get();
            if (controller != null) {
                if(isCorrectAns){
                    gameController.onShowCurrectQuestionfinished();
                }
                else {
                    gameController.onShowIncurrectQuestionfinished();
                }

            }
        }
    }
}
