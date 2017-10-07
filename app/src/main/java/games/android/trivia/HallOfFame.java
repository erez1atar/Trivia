package games.android.trivia;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import games.android.trivia.DataBase.DBManager;
import games.android.trivia.DataBase.ScoreCursorAdapter;


public class HallOfFame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall_of_fame);

        ((TextView)(findViewById(R.id.hall_of_fame_date))).setTypeface(App.getResourcesManager().getNumbersFont());
        ((TextView)(findViewById(R.id.hall_of_fame_name))).setTypeface(App.getResourcesManager().getNumbersFont());
        ((TextView)(findViewById(R.id.hall_of_fame_score))).setTypeface(App.getResourcesManager().getNumbersFont());
        ((TextView)(findViewById(R.id.restartFromList))).setTypeface(App.getResourcesManager().getNumbersFont());

        ListView listView = (ListView)findViewById(R.id.listOfWinners);
        //listView.setAdapter(App.getWinAdapter());
        DBManager manager = App.getDataBase();
        ScoreCursorAdapter adapter = App.getCursorAdapter();
        adapter.changeCursor(manager.getAllHighScores());
        listView.setAdapter(adapter);
        Button restart = (Button)findViewById(R.id.restartFromList);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
