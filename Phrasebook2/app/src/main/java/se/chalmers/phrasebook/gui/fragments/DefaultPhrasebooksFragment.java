package se.chalmers.phrasebook.gui.fragments;

import android.app.FragmentManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.gui.adapters.PhrasebookButtonAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link DefaultPhrasebooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefaultPhrasebooksFragment extends Fragment {

    private static final String ARG_SECTION_NBR = "ARG_SECTION_NBR";

    private int sectionNumber;

    public DefaultPhrasebooksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber Parameter 1.
     * @return A new instance of fragment DefaultPhrasebooksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DefaultPhrasebooksFragment newInstance(int sectionNumber) {
        DefaultPhrasebooksFragment fragment = new DefaultPhrasebooksFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NBR, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sectionNumber = getArguments().getInt(ARG_SECTION_NBR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_phrasebooks, container, false);

        Resources res = getResources();
        GridView gridView = (GridView) view.findViewById(R.id.standard_gridView);
        gridView.setAdapter(new PhrasebookButtonAdapter(getActivity().getApplicationContext()));

        return view;

    }


}
