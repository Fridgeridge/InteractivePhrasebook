package se.chalmers.phrasebook.gui.smallFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.gui.adapters.SwipeAdapter;


public class SwipeFragment extends Fragment {

    private View view;
    private ViewPager pager;
    private SwipeAdapter swipeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.small_fragment_swipe, container, false);

        pager = (ViewPager)view.findViewById(R.id.vpPager);

        swipeAdapter = new SwipeAdapter(getChildFragmentManager());

        pager.setAdapter(swipeAdapter);
        return view;
    }

    public boolean isAdvanced() {
        return true;
    }

}
