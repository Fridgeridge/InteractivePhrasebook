package se.chalmers.phrasebook.gui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;

import se.chalmers.phrasebook.App;
import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.smallFragments.DialogFragment;
import se.chalmers.phrasebook.gui.smallFragments.SwipeFragment;
import se.chalmers.phrasebook.gui.smallFragments.TranslationFragment;

/**
 * Created by matilda on 04/04/16.
 */
public class TranslatorFragment extends Fragment {
    protected Model model;
    protected FloatingActionButton floatingActionButton;

    public static TranslatorFragment newInstance(String phrase) {
        TranslatorFragment fragment = new TranslatorFragment();
        Bundle args = new Bundle();
        args.putString("phrase", phrase);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = Model.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_translator, container, false);

        final FragmentTransaction fm = getChildFragmentManager().beginTransaction();

        fm.replace(R.id.containerfor_translation, new TranslationFragment());
        fm.replace(R.id.containerfor_options, new SwipeFragment());

        fm.commit();


        ImageView imageView = new ImageView(App.get());
        imageView.setImageResource(R.drawable.parlira);

        floatingActionButton = new FloatingActionButton.Builder(getActivity()).setContentView(imageView).build();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                DialogFragment dialogFragment = new DialogFragment();
                dialogFragment.show(fm, "dialog_fragment");
            }
        });


        return view;
    }

    public void updateTranslation() {
        TranslationFragment translationFragment = (TranslationFragment) getChildFragmentManager().findFragmentById(R.id.containerfor_translation);
        if (translationFragment != null)
            translationFragment.updateData();
    }

    @Override
    public void onPause() {
        super.onPause();
        floatingActionButton.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onPause();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        floatingActionButton.setVisibility(View.GONE);
    }

    @Override
    public void onResume(){
        super.onResume();
        floatingActionButton.setVisibility(View.VISIBLE);
    }

}
