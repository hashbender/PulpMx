package com.pulpmx.pulpmxapp.rss;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Looper;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

public class DownloadArticlesTask extends
        AsyncTask<String, Void, ArrayList<Article>> {
    ArrayList<Article> aMessages;
    ListView listView;
    Context context;

    public DownloadArticlesTask(ArrayList<Article> msg, ListView lv, Context c) {
        this.aMessages = msg;
        this.listView = lv;
        this.context = c;
    }

    @Override
    protected ArrayList<Article> doInBackground(String... params) {
        PulpMxPullFeedParser pulpParser = new PulpMxPullFeedParser(params[0]);
        try {
            return (ArrayList<Article>) pulpParser.parse();
        } catch (IOException e) {
            Looper.prepare();
            Toast.makeText(context, "Network Connection Error",
                    Toast.LENGTH_LONG).show();
        } catch (XmlPullParserException e) {
            Toast.makeText(context, "Encountered Malformed XML",
                    Toast.LENGTH_LONG).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<Article> result) {
        if (aMessages != null && result != null) {
            aMessages.addAll(result);
            ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
        }
    }
}
