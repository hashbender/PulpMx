package com.pulpmx.pulpmxapp.rss;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;

import java.util.ArrayList;
import java.util.List;

public class PulpMxSaxFeedParser extends BaseFeedParser {
    public PulpMxSaxFeedParser(String feedUrl) {
        super(feedUrl);
    }

    public List<Article> parse() {
        final Article currentArticle = new Article();
        RootElement root = new RootElement("rss");
        final List<Article> articles = new ArrayList<Article>();
        Element channel = root.getChild("channel");
        Element item = channel.getChild("item");
        item.setEndElementListener(new EndElementListener() {
            @Override
            public void end() {
                articles.add(currentArticle);
            }
        });
        item.getChild(TITLE).setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentArticle.setTitle(body);
            }
        });
        item.getChild(LINK).setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentArticle.setDescription(body);
            }
        });
        item.getChild(DESCRIPTION).setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentArticle.setDescription(body);
            }
        });
        item.getChild(PUB_DATE).setEndTextElementListener(new EndTextElementListener() {
            @Override
            public void end(String body) {
                currentArticle.setDate(body);
            }
        });
        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return articles;
    }
}
