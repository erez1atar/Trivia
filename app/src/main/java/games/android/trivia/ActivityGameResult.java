package games.android.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityGameResult extends AppCompatActivity {

    public static final String INTENT_SCORE_KEY = "score";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

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
        scoreTxt.setText(String.valueOf(score));

        TextView youWinTxt = (TextView)findViewById(R.id.game_result_you_win);

        scoreTxt.setTypeface(App.getResourcesManager().getNumbersFont());
        tryAgainBtn.setTypeface(App.getResourcesManager().getNumbersFont());
        backBtn.setTypeface(App.getResourcesManager().getNumbersFont());
        youWinTxt.setTypeface(App.getResourcesManager().getNumbersFont());
    }
}
