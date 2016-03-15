package se.chalmers.phrasebook.gui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.gui.adapters.SwipeAdapter;


public class SwipeFragment extends Fragment {

    View view;
    ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_swipe, container, false);

        pager = (ViewPager)view.findViewById(R.id.vpPager);

        pager.setAdapter(new SwipeAdapter(getChildFragmentManager()));

        return view;
    }

}
