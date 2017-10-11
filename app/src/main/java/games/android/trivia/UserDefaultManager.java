package games.android.trivia;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Erez on 11/10/2017.
 */

public class UserDefaultManager {
    private SharedPreferences preferences;
    private final String NAME_KEY = "name";

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
}
