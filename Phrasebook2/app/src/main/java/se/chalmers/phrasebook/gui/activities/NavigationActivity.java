package se.chalmers.phrasebook.gui.activities;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;


import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.fragments.ChangeLanguageFragment;
import se.chalmers.phrasebook.gui.fragments.DefaultPhrasebooksFragment;
import se.chalmers.phrasebook.gui.fragments.MyPhrasebooksFragment;
import se.chalmers.phrasebook.gui.fragments.NavigationDrawerFragment;
import se.chalmers.phrasebook.gui.fragments.PhraseListFragment;
import se.chalmers.phrasebook.gui.fragments.TranslatorFragment;

public class NavigationActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private Fragment mContent;

    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        model = Model.getInstance();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#4db6ac")));


        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        switch (position) {
            case 0:
                switchContent(DefaultPhrasebooksFragment.newInstance(1));
                break;
            case 1:
                switchContent(new MyPhrasebooksFragment());
                break;
            case 2:
                switchContent(new ChangeLanguageFragment());
                break;
        }

    }

    public void switchContent(Fragment fragment) {
        mContent = fragment;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();

    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public void onResume() {
        super.onResume();

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("phrasebook_event"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("phrase_list_event"));
    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            System.out.println("receiving");

            String action = intent.getAction();
            String message;

            if(action.equals("phrasebook_event")){
                message = intent.getStringExtra("message");
                switchContent(PhraseListFragment.newInstance(message));
            }else if(action.equals("phrase_list_event")){
                message = intent.getStringExtra("message");
                switchContent(TranslatorFragment.newInstance(message));
            }else{
                throw new IllegalArgumentException();
            }

        }
    };

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }

}