package games.android.trivia.Analytics;

import android.os.Bundle;

import games.android.trivia.App;
import games.android.trivia.GlobalHighScore.FirebaseManager;

/**
 * Created by Erez on 09/11/2017.
 */

public class FirebaseAnalytics {

    private FirebaseManager firebaseManager = null;
    private final String START_GAME_EVENT = "start_game";
    private final String END_GAME_EVENT = "end_game";
    private final String OPEN_APP_EVENT = "open_app";
    private final String INSERT_NAME_EVENT = "insert_name_from_scrn";

    private final String NAME_PARAM = "name";

    public FirebaseAnalytics() {
        firebaseManager = App.getFirebaseManager();
    }

    public void logStartGameEvent(){
        firebaseManager.logEvent(START_GAME_EVENT, new Bundle());
    }

    public void logEndGameEvent(){
        firebaseManager.logEvent(END_GAME_EVENT, new Bundle());
    }

    public void logAppOpenedEvent(){
        firebaseManager.logEvent(OPEN_APP_EVENT, new Bundle());
    }

    public void logNameInsertedEvent(String name){
        firebaseManager.logEvent(INSERT_NAME_EVENT, new Bundle());
    }
}
