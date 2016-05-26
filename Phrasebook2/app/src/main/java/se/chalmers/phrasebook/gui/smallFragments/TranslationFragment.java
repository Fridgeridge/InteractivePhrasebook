package se.chalmers.phrasebook.gui.smallFragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.ImageView;

import android.widget.TextView;


import se.chalmers.phrasebook.App;
import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;

/**
 * Created by matilda on 10/03/16.
 */
public class TranslationFragment extends Fragment {

    private View translateView;
    private Model model;
    private TextView origin,target;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = Model.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        translateView = inflater.inflate(R.layout.small_fragment_translation, container, false);

        origin = (TextView) translateView.findViewById(R.id.origin_phrase);
        target = (TextView) translateView.findViewById(R.id.target_phrase);

        ImageButton button = (ImageButton) translateView.findViewById(R.id.button3);
        final ImageButton favorite = (ImageButton)translateView.findViewById(R.id.imageButton);

        if(model.isFavorite(model.getCurrentPhrase())) {
            favorite.setImageResource(R.drawable.green_star);
        } else {
            favorite.setImageResource(R.drawable.grey_star);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.playCurrentTargetPhrase();
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(model.isFavorite(model.getCurrentPhrase())) {
                    favorite.setImageResource(R.drawable.grey_star);
                    model.getFavorites().removePhrase(model.getCurrentPhrase().getId());
                    model.getCurrentPhrase().setFavorite(false);
                } else {
                    favorite.setImageResource(R.drawable.green_star);
                    model.getFavorites().addPhrase(model.translateToOrigin()
                           , model.getSentenceFromID(model.getCurrentPhrase().getId()));
                    model.getCurrentPhrase().setFavorite(true);
                    System.out.println("Should add");
                }
            }
        });

        return translateView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String originTranslation = model.translateToOrigin();
        String targetTranslation = model.translateToTarget();
        origin.setText(formattingText(originTranslation));
        target.setText(formattingText(targetTranslation));

    }

    public String getTargetTranslation() {
        return target.getText().toString();
    }

    public void updateData(){

        String originTranslation = model.translateToOrigin();
        String targetTranslation = model.translateToTarget();
        origin.setText(formattingText(originTranslation));
        target.setText(formattingText(targetTranslation));

    }

    private String formattingText(String text){

        if(text.length() < 2){
            return text;
        }

        String temp;

        temp = text.substring(0,1).toUpperCase() + text.substring(1);

        temp = temp.replace(" ?", "?");
        temp = temp.replace(" .", ".");
        temp = temp.replace(" !", "!");
        temp = temp.replace(" ,", ",");

        return temp;
    }



}
