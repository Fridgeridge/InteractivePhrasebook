package se.chalmers.phrasebook;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TranslatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String[] example = new String[3];
        example[0] = "Chicken";
        example[1] = "Apple";
        example[2] = "Car";

        OptionsFragment fragment = OptionsFragment.newInstance(example);
        fragmentTransaction.add(R.id.scrollView, fragment);
        fragmentTransaction.commit();
    }
}
