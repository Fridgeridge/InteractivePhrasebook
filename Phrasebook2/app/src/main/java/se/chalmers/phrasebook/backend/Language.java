package se.chalmers.phrasebook.backend;

/**
 * Created by Björn on 2016-03-03.
 */
public class Language {

    private String name;
    private String gfKey;
    private String information;

    public Language(String name, String gfKey, String information) {
        this.name = name;
        this.gfKey = gfKey;
        this.information = information;
    }

    public String getInformation() {
        return information;
    }

    public String getName() {
        return name;
    }

    public String getGfKey() {
        return gfKey;
    }
}
