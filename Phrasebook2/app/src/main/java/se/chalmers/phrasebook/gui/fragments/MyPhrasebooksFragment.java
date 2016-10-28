package se.chalmers.phrasebook.gui.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import se.chalmers.phrasebook.App;
import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.FragmentCommunicator;
import se.chalmers.phrasebook.gui.adapters.MyPhrasebookButtonAdapter;
import se.chalmers.phrasebook.gui.smallFragments.AddPhrasebookDialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPhrasebooksFragment extends Fragment {
    private FloatingActionButton fab;
    private FragmentCommunicator mCallback;


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Model model = Model.getInstance();
        View view = inflater.inflate(R.layout.fragment_phrasebooks, container, false);

        TextView text = (TextView) view.findViewById(R.id.my_phrasebooks_textView);

        if (model.getMyPhrasebookTitles().size() > 0) {
            GridView gridView = (GridView) view.findViewById(R.id.standard_gridView);
            gridView.setAdapter(new MyPhrasebookButtonAdapter(getActivity().getApplicationContext(), mCallback, model.getMyPhrasebookTitles()));
            text.setVisibility(View.GONE);
        } else {
            text.setText("Please add a phrasebook by clicking the button below.");
        }


        ImageView image = new ImageView(App.get());
        image.setImageResource(R.drawable.ic_add_white_24dp);

        fab = new FloatingActionButton.Builder(getActivity()).setContentView(image).build();
        fab.setBackgroundResource(R.drawable.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                AddPhrasebookDialog dialogFragment = new AddPhrasebookDialog();
                dialogFragment.show(fm, "dialog_add_phrasebook");
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (fab != null)
            fab.detach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (fab != null)
            fab.detach();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (fab != null)
            fab.setVisibility(View.VISIBLE);
    }

}
