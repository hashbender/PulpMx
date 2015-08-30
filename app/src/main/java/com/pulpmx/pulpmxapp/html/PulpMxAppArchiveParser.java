package com.pulpmx.pulpmxapp.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PulpMxAppArchiveParser {

    URL url;

    public PulpMxAppArchiveParser(String url) {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public List<Archive> parse() {
        List<Archive> archives = new ArrayList<Archive>();
        Document doc = null;
        try {
            doc = Jsoup.connect(url.toExternalForm()).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (doc == null) return null;

        Elements hrefs = doc.getElementsByTag("a");
        Elements titles = doc.getElementsByClass("title");
        Elements descriptions = doc.getElementsByClass("descript");

        for (int i = 0; i < titles.size(); i++) {
            archives.add(new Archive(titles.get(i).childNode(0).toString(), descriptions.get(i).childNode(0).toString(), hrefs.get(i + 2).childNode(0).toString()));
        }


        return archives;

    }
}
