package com.example.android.miwok;

public class Words {
    private final String defaultTranslation;
    private final String miwokTranslation;
    private final int NO_IMAGE = -1;
    private int imageResourceId = NO_IMAGE;
    private int audioResourceId;



    public Words(String defaultTranslation, String miwokTranslation, int imageResourceId,
                 int audioResourceId) {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.imageResourceId = imageResourceId;
        this.audioResourceId = audioResourceId;
    }

    public Words(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        this.defaultTranslation = defaultTranslation;
        this.miwokTranslation = miwokTranslation;
        this.audioResourceId = audioResourceId;
    }
    public int getAudioResourceId() {
        return audioResourceId;
    }

    public String getDefaultTranslation() {
        return defaultTranslation;
    }

    public String getMiwokTranslation() {
        return miwokTranslation;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public boolean hasImage(){
        return imageResourceId != NO_IMAGE;
    }

}
