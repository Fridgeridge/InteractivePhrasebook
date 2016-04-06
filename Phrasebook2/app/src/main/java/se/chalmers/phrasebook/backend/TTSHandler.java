package se.chalmers.phrasebook.backend;

import android.app.Application;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;


/**
 * Created by David on 2016-04-05.
 */
public class TTSHandler {

    private TextToSpeech tts;

    public TTSHandler(Application app) {
        tts = new TextToSpeech(app.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    Locale l = Locale.ENGLISH;
                    tts.setLanguage(l);

                }
            }
        });

    }

    public void setTargetTTSLanguage(Langs target) {
        int e = tts.setLanguage(target.getTTS());
        System.out.println(e);
    }

    public void playSentence(String sentence) {
        tts.speak(sentence, TextToSpeech.QUEUE_FLUSH, null);
    }


    public void getSupportedLanguages() {
        for (Locale l : Locale.getAvailableLocales())
            Log.i("TTS", (l.toLanguageTag() + "  : " + l.getLanguage() + "  :   " + l.getDisplayLanguage()));
    }


}
