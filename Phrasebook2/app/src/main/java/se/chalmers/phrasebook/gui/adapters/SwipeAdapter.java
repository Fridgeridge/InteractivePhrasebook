package se.chalmers.phrasebook.gui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.TestSentence;
import se.chalmers.phrasebook.gui.fragments.OptionsFragment;
import se.chalmers.phrasebook.gui.fragments.Swipe1;
import se.chalmers.phrasebook.gui.fragments.Swipe2;

/**
 * Created by matilda on 15/03/16.
 */
public class SwipeAdapter extends FragmentPagerAdapter {

        private int pages;
        private Model model;
        private TestSentence testSentence;

        public SwipeAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            model = Model.getInstance();
            testSentence = model.getTestSentence();

            //Går nog definifivt att göra snyggare nån annan dag
            if(testSentence.getNbrOptions() <= 3) {
                pages = 1;
            }else if(testSentence.getNbrOptions() <=6){
                pages = 2;
            }else{
                pages = 3;
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

            if(pages == 1) {
                switch (position) {
                    case 0:
                        return OptionsFragment.newInstance(testSentence.getNbrOptions());
                    default:
                        return null;
                }
            }else if(pages == 2){
                switch (position) {
                    case 0:
                        return OptionsFragment.newInstance(3);
                    case 1:
                        return OptionsFragment.newInstance(testSentence.getNbrOptions() - 3);
                    default:
                        return null;
                }
            }else{
                switch (position) {
                    case 0:
                        return OptionsFragment.newInstance(3);
                    case 1:
                        return OptionsFragment.newInstance(3);
                    case 2:
                        return OptionsFragment.newInstance(testSentence.getNbrOptions() - 6);
                    default:
                        return null;
                }
            }
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            return "Page " + position;
        }


}
