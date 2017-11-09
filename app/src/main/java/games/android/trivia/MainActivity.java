package games.android.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import games.android.trivia.HighScores.HallOfFame;
import games.android.trivia.Settings.SettingsActivity;
import games.android.trivia.Stages.StagesPresentor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getAnalyticsManager().logAppOpenedEvent();

        Button start = (Button)findViewById(R.id.start_game_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(App.getUserDefaultManager().getUserName().equals("")){
                    showEnterNameDialog();
                }
                else{
                    startActivity(new Intent(MainActivity.this,StagesPresentor.class));
                }

            }
        });

        Button hallOfFame = (Button)findViewById(R.id.hall_of_fame_btn);
        hallOfFame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HallOfFame.class);
                intent.putExtra(HallOfFame.INTENT_HIGH_SCORE_KEY, HallOfFame.INTENT_LOCAL_HISH_SCORE_VALUE);
                startActivity(intent);
            }
        });

        Button hallOfFameGlobal = (Button)findViewById(R.id.hall_of_fame_global_btn);
        hallOfFameGlobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HallOfFame.class);
                intent.putExtra(HallOfFame.INTENT_HIGH_SCORE_KEY, HallOfFame.INTENT_GLOBAL_HISH_SCORE_VALUE);
                startActivity(intent);
            }
        });

        Button SettingsBtn = (Button)findViewById(R.id.main_go_settings_btn);
        SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SettingsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showEnterNameDialog() {
        startActivity(new Intent(MainActivity.this,EnterNameActivity.class));
    }
}
