package se.chalmers.phrasebook;

import android.app.Application;

/**
 * Created by David on 2016-03-14.
 */
public class App extends Application {
    private static App instance;
    public static App get(){return instance;}


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

}
