package se.chalmers.phrasebook.gui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    private SwipeFragment swiper;
    private View view;

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
        //TODO Quickfix for TTS language. TTS defaults to English despite target language being different.
        model.setTargetLanguage(model.getTargetLang().getKey());
    }

    public void displayDots() {
        if(swiper.isAdvanced(view.findViewById(R.id.containerfor_options))) {
            ((ImageView)view.findViewById(R.id.firstDot))
                    .setImageResource(R.drawable.abc_btn_radio_to_on_mtrl_000);
            ((ImageView)view.findViewById(R.id.secondDot))
                    .setImageResource(R.drawable.abc_btn_radio_to_on_mtrl_015);
        } else {
            ((ImageView)view.findViewById(R.id.secondDot))
                    .setImageResource(R.drawable.abc_btn_radio_to_on_mtrl_000);
            ((ImageView)view.findViewById(R.id.firstDot))
                    .setImageResource(R.drawable.abc_btn_radio_to_on_mtrl_015);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_translator, container, false);

        final FragmentTransaction fm = getChildFragmentManager().beginTransaction();

        swiper = new SwipeFragment();
        fm.replace(R.id.containerfor_translation, new TranslationFragment());
        fm.replace(R.id.containerfor_options, new SwipeFragment());

        fm.commit();

        if(!model.getCurrentPhrase().hasAdvOptions()) {
            view.findViewById(R.id.firstDot).setVisibility(View.GONE);
            view.findViewById(R.id.secondDot).setVisibility(View.GONE);
        }

        ImageView imageView = new ImageView(App.get());
        imageView.setImageResource(R.drawable.orange_fab_2);


        floatingActionButton = new FloatingActionButton.Builder(getActivity()).setContentView(imageView).build();
        floatingActionButton.setBackgroundResource(R.drawable.orange_fab_2);
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
        floatingActionButton.detach();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if(floatingActionButton != null) {
            floatingActionButton.setVisibility(View.GONE);
            floatingActionButton.detach();
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        updateTranslation();
        floatingActionButton.setVisibility(View.VISIBLE);
    }

}
