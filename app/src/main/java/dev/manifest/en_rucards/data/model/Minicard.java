package dev.manifest.en_rucards.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Minicard {

    @SerializedName("SourceLanguage")
    @Expose
    private Integer sourceLanguage;
    @SerializedName("TargetLanguage")
    @Expose
    private Integer targetLanguage;
    @SerializedName("Heading")
    @Expose
    private String heading;
    @SerializedName("Translation")
    @Expose
    private Translation translation;
    @SerializedName("SeeAlso")
    @Expose
    private List<Object> seeAlso = null;

    public Integer getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(Integer sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public Integer getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(Integer targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }

    public List<Object> getSeeAlso() {
        return seeAlso;
    }

    public void setSeeAlso(List<Object> seeAlso) {
        this.seeAlso = seeAlso;
    }

}
