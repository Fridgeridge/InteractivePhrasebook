package se.chalmers.phrasebook.backend;

import android.app.Application;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

import se.chalmers.phrasebook.App;


/**
 * Created by David on 2016-04-05.
 */
class TTSHandler {

    private final TextToSpeech tts;

    public TTSHandler(Application app) {
        tts = new TextToSpeech(app.getApplicationContext(), new InitListener());
    }

    public void setTargetTTSLanguage(Languages target) {
        int e = tts.setLanguage(target.getTTS());
    }

    public void playSentence(String sentence) {
        int status = tts.speak(sentence, TextToSpeech.QUEUE_FLUSH, null);

        if(status!=TextToSpeech.SUCCESS)
            Toast.makeText(App.get(),"Failed to play translation",Toast.LENGTH_SHORT).show();
    }


    public void destroy() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    public void getSupportedLanguages() {
        for (Locale l : Locale.getAvailableLocales())
            Log.i("TTS", (l.toLanguageTag() + "  : " + l.getLanguage() + "  :   " + l.getDisplayLanguage()));
    }


    private class InitListener implements TextToSpeech.OnInitListener {

        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                Log.d("TTS", "Successfully initiated TTS");
            } else {
                Log.d("TTS", "Failed to initiate TTS");
            }
        }

    }

}
