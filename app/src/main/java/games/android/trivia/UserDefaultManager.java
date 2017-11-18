package games.android.trivia;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Erez on 11/10/2017.
 */

public class UserDefaultManager {
    private SharedPreferences preferences;
    private final String NAME_KEY = "name";
    private final String GAME_NUM_KEY = "game_num";

    public UserDefaultManager() {
        preferences = PreferenceManager.getDefaultSharedPreferences(App.getInstance());
    }

    public String getUserName() {
        String name = preferences.getString(NAME_KEY, "");
        return name;
    }

    public void setUserName(String name) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(NAME_KEY,name);
        editor.apply();
    }

    public int getGameNumber() {
        return preferences.getInt(GAME_NUM_KEY, 0);
    }

    public void increaseGameNumber(){
        int gameNum = getGameNumber();
        gameNum++;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(GAME_NUM_KEY,gameNum);
        editor.apply();
    }
}
