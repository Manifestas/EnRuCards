package dev.manifest.en_rucards.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Card {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String originalWord;
    private String translate;
    private String soundName;

    public Card(String originalWord, String translate, String soundName) {
        this.originalWord = originalWord;
        this.translate = translate;
        this.soundName = soundName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

    public String getTranslate() {
        return translate;
    }

    public void setTranslate(String translate) {
        this.translate = translate;
    }

    public String getSoundName() {
        return soundName;
    }

    public void setSoundName(String soundName) {
        this.soundName = soundName;
    }
}
