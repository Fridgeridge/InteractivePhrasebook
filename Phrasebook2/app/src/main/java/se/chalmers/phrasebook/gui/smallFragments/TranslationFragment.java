package se.chalmers.phrasebook.gui.smallFragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        translateView = inflater.inflate(R.layout.fragment_translation, container, false);

        origin = (TextView) translateView.findViewById(R.id.origin_phrase);
        target = (TextView) translateView.findViewById(R.id.target_phrase);

        return translateView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String originTranslation = model.translateToOrigin();
        String targetTranslation = model.translateToTarget();
        origin.setText(originTranslation);
        target.setText(targetTranslation);

    }

    public String getTargetTranslation() {
        return target.getText().toString();
    }

    public void updateData(){

        String originTranslation = model.translateToOrigin();
        String targetTranslation = model.translateToTarget();
        origin.setText(originTranslation);
        target.setText(targetTranslation);

    }



}
