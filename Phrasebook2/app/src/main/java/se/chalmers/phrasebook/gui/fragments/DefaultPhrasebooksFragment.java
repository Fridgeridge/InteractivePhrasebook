package se.chalmers.phrasebook.gui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.FragmentCommunicator;
import se.chalmers.phrasebook.gui.adapters.PhrasebookButtonAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link DefaultPhrasebooksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DefaultPhrasebooksFragment extends Fragment {

    private static final String ARG_SECTION_NBR = "ARG_SECTION_NBR";

    private Model model;

    private FragmentCommunicator mCallback;

    public DefaultPhrasebooksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (FragmentCommunicator) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DefaultPhrasebooksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DefaultPhrasebooksFragment newInstance() {
        DefaultPhrasebooksFragment fragment = new DefaultPhrasebooksFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NBR, 1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = Model.getInstance();
        if (getArguments() != null) {
            int sectionNumber = getArguments().getInt(ARG_SECTION_NBR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_phrasebooks, container, false);

        TextView text = (TextView) view.findViewById(R.id.my_phrasebooks_textView);
        text.setVisibility(View.GONE);

        GridView gridView = (GridView) view.findViewById(R.id.standard_gridView);
        gridView.setAdapter(new PhrasebookButtonAdapter(getActivity().getApplicationContext(),mCallback, model.getDefaultPhrasebookTitles()));

        return view;

    }


}
