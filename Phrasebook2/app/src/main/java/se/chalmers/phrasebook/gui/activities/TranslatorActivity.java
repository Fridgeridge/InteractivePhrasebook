package se.chalmers.phrasebook.gui.activities;



import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
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

        //FAB
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.ic_menu_camera);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this).setContentView(imageView).build();

        //Menu items
        ImageView firstChoice = new ImageView(this);
        firstChoice.setImageResource(R.drawable.ic_menu_gallery);
        ImageView secondChoice = new ImageView(this);
        secondChoice.setImageResource(R.drawable.ic_menu_gallery);
        ImageView thirdChoice = new ImageView(this);
        thirdChoice.setImageResource(R.drawable.ic_menu_gallery);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);

        SubActionButton buttonFirst = itemBuilder.setContentView(firstChoice).build();
        SubActionButton buttonSecond = itemBuilder.setContentView(secondChoice).build();
        SubActionButton buttonThird = itemBuilder.setContentView(thirdChoice).build();

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this).addSubActionView(buttonFirst)
                .addSubActionView(buttonSecond).addSubActionView(buttonThird).attachTo(actionButton).build();

        //ClickListeners
        buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lägg till i frasbok
            }
        });

        buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Ändra språk
            }
        });

        buttonThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Spela upp frasen
            }
        });

    }


    @Override
    public void onOptionSelected(int dataIndex, String label, String currentChoice, String newChoice) {
        model.update(dataIndex, label, currentChoice, newChoice);

        TranslationFragment translationFragment = (TranslationFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_translation);
        translationFragment.updateData();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.fragment_options, new SwipeFragment());
        transaction.commit();

    }
}
