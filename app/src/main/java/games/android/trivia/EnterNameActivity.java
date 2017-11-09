package games.android.trivia;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import games.android.trivia.Stages.StagesPresentor;

public class EnterNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);

        final EditText name = (EditText) findViewById(R.id.enter_name_name);
        Button next = (Button)findViewById(R.id.enter_name_next) ;
        TextView desc1 = (TextView) findViewById(R.id.enter_name_desc1);
        TextView desc2 = (TextView) findViewById(R.id.enter_name_desc2);
        next.setTypeface(App.getResourcesManager().getNumbersFont());
        name.setTypeface(App.getResourcesManager().getNumbersFont());
        desc1.setTypeface(App.getResourcesManager().getNumbersFont());
        desc2.setTypeface(App.getResourcesManager().getNumbersFont());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getUserDefaultManager().setUserName(name.getText().toString());
                App.getAnalyticsManager().logNameInsertedEvent(name.getText().toString());
                startActivity(new Intent(EnterNameActivity.this,StagesPresentor.class));
            }
        });
    }
}
