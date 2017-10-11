package games.android.trivia.HighScores;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import games.android.trivia.App;
import games.android.trivia.DataBase.DBManager;
import games.android.trivia.DataBase.ScoreCursorAdapter;
import games.android.trivia.GlobalHighScore.FirebaseManager;
import games.android.trivia.GlobalHighScore.FirebaseWinnerWrapper;
import games.android.trivia.GlobalHighScore.GlobalHighScoreManager;
import games.android.trivia.R;


public class HallOfFame extends AppCompatActivity implements FirebaseManager.HighScoreListener {

    public static final String INTENT_HIGH_SCORE_KEY = "hs";
    public static final String INTENT_LOCAL_HISH_SCORE_VALUE = "lo";
    public static final String INTENT_GLOBAL_HISH_SCORE_VALUE = "lo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);

        Intent intent = getIntent();
        String type = intent.getStringExtra(HallOfFame.INTENT_HIGH_SCORE_KEY);
        if(type.equals(HallOfFame.INTENT_GLOBAL_HISH_SCORE_VALUE)){
            initGlobal();
        }
        else {
            initLocal();
        }

        Button restart = (Button)findViewById(R.id.restartFromList);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initLocal() {
        ((TextView)(findViewById(R.id.hall_of_fame_name))).setTypeface(App.getResourcesManager().getNumbersFont());
        ((TextView)(findViewById(R.id.hall_of_fame_score))).setTypeface(App.getResourcesManager().getNumbersFont());
        ((TextView)(findViewById(R.id.restartFromList))).setTypeface(App.getResourcesManager().getNumbersFont());

        ListView listView = (ListView)findViewById(R.id.listOfWinners);
        DBManager manager = App.getDataBase();
        ScoreCursorAdapter adapter = App.getCursorAdapter();
        adapter.changeCursor(manager.getAllHighScores());
        listView.setAdapter(adapter);
    }

    private void initGlobal() {
        App.getFirebaseManager().setHighScoreListener(this);
        App.getFirebaseManager().requestHighScores();
    }

    @Override
    public void onHighScoresReady(final ArrayList<FirebaseWinnerWrapper> winnerWrappers) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((TextView)(findViewById(R.id.hall_of_fame_name))).setTypeface(App.getResourcesManager().getNumbersFont());
                ((TextView)(findViewById(R.id.hall_of_fame_score))).setTypeface(App.getResourcesManager().getNumbersFont());
                ((TextView)(findViewById(R.id.restartFromList))).setTypeface(App.getResourcesManager().getNumbersFont());

                ArrayList<WinnerData> winnerDatas = new ArrayList<>();
                for(int i = 0 ; i < winnerWrappers.size() ; ++i){
                    winnerDatas.add(winnerWrappers.get(i).getFirebaseWinnerData());
                }
                Collections.sort(winnerDatas, Collections.reverseOrder());
                GlobalScoreAdapter globalScoreAdapter = new GlobalScoreAdapter(HallOfFame.this,R.layout.winner, winnerDatas);
                ListView listView = (ListView)findViewById(R.id.listOfWinners);
                listView.setAdapter(globalScoreAdapter);
            }
        });
    }
}
