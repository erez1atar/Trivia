package games.android.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.text.NumberFormat;
import java.util.Locale;

public class ActivityGameResult extends AppCompatActivity {

    public static final String INTENT_SCORE_KEY = "score";
    private InterstitialAd mInterstitialAd;

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
        int score = intent.getIntExtra(INTENT_SCORE_KEY, 0);

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
    }
}
