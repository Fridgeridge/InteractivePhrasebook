package se.chalmers.phrasebook.backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by David on 2016-03-03.
 */
public enum Langs {

    RUSSIAN("ру́сский","Russian","PhrasebookRus", new Locale("ru")),
    BULGARIAN("български","Bulgarian","PhrasebookBul",new Locale("bg")),
    HINDU("हिन्दी","Hindi","PhrasebookHin",new Locale("hi")),
    CATALAN("Català","Catalan","PhrasebookCat",new Locale("ca")),
    THAI("","Thai","PhrasebookTha",new Locale("th")),
    PERSIAN("","Persian","PhrasebookPes",new Locale("fa")),
    FINNISH("","Finnish","PhrasebookFin",new Locale("fi")),
    JAPANESE("","Japanese","PhrasebookJpn",Locale.JAPANESE),
    POLISH("","Polish","PhrasebookPol",new Locale("pl")),
    SWEDISH("Svenska","Swedish","PhrasebookSwe",new Locale("sv")),
    URDU("","Urdu","PhrasebookUrd",new Locale("ur")),
    ROMANIAN("","Romanian","PhrasebookRon",new Locale("ro")),
    CHINESE("","Chinese", "PhrasebookChi",Locale.CHINESE),
    ITALIAN("","Italian","PhrasebookIta",Locale.ITALIAN),
    DANISH("","Danish","PhrasebookDan",new Locale("da")),
    SPANISH("","Spanish","PhrasebookSpa",new Locale("es")),
    LATVIAN("","Latvian","PhrasebookLav",new Locale("lv")),
    ENGLISH("English","English","PhrasebookEng",Locale.ENGLISH),
    NORWEGIAN("","Norwegian","PhrasebookNor",new Locale("nn")),
    GERMAN("","German","PhrasebookGer",new Locale("de")),
    DUTCH("","Dutch","PhrasebookDut",new Locale("nl")),
    FRENCH("","French","PhrasebookFre",Locale.FRENCH);


    private final String language;
    private final String key;
    private final String nativeSpelling;
    private final Locale ttsLang;

    private static final Map<String, String> langMap = Collections.unmodifiableMap(initializeMap());

    Langs(String spelling, String language, String key, Locale locale){
        this.nativeSpelling = spelling;
        this.language = language;
        this.key = key;
        this.ttsLang = locale;
    }

    public String getKey(){
        return this.key;
    }

    public String getLang(){
        return this.language;
    }

    public Locale getTTS(){
        return this.ttsLang;
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

    public static Langs getLang(String Language){
        for(Langs l : Langs.values()){
           if(l.getKey().equals(Language)) return l;
        }
        return Langs.ENGLISH;
    }

    public static String getKey(String name){
        for(Map.Entry<String,String> e:langMap.entrySet()){
            if(e.getValue().equals(name)) return e.getKey();
        }
        return null;
    }

}
