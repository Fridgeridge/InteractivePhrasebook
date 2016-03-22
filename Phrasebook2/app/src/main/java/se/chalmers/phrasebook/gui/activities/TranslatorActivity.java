package se.chalmers.phrasebook.gui.activities;



import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.fragments.OptionsFragment;
import se.chalmers.phrasebook.gui.fragments.SpinnerFragment;
import se.chalmers.phrasebook.gui.fragments.SwipeFragment;
import se.chalmers.phrasebook.gui.fragments.TranslationFragment;

public class TranslatorActivity extends FragmentActivity implements SpinnerFragment.OnChangeListener{

    TranslationFragment translationFragment;
    SwipeFragment swipeFragment;
    private Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);

        model = Model.getInstance();

        translationFragment = new TranslationFragment();
        swipeFragment = new SwipeFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.fragment_translation, translationFragment);
        ft.add(R.id.fragment_options, swipeFragment);
        ft.commit();
    }


    @Override
    public void onOptionSelected(int dataIndex, String label, String currentChoice, String newChoice) {
        model.update(dataIndex, label, currentChoice, newChoice);

        TranslationFragment translationFragment = (TranslationFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_translation);
        translationFragment.updateData();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.remove(swipeFragment);
        transaction.add(R.id.fragment_options, new SwipeFragment());
        transaction.commit();

    }
}
