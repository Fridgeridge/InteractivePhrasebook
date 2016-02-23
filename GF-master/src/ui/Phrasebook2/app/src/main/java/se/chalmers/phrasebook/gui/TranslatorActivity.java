package se.chalmers.phrasebook.gui;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.gui.listners.OnSwipeTouchListener;

public class TranslatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        String[] example = new String[3];
        View my_view = this.findViewById(android.R.id.optionsListView);
        my_view.setOnTouchListener(new OnSwipeTouchListener(TranslatorActivity.this) {
            @Override
            public void onSwipeLeft() {
                System.out.println("Swiping left");
            }
        });
        example[0] = "Chicken";
        example[1] = "Apple";
        example[2] = "Car";

        OptionsFragment fragment = OptionsFragment.newInstance(example);
        fragmentTransaction.add(R.id.textView , fragment);//TODO Change layout to correct one
        fragmentTransaction.commit();
    }
}
