package se.chalmers.phrasebook.gui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.smallFragments.OptionsFragment;

/**
 * Created by matilda on 15/03/16.
 */
public class SwipeAdapter extends FragmentPagerAdapter {

    private int pages;
    private Model model;

    public SwipeAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        model = Model.getInstance();
        if(model.getCurrentPhrase().hasAdvOptions()) {
            pages = 2;
        } else {
            pages = 1;
        }
    }


    // Returns total number of pages
    @Override
    public int getCount() {
        return pages;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {

        switch(position){
            case 0:
                return OptionsFragment.newInstance(1, false);
            case 1:
                return OptionsFragment.newInstance(2, model.getCurrentPhrase().isAdvActivated());
            default:
                return null;
        }

    }

}
