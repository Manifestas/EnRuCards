package dev.manifest.en_rucards.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Translation {

    @SerializedName("Heading")
    @Expose
    private String heading;
    @SerializedName("Translation")
    @Expose
    private String translation;
    @SerializedName("DictionaryName")
    @Expose
    private String dictionaryName;
    @SerializedName("SoundName")
    @Expose
    private String soundName;
    @SerializedName("Type")
    @Expose
    private Integer type;
    @SerializedName("OriginalWord")
    @Expose
    private String originalWord;

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName;
    }

    public String getSoundName() {
        return soundName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

}
