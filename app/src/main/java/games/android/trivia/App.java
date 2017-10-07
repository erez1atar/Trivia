package games.android.trivia;

import android.app.Application;
import android.content.Intent;
import android.provider.CalendarContract;

import games.android.trivia.DataBase.DBManager;
import games.android.trivia.DataBase.ScoreCursorAdapter;
import games.android.trivia.HighScores.HallOfFameBuilder;
import games.android.trivia.HighScores.WinnerAdapter;

/**
 * Created by Erez on 04/10/2017.
 */

public class App extends Application {
    private static App Instance = null;
    private static ResourcesManager resourcesManager;
    private static DBManager dbManager;
    private static ScoreCursorAdapter cursorAdapter;
    private static WinnerAdapter winAdapter;

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
}
