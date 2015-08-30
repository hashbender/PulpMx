package com.pulpmx.pulpmxapp.rss;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.pulpmx.pulpmxapp.html.Archive;
import com.pulpmx.pulpmxapp.html.PulpMxAppArchiveParser;

import java.util.ArrayList;

public class DownloadArchivesTask extends AsyncTask<String, Void, ArrayList<Archive>> {
    ArrayList<Archive> aMessages;
    ListView listView;
    Context context;

    public DownloadArchivesTask(ArrayList<Archive> msg, ListView lv, Context c) {
        this.aMessages = msg;
        this.listView = lv;
        this.context = c;
    }

    @Override
    protected ArrayList<Archive> doInBackground(String... params) {
        Looper.prepare();
        PulpMxAppArchiveParser pulpParser = new PulpMxAppArchiveParser(params[0]);
        return (ArrayList<Archive>) pulpParser.parse();
    }


    @Override
    protected void onPostExecute(ArrayList<Archive> result) {
        if (aMessages != null && result != null) {
            aMessages.addAll(result);
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }
}
