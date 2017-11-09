package games.android.trivia;

import android.app.Application;

import games.android.trivia.Analytics.FirebaseAnalytics;
import games.android.trivia.DataBase.DBManager;
import games.android.trivia.DataBase.ScoreCursorAdapter;
import games.android.trivia.GlobalHighScore.FirebaseManager;
import games.android.trivia.HighScores.HallOfFameBuilder;
import games.android.trivia.HighScores.WinnerAdapter;

/**
 * Created by Erez on 04/10/2017.
 */

public class App extends Application {
    private static App Instance = null;
    private static ResourcesManager resourcesManager = null;
    private static DBManager dbManager = null;
    private static ScoreCursorAdapter cursorAdapter = null;
    private static WinnerAdapter winAdapter = null;
    private static FirebaseManager firebaseManager = null;
    private static UserDefaultManager userDefaultManager = null;
    private static FirebaseAnalytics firebaseAnalytics = null;

    public App()
    {
        Instance = this;
    }

    public static ResourcesManager getResourcesManager() {
        if(resourcesManager ==  null){
            resourcesManager = new ResourcesManager();
        }
        return resourcesManager;
    }

    public static DBManager getDataBase()
    {
        if(null == dbManager)
        {
            dbManager = new DBManager(App.Instance);
        }
        return dbManager;
    }

    public static ScoreCursorAdapter getCursorAdapter()
    {
        if(null == dbManager)
        {
            getDataBase();
        }
        if(null == cursorAdapter)
        {
            cursorAdapter = new ScoreCursorAdapter(App.Instance, dbManager.getAllHighScores(), 0);
        }
        return cursorAdapter;
    }

    public static WinnerAdapter getWinAdapter()
    {
        if(null == winAdapter)
        {
            winAdapter = new WinnerAdapter(App.Instance, R.layout.winner, HallOfFameBuilder.getWinners());
        }
        return winAdapter;
    }

    public static App getInstance() {
        return Instance;
    }

    public static FirebaseManager getFirebaseManager() {
        if(firebaseManager == null){
            firebaseManager = new FirebaseManager();
        }
        return firebaseManager;
    }

    public static UserDefaultManager getUserDefaultManager() {
        if(userDefaultManager == null){
            userDefaultManager = new UserDefaultManager();
        }
        return userDefaultManager;
    }

    public static FirebaseAnalytics getAnalyticsManager() {
        if(firebaseAnalytics == null){
            firebaseAnalytics = new FirebaseAnalytics();
        }
        return firebaseAnalytics;
    }
}
