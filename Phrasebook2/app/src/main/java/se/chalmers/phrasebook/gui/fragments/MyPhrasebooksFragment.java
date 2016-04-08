package se.chalmers.phrasebook.gui.fragments;


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
import se.chalmers.phrasebook.gui.adapters.PhrasebookButtonAdapter;
import se.chalmers.phrasebook.gui.smallFragments.AddPhrasebookDialog;

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
        model = Model.getInstance();
        View view = inflater.inflate(R.layout.fragment_phrasebooks, container, false);

        TextView text = (TextView) view.findViewById(R.id.my_phrasebooks_textView);

        if(model.getMyPhrasebookTitles().size() > 0) {
            GridView gridView = (GridView) view.findViewById(R.id.standard_gridView);
            gridView.setAdapter(new PhrasebookButtonAdapter(getActivity().getApplicationContext(), model.getMyPhrasebookTitles()));
            text.setVisibility(view.GONE);
        }else{
            text.setText("Please add a phrasebook by clicking the button below");
        }

        ImageView image = new ImageView(App.get());
        image.setImageResource(R.drawable.ic_menu_camera);

        fab = new FloatingActionButton.Builder(getActivity()).setContentView(image).build();

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
        fab.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        fab.setVisibility(View.GONE);
    }

}
