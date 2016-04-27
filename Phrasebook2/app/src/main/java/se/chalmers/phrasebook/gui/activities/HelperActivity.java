package se.chalmers.phrasebook.gui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import se.chalmers.phrasebook.App;
import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.fragments.MyPhrasebooksFragment;




/**
 * Created by matilda on 03/03/16.
 */
public class HelperActivity extends Activity {

    SharedPreferences prefs = null;
    private static long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 //       setContentView(R.layout.activity_splash);

        time = System.currentTimeMillis();

        Model.getInstance();
        prefs = getSharedPreferences(App.get().getString(R.string.preference_file_key), MODE_PRIVATE);

        getActionBar().hide();



    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Startup time","Milliseconds: "+ (System.currentTimeMillis()-time));
        if (prefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            //strat  DataActivity beacuase its your app first run
            // using the following line to edit/commit prefs
            prefs.edit().putBoolean("firstrun", false).commit();
            //startActivity(new Intent(HelperActivity.ths , FirstUsageActivity.class));
            Intent intent = new Intent(this, FirstUsageActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            startActivity(new Intent(HelperActivity.this , NavigationActivity.class));
            finish();
        }
    }


}
