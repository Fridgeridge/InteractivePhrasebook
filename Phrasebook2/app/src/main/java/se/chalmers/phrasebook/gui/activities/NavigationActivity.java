package se.chalmers.phrasebook.gui.activities;

import android.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.PhraseBook;
import se.chalmers.phrasebook.backend.syntax.SyntaxNodeList;
import se.chalmers.phrasebook.gui.FragmentCommunicator;
import se.chalmers.phrasebook.gui.fragments.ChangeLanguageFragment;
import se.chalmers.phrasebook.gui.fragments.DefaultPhrasebooksFragment;
import se.chalmers.phrasebook.gui.fragments.MyPhrasebooksFragment;
import se.chalmers.phrasebook.gui.fragments.NavigationDrawerFragment;
import se.chalmers.phrasebook.gui.fragments.NumeralTranslatorFragment;
import se.chalmers.phrasebook.gui.fragments.PhraseListFragment;
import se.chalmers.phrasebook.gui.fragments.TranslatorFragment;

public class NavigationActivity extends FragmentActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, FragmentCommunicator {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private Fragment mContent;

    public void pageChanged() {
        if (mContent instanceof TranslatorFragment)
            ((TranslatorFragment) mContent).displayDots();
    }

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
                getActionBar().setTitle("Default Phrasebooks");
                switchContent(DefaultPhrasebooksFragment.newInstance(1), "default");
                break;
            case 1:
                getActionBar().setTitle("My Phrasebooks");
                switchContent(new MyPhrasebooksFragment(), "");
                break;
            case 2:
                getActionBar().setTitle("Change Language");
                switchContent(new ChangeLanguageFragment(), "");
                break;
//            case 3:
//                getActionBar().setTitle("Numbers to Words");
//                model.setNumeralCurrentPhrase();
//                System.out.println(model.getCurrentPhrase().getSyntax() + "currentPhraze");
//                switchContent(TranslatorFragment.newInstance("NNumeral"), "");
//                // model.setCurrentPhrase(1);
            default:
                break;
        }

    }

    public void switchContent(Fragment fragment, String message) {
        mContent = fragment;

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(message);
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
//            case 4:
//                mTitle = getString(R.string.title_section4);
//                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    protected void onDestroy() {
        model.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        //Nån konstig bug...
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction().commit();
            //Switches to the previous entry on the stack to ensure
            //that mContent is preserved
            mContent = getSupportFragmentManager().getFragments()
                    .get(getSupportFragmentManager().getFragments().size() - 2);
        }
    }

    @Override
    public void updateSyntax(int optionIndex, SyntaxNodeList l, int childIndex, boolean isAdvanced) {
        model.update(optionIndex, l, childIndex, isAdvanced);
        updateTranslation();
    }

    public void updateTranslation() {
       if (mContent instanceof TranslatorFragment) {
            TranslatorFragment fragment = (TranslatorFragment) mContent;
            fragment.updateTranslation();
        }
    }

    @Override
    public void setPhraseListFragment(String id) {
        if (id != null && !id.isEmpty())
            switchContent(PhraseListFragment.newInstance(id), "");
    }

    @Override
    public void setToTranslationFragment(int id) {
        switchContent(TranslatorFragment.newInstance(id + ""), "");
    }

    @Override
    public boolean removePhrasebook(String id) {
        PhraseBook phraseBook = model.getPhrasebookByTitle(id);
        boolean status = model.removePhrasebook(phraseBook);
        switchContent(new MyPhrasebooksFragment(), "");
        onBackPressed();
        return status;
    }
}
