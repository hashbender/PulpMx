package com.pulpmx.pulpmxapp.rss;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PulpMxPullFeedParser extends BaseFeedParser {
    public PulpMxPullFeedParser(String feedUrl) {
        super(feedUrl);
    }

    public static String CHANNEL = "channel";

    public List<Article> parse() throws IOException, XmlPullParserException {
        List<Article> articles = null;
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(this.getInputStream(), null);
        int eventType = parser.getEventType();
        Article currentArticle = null;
        boolean done = false;
        while (eventType != XmlPullParser.END_DOCUMENT && !done) {
            String name;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    articles = new ArrayList<Article>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase(ITEM)) {
                        currentArticle = new Article();
                    } else if (currentArticle != null) {
                        if (name.equalsIgnoreCase(LINK)) {
                            currentArticle.setLink(parser.nextText());
                        } else if (name.equalsIgnoreCase(DESCRIPTION)) {
                            currentArticle.setDescription(parser.nextText());
                        } else if (name.equalsIgnoreCase(PUB_DATE)) {
                            currentArticle.setDate(parser.nextText());
                        } else if (name.equalsIgnoreCase(TITLE)) {
                            currentArticle.setTitle(parser.nextText());
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase(ITEM) && currentArticle != null) {
                        if (articles != null) articles.add(currentArticle);
                    } else if (name.equalsIgnoreCase(CHANNEL)) {
                        done = true;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return articles;
    }
}
