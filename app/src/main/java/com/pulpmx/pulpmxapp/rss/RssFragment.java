package com.pulpmx.pulpmxapp.rss;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.pulpmx.pulpmxapp.R;

import java.util.ArrayList;

public class RssFragment extends ListFragment {

    ListView listView = null;
    LinearLayout linearLayout = null;
    public static String PULPMX_URL = "http://www.pulpmx.com/rss.xml";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle icicle) {
        LayoutInflater mInflater = (LayoutInflater) getActivity()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        linearLayout = (LinearLayout) mInflater.inflate(R.layout.rss_layout,
                null);
        listView = (ListView) linearLayout.findViewById(android.R.id.list);

        ArrayList<Article> messages = new ArrayList<Article>();

        new DownloadArticlesTask(messages, listView, getActivity()).execute(PULPMX_URL);

        ListAdapter adapter = new RssAdapter(getActivity(),
                R.layout.rss_list_item, messages);
        listView.setAdapter(adapter);
        return linearLayout;
    }
}