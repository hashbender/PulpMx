package com.pulpmx.pulpmxapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.pulpmx.pulpmxapp.rss.Article;
import com.pulpmx.pulpmxapp.rss.DownloadArticlesTask;
import com.pulpmx.pulpmxapp.rss.RssAdapter;

import java.util.ArrayList;

public class HistoryFragment extends ListFragment {

    LinearLayout linearLayout = null;
    ListView listView = null;
    public static String HISTORY_URL = "http://apptabs.pulpmx.com/history.xml";
    public static String CLASSICS_URL = "http://apptabs.pulpmx.com/y_classics.html";


    @SuppressWarnings("unchecked")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle icicle) {
        LayoutInflater mInflater = (LayoutInflater) getActivity()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) mInflater.inflate(R.layout.rss_layout,
                null);
        listView = (ListView) linearLayout.findViewById(android.R.id.list);

        ArrayList<Article> messages = new ArrayList<Article>();

        new DownloadArticlesTask(messages, listView, getActivity()).execute(HISTORY_URL);

        ListAdapter adapter = new RssAdapter(getActivity(),
                R.layout.rss_list_item, messages);
        listView.setAdapter(adapter);
        return linearLayout;

    }
}
