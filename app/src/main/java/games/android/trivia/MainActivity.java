package games.android.trivia;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import games.android.trivia.GlobalHighScore.FirebaseManager;
import games.android.trivia.HighScores.HallOfFame;
import games.android.trivia.Stages.StagesPresentor;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    }

    private void showEnterNameDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.enter_name_dialog);


        final EditText name = (EditText) dialog.findViewById(R.id.enter_name_dialog_name);
        Button next = (Button)dialog.findViewById(R.id.enter_name_dialog_next) ;

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getUserDefaultManager().setUserName(name.getText().toString());
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this,StagesPresentor.class));
            }
        });

        dialog.show();
    }
}
