package com.pulpmx.pulpmxapp.rss;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Article implements Comparable<Article> {
    static SimpleDateFormat FORMATTER = new SimpleDateFormat(
            "EEE, dd MMM yyyy HH:mm:ss Z");
    private String title;
    private URL link;
    private String description;
    private Date date;

    public void setDescription(String d) {
        this.description = d;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setLink(String link) {
        try {
            this.link = new URL(link);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public URL getLink() {
        return this.link;
    }

    public String getDate() {
        return FORMATTER.format(this.date);
    }

    public void setDate(String date) {
        // pad the date if necessary
        while (!date.endsWith("00")) {
            date += "0";
        }
        try {
            this.date = FORMATTER.parse(date.trim());
        } catch (java.text.ParseException e) {
            try {
                this.date = FORMATTER.parse(Calendar.getInstance().toString());
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    // sort by date
    public int compareTo(Article another) {
        if (another == null)
            return 1;
        // sort descending, most recent first
        return date.compareTo(another.date);
    }
}
