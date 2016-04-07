package se.chalmers.phrasebook.gui.fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import java.util.ArrayList;

import se.chalmers.phrasebook.App;
import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.backend.PhraseBook;
import se.chalmers.phrasebook.gui.adapters.PhrasebookButtonAdapter;
import se.chalmers.phrasebook.gui.smallFragments.DialogFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPhrasebooksFragment extends Fragment {
    private Model model;
    private FloatingActionButton fab;

    public MyPhrasebooksFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        model.getInstance();
        View view = inflater.inflate(R.layout.fragment_phrasebooks, container, false);

        //Hämtas nu från array i xml, måste nog hämtas en array någon annanstans ifrån

        GridView gridView = (GridView) view.findViewById(R.id.standard_gridView);
        gridView.setAdapter(new PhrasebookButtonAdapter(getActivity().getApplicationContext()));

        ImageView image = new ImageView(App.get());
        image.setImageResource(R.drawable.ic_menu_camera);

        fab = new FloatingActionButton.Builder(getActivity()).setContentView(image).build();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ADD");
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        fab.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        fab.setVisibility(View.GONE);
    }

}
