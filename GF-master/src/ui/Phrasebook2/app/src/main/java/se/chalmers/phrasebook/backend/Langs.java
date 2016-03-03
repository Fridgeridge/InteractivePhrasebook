package se.chalmers.phrasebook.backend;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by David on 2016-03-03.
 */
public enum Langs {

    RUSSIAN("Russian","PhrasebookRus"),
    BULGARIAN("Bulgarian","PhrasebookBul"),
    HINDU("Hindu","PhrasebookHin"),
    CATALAN("Catalan","PhrasebookCat"),
    THAI("Thai","PhrasebookTha"),
    PERSIAN("Persian","PhrasebookPes"),
    FINNISH("Finnish","PhrasebookFin"),
    JAPANESE("Japanese","PhrasebookJpn"),
    POLISH("Polish","PhrasebookPol"),
    SWEDISH("Swedish","PhrasebookSwe"),
    URDU("Urdu","PhrasebookUrd"),
    ROMANIAN("Romanian","PhrasebookRon"),
    CHINESE("Chinese", "PhrasebookChi"),
    ITALIAN("Italian","PhrasebookIta"),
    DANISH("Danish","PhrasebookDan"),
    SPANISH("Spanish","PhrasebookSpa"),
    LATVIAN("Latvian","PhrasebookLav"),
    ENGLISH("English","PhrasebookEng"),
    NORWEGIAN("Norwegian","PhrasebookNor"),
    GERMAN("German","PhrasebookGer"),
    DUTCH("Dutch","PhrasebookDut"),
    FRENCH("French","PhrasebookFre");


    private final String language;
    private final String key;



    private static final Map<String, String> langMap = Collections.unmodifiableMap(initializeMap());

    private Langs(String language, String key){
        this.language = language;
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }

    public String getLang(){
        return this.language;
    }

    private static Map<String, String> initializeMap(){
        Map<String, String> map = new HashMap<String, String>();
        for (Langs l : Langs.values()) {
            map.put(l.key, l.language);
        }
        return map;
    }

    public static String getEngName(String key){
        return langMap.get(key);
    }

}
