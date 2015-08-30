package com.pulpmx.pulpmxapp.rss;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.List;

public interface FeedParser {
    List<Article> parse() throws IOException, XmlPullParserException;
}
