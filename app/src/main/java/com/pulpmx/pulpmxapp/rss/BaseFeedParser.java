package com.pulpmx.pulpmxapp.rss;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class BaseFeedParser implements FeedParser {

    static final String PUB_DATE = "pubDate";
    static final String DESCRIPTION = "description";
    static final String LINK = "link";
    static final String TITLE = "title";
    static final String ITEM = "item";

    final URL feedUrl;

    protected BaseFeedParser(String feedUrl) {
        try {
            this.feedUrl = new URL(feedUrl);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private class DownloadRssTask extends AsyncTask<URL, Void, InputStream> {
        InputStream ioStream;

        public DownloadRssTask(InputStream inputStream) {
            ioStream = inputStream;
        }

        protected InputStream doInBackground(URL... urls) {
            URL urldisplay = urls[0];
            try {
                ioStream = urldisplay.openConnection().getInputStream();
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return ioStream;
        }
    }

    protected InputStream getInputStream() throws IOException {
        return feedUrl.openConnection().getInputStream();
    }
}
