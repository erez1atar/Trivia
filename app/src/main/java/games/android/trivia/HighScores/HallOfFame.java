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


public class HallOfFame extends AppCompatActivity implements FirebaseManager.HighScoreListener,FirebaseManager.MonthlyHighScoreListener {


    private enum GLOBAL_TYPE  {
            MONTHLY,
            ALL_TIMES
    }

    public static final String INTENT_HIGH_SCORE_KEY = "hs";
    public static final String INTENT_LOCAL_HISH_SCORE_VALUE = "lo";
    public static final String INTENT_GLOBAL_HISH_SCORE_VALUE = "gl";

    private Button montly_btn = null;
    private Button all_times_btn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);

        Intent intent = getIntent();
        String type = intent.getStringExtra(HallOfFame.INTENT_HIGH_SCORE_KEY);
        all_times_btn = (Button)findViewById(R.id.hall_of_fame_all_times);
        montly_btn = (Button)findViewById(R.id.hall_of_fame_monthly);

        if(type.equals(HallOfFame.INTENT_GLOBAL_HISH_SCORE_VALUE)){
            initGlobal(GLOBAL_TYPE.ALL_TIMES);
            this.actButtonsState(true);
        }
        else {
            initLocal();
            all_times_btn.setVisibility(View.INVISIBLE);
            all_times_btn.setEnabled(false);
            montly_btn.setVisibility(View.INVISIBLE);
            montly_btn.setEnabled(false);
        }

        Button restart = (Button)findViewById(R.id.restartFromList);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        all_times_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGlobal(GLOBAL_TYPE.ALL_TIMES);
                actButtonsState(true);
            }
        });

        montly_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initGlobal(GLOBAL_TYPE.MONTHLY);
                actButtonsState(false);
            }
        });
    }

    private void initLocal() {
        /*((TextView)(findViewById(R.id.hall_of_fame_name))).setTypeface(App.getResourcesManager().getNumbersFont());
        ((TextView)(findViewById(R.id.hall_of_fame_score))).setTypeface(App.getResourcesManager().getNumbersFont());
        ((TextView)(findViewById(R.id.restartFromList))).setTypeface(App.getResourcesManager().getNumbersFont());*/

        ListView listView = (ListView)findViewById(R.id.listOfWinners);
        DBManager manager = App.getDataBase();
        ScoreCursorAdapter adapter = App.getCursorAdapter();
        adapter.changeCursor(manager.getAllHighScores());
        listView.setAdapter(adapter);
    }

    private void initGlobal(GLOBAL_TYPE type) {
        if(type == GLOBAL_TYPE.MONTHLY){
            App.getFirebaseManager().setMonthlyHighScoreListener(this);
            App.getFirebaseManager().requestMonthlyHighScores();
        }
        else {
            App.getFirebaseManager().setHighScoreListener(this);
            App.getFirebaseManager().requestHighScores();
        }
    }

    @Override
    public void onHighScoresReady(final ArrayList<FirebaseWinnerWrapper> winnerWrappers) {
        doHighScoreTableInit(winnerWrappers);
    }

    @Override
    public void onMonthlyHighScoresReady(ArrayList<FirebaseWinnerWrapper> winnerWrappers) {
        doHighScoreTableInit(winnerWrappers);
    }

    private void doHighScoreTableInit(final ArrayList<FirebaseWinnerWrapper> winnerWrappers) {
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

    private void actButtonsState(boolean allTimesOn) {

        all_times_btn.setBackgroundResource(allTimesOn ? R.drawable.button_correct_style : R.drawable.button_style);
        montly_btn.setBackgroundResource(allTimesOn ? R.drawable.button_style : R.drawable.button_correct_style);

    }
}
