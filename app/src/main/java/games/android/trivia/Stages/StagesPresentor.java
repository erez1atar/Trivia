package games.android.trivia.Stages;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import games.android.trivia.App;
import games.android.trivia.GameActivity;
import games.android.trivia.R;

public class StagesPresentor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stages_presentor);

        Button start = (Button)findViewById(R.id.stages_presentor_start_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StagesPresentor.this, GameActivity.class);
                startActivity(intent);
            }
        });
        //start.setTypeface(App.getResourcesManager().getNumbersFont());

    }
}
