package dev.manifest.en_rucards.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = @Index(value = {"original_word"}, unique = true))
public class Card {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "original_word")
    private String originalWord;

    private String translate;

    @ColumnInfo(name = "sound_name")
    private String soundName;

    @Ignore
    public Card(String originalWord) {
        this.originalWord = originalWord;
    }

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
