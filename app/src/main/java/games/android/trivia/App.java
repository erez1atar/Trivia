package games.android.trivia;

import android.app.Application;
import android.content.Intent;
import android.provider.CalendarContract;

/**
 * Created by Erez on 04/10/2017.
 */

public class App extends Application {
    private static App Instance = null;
    private static ResourcesManager resourcesManager;
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

    public static App getInstance() {
        return Instance;
    }
}
