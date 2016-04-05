package se.chalmers.phrasebook.backend;

import android.app.Application;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

import se.chalmers.phrasebook.App;


/**
 * Created by David on 2016-04-05.
 */
public class TTSHandler {

    private TextToSpeech tts;
    private Model model;


    public TTSHandler() {
        Application app = App.get();
        model = Model.getInstance();
        tts = new TextToSpeech(app.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    Locale l = model.getTargetLang().getTTS();
                    tts.setLanguage(l);

                }
            }
        });
    }


}
