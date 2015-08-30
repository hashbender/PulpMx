package com.pulpmx.pulpmxapp.html;

public class Archive {
    private String mp3Url;
    private String description;
    private String Title;

    public Archive(String title, String desc, String url) {
        Title = title;
        description = desc;
        mp3Url = url;
    }

    public String getMp3Url() {
        return mp3Url;
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }


}
