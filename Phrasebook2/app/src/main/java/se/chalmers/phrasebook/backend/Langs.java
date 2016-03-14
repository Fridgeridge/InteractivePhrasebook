package se.chalmers.phrasebook.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by David on 2016-03-03.
 */
public enum Langs {

    RUSSIAN("ру́сский","Russian","PhrasebookRus"),
    BULGARIAN("български","Bulgarian","PhrasebookBul"),
    HINDU("हिन्दी","Hindi","PhrasebookHin"),
    CATALAN("Català","Catalan","PhrasebookCat"),
    THAI("","Thai","PhrasebookTha"),
    PERSIAN("","Persian","PhrasebookPes"),
    FINNISH("","Finnish","PhrasebookFin"),
    JAPANESE("","Japanese","PhrasebookJpn"),
    POLISH("","Polish","PhrasebookPol"),
    SWEDISH("Svenska","Swedish","PhrasebookSwe"),
    URDU("","Urdu","PhrasebookUrd"),
    ROMANIAN("","Romanian","PhrasebookRon"),
    CHINESE("","Chinese", "PhrasebookChi"),
    ITALIAN("","Italian","PhrasebookIta"),
    DANISH("","Danish","PhrasebookDan"),
    SPANISH("","Spanish","PhrasebookSpa"),
    LATVIAN("","Latvian","PhrasebookLav"),
    ENGLISH("English","English","PhrasebookEng"),
    NORWEGIAN("","Norwegian","PhrasebookNor"),
    GERMAN("","German","PhrasebookGer"),
    DUTCH("","Dutch","PhrasebookDut"),
    FRENCH("","French","PhrasebookFre");


    private final String language;
    private final String key;
    private final String nativeSpelling;


    private static final Map<String, String> langMap = Collections.unmodifiableMap(initializeMap());

    Langs(String spelling, String language, String key){
        this.nativeSpelling = spelling;
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

    public static ArrayList<String> getLanguages() {
        ArrayList<String> list = new ArrayList<>();
        Langs[] langs = Langs.values();

        for (int i = 0; i < langs.length; i++) {
            if (Langs.getEngName(langs[i].getKey()) != null)
                list.add(Langs.getEngName(langs[i].getKey()));
        }
        return list;
    }

    public static String getEngName(String key){
        return langMap.get(key);
    }

    public static String getKey(String name){
        for(Map.Entry<String,String> e:langMap.entrySet()){
            if(e.getValue().equals(name)) return e.getKey();
        }
        return null;
    }
}
