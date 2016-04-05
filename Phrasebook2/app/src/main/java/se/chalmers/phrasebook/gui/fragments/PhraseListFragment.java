package se.chalmers.phrasebook.gui.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.activities.TranslatorActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhraseListFragment extends Fragment {

    private Model model;
    private ArrayList<String> phrases;
    Context context;
    private String title;

    public PhraseListFragment() {
        // Required empty public constructor
    }

    public static PhraseListFragment newInstance(String title) {
        PhraseListFragment fragment = new PhraseListFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = Model.getInstance();

        title = getArguments().getString("title");

        phrases = new ArrayList<>();
        phrases.addAll(model.getSentences().values());

        context = getActivity().getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_phrase_list, container, false);

        ArrayAdapter adapter = new ArrayAdapter<String>(context, R.layout.phrase_list_item, phrases);

        final ListView phraseListView = (ListView) view.findViewById(R.id.phrase_listView);
        phraseListView.setAdapter(adapter);

        phraseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                model.setCurrentPhrase((String) phraseListView.getItemAtPosition(position));

                sendMessage((String) phraseListView.getItemAtPosition(position));
            }
        });


        return view;
    }

    private void sendMessage(String phrase){

        System.out.println("sending");

        Intent intent = new Intent("phrase_list_event");
        // add data
        intent.putExtra("message", "data");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

}