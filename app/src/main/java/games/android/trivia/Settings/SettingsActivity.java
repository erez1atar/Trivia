package games.android.trivia.Settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import games.android.trivia.App;
import games.android.trivia.CreditsActivity;
import games.android.trivia.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button save = (Button)findViewById(R.id.settings_back);
        Switch music = (Switch)findViewById(R.id.settings_music_switch);
        Switch sound = (Switch)findViewById(R.id.settings_sound_switch);
        final EditText name = (EditText) findViewById(R.id.settings_name);

        music.setTypeface(App.getResourcesManager().getNumbersFont());
        sound.setTypeface(App.getResourcesManager().getNumbersFont());

        name.setText(App.getUserDefaultManager().getUserName());

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.getUserDefaultManager().setUserName(name.getText().toString());
                finish();
            }
        });

        Button crideits = (Button)findViewById(R.id.settings_credits);
        crideits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, CreditsActivity.class);
                startActivity(intent);
            }
        });
    }
}
