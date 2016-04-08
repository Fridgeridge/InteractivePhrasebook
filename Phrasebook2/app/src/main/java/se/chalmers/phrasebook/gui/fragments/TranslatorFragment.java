package se.chalmers.phrasebook.gui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.smallFragments.SwipeFragment;
import se.chalmers.phrasebook.gui.smallFragments.TranslationFragment;

/**
 * Created by matilda on 04/04/16.
 */
public class TranslatorFragment extends Fragment {

    private String phrase;

    private Model model;

    public TranslatorFragment() {
        // Required empty public constructor
    }

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

        //phrase = getArguments().getString("phrase");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_translator, container, false);

        FragmentTransaction fm = getChildFragmentManager().beginTransaction();
        
        fm.add(R.id.containerfor_translation, new TranslationFragment());
        fm.add(R.id.containerfor_options, new SwipeFragment());

        fm.commit();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Register mMessageReceiver to receive messages.
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).registerReceiver(mMessageReceiver, new IntentFilter("gui_update"));

    }


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            System.out.println("receiving");

            String action = intent.getAction();
            int dataIndex;
            int childIndex;
            String newChoice;

            if(action.equals("gui_update")){

                dataIndex = intent.getIntExtra("optionIndex", -1);
                childIndex = intent.getIntExtra("childIndex", -1);
                model.update(dataIndex, childIndex);


                TranslationFragment translationFragment = (TranslationFragment)getChildFragmentManager().findFragmentById(R.id.containerfor_translation);
                translationFragment.updateData();

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

                transaction.replace(R.id.containerfor_options, new SwipeFragment());
                transaction.commit();


            }else{
                throw new IllegalArgumentException();
            }

        }
    };

    @Override
    public void onPause() {
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext()).unregisterReceiver(mMessageReceiver);
        super.onPause();
    }


}
