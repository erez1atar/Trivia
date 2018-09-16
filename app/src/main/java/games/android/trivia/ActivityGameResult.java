package games.android.trivia;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.NumberFormat;
import java.util.Locale;

import games.android.trivia.GlobalHighScore.FirebaseManager;
import games.android.trivia.GlobalHighScore.GlobalHighScoreManager;

public class ActivityGameResult extends AppCompatActivity implements GlobalHighScoreManager.TablesDataRequester {

    public static final String INTENT_SCORE_KEY = "score";
    private InterstitialAd mInterstitialAd;
    private int score = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1765755909018734/3155379363");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

        Intent intent = getIntent();

        score = intent.getIntExtra(INTENT_SCORE_KEY, 0);
        // MY HACK - its because the reqeues to add winner not finished, and the firebase manager not support two requwsts data and to add winner,
        // so i put delay to solve it
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                GlobalHighScoreManager.getInstance().requestTablesData(ActivityGameResult.this);
            }
        }, 5000);

        Button backBtn = (Button)findViewById(R.id.game_result_back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityGameResult.this, MainActivity.class));
            }
        });
        Button tryAgainBtn = (Button)findViewById(R.id.game_result_try_again);
        tryAgainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityGameResult.this, GameActivity.class));
            }
        });

        TextView scoreTxt = (TextView)findViewById(R.id.game_result_score);
        String formatStr = NumberFormat.getNumberInstance(Locale.US).format(score);
        scoreTxt.setText(formatStr);

        TextView youWinTxt = (TextView)findViewById(R.id.game_result_you_win);

        scoreTxt.setTypeface(App.getResourcesManager().getNumbersFont());
        tryAgainBtn.setTypeface(App.getResourcesManager().getNumbersFont());
        backBtn.setTypeface(App.getResourcesManager().getNumbersFont());
        youWinTxt.setTypeface(App.getResourcesManager().getNumbersFont());

//        findViewById(R.id.medal_month).setVisibility(View.VISIBLE);
//        findViewById(R.id.text_month).setVisibility(View.VISIBLE);
//        findViewById(R.id.medal_month).startAnimation(getScaleAnimation());
//        findViewById(R.id.medal_all_times).setVisibility(View.VISIBLE);
//        findViewById(R.id.text_all_times).setVisibility(View.VISIBLE);
//        findViewById(R.id.medal_all_times).startAnimation(getScaleAnimation());
    }

    @Override
    public void onTablesDataReady(final GlobalHighScoreManager.TablesData tablesData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(score > tablesData.monthlyMinScore) {
                    findViewById(R.id.medal_month).setVisibility(View.VISIBLE);
                    findViewById(R.id.text_month).setVisibility(View.VISIBLE);
                    findViewById(R.id.medal_month).startAnimation(getScaleAnimation());
                    Log.d("Result", "do monthly record animation");
                }
                if(score > tablesData.minScore) {
                    findViewById(R.id.medal_all_times).setVisibility(View.VISIBLE);
                    findViewById(R.id.text_all_times).setVisibility(View.VISIBLE);
                    findViewById(R.id.medal_all_times).startAnimation(getScaleAnimation());
                    Log.d("Result", "do monthly record animation");
                }
            }
        });

    }

    private ScaleAnimation getScaleAnimation() {
        ScaleAnimation animation = new ScaleAnimation((float) 0.8, (float)1.2, (float)0.8, (float)1.2, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(3000);
        return animation;

    }
}
