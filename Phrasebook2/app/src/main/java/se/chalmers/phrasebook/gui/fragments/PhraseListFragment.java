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

/**
 * A simple {@link Fragment} subclass.
 */
public class PhraseListFragment extends Fragment {

    protected Model model;
    protected ArrayList<String> phrases;
    Context context;
    private String title;


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
        phrases.addAll(model.getSentencesInCurrentPhrasebook());
        //phrases.addAll(model.getSentences().values());

        context = getActivity().getApplicationContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_phrase_list, container, false);

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, phrases);

        final ListView phraseListView = (ListView) view.findViewById(R.id.phrase_listView);
        phraseListView.setAdapter(adapter);

        phraseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                model.setCurrentPhrase(position);

                //sendMessage((String) phraseListView.getItemAtPosition(position));
                sendMessage(position);
            }
        });


        return view;
    }

    private void sendMessage(String phrase) {


        Intent intent = new Intent("phrase_list_event");
        // add data
        intent.putExtra("message", "data");
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void sendMessage(int position) {

        Intent intent = new Intent("phrase_list_event");
        // add data
        intent.putExtra("position", position);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }


}
