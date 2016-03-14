package se.chalmers.phrasebook.gui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by matilda on 03/03/16.
 */
public class HelperActivity extends Activity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getSharedPreferences("se.chalmers.phrasebook", MODE_PRIVATE);

    }

    @Override
    protected void onResume() {
        super.onResume();

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
            //startActivity(new Intent(HelperActivity.ths , HomeScreenActivity.class));
            Intent intent = new Intent(this, FirstUsageActivity.class);
            startActivity(intent);
            finish();
        }
    }


}
