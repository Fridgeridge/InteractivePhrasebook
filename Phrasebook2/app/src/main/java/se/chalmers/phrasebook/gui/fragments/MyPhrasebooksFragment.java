package se.chalmers.phrasebook.gui.fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.gui.adapters.PhrasebookButtonAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPhrasebooksFragment extends Fragment {


    public MyPhrasebooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_phrasebooks, container, false);

        //Hämtas nu från array i xml, måste nog hämtas en array någon annanstans ifrån
        Resources res = getResources();
        String[] buttonNames = res.getStringArray(R.array.my_phrasebooks);
        GridView gridView = (GridView) view.findViewById(R.id.standard_gridView);
        gridView.setAdapter(new PhrasebookButtonAdapter(getActivity().getApplicationContext(), buttonNames));

        return view;
    }

}
