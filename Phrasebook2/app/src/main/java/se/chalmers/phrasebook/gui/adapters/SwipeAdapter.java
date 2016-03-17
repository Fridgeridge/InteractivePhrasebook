package se.chalmers.phrasebook.gui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.SyntaxTree;
import se.chalmers.phrasebook.gui.fragments.OptionsFragment;

/**
 * Created by matilda on 15/03/16.
 */
public class SwipeAdapter extends FragmentPagerAdapter {

    private int pages;
    private Model model;
    private SyntaxTree phrase;

    public SwipeAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        model = Model.getInstance();
        phrase = model.getCurrentPhrase();

        System.out.println("Number of modular nodes = " + phrase.getOptions().size());

        pages = (int) Math.ceil((double) phrase.getOptions().size() / 3);
        System.out.println("Antalet sidor = " + pages);

    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return pages;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {

        if (pages == 1) {
            switch (position) {
                case 0:
                    return OptionsFragment.newInstance(1);
                default:
                    return null;
            }
        } else if (pages == 2) {
            switch (position) {
                case 0:
                    return OptionsFragment.newInstance(1);
                case 1:
                    return OptionsFragment.newInstance(2);
                default:
                    return null;
            }
        } else {
            switch (position) {
                case 0:
                    return OptionsFragment.newInstance(1);
                case 1:
                    return OptionsFragment.newInstance(2);
                case 2:
                    return OptionsFragment.newInstance(3);
                default:
                    return null;
            }
        }
    }
}
