package se.chalmers.phrasebook.gui.activities;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import java.util.Locale;

import se.chalmers.phrasebook.R;
import se.chalmers.phrasebook.backend.Model;
import se.chalmers.phrasebook.gui.fragments.SpinnerFragment;
import se.chalmers.phrasebook.gui.fragments.SwipeFragment;
import se.chalmers.phrasebook.gui.fragments.TranslationFragment;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

public class TranslatorActivity extends FragmentActivity implements SpinnerFragment.OnChangeListener{

    TranslationFragment translationFragment;
    SwipeFragment swipeFragment;
    private Model model;
    private TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translator);

        model = Model.getInstance();

        translationFragment = new TranslationFragment();
        swipeFragment = new SwipeFragment();

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.container_translation, translationFragment);
        ft.add(R.id.container_options, swipeFragment);
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
                changeActivity(AddToPhrasebookActivity.class);
            }
        });

        buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeActivity(LanguageActivity.class);

            }
        });
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        buttonThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSpeak = translationFragment.getTargetTranslation();
                Toast.makeText(getApplicationContext(), toSpeak, Toast.LENGTH_SHORT).show();
                t1.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
        });

    }


    @Override
    public void onOptionSelected(int dataIndex, String label, String newChoice) {
        model.update(dataIndex, label, newChoice);

        TranslationFragment translationFragment = (TranslationFragment)getSupportFragmentManager().findFragmentById(R.id.container_translation);
        translationFragment.updateData();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.replace(R.id.container_options, new SwipeFragment());
        transaction.commit();

    }

    private void changeActivity(Class activity){
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, PhraseListActivity.class);
        startActivity(intent);
    }

}
