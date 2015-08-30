package com.pulpmx.pulpmxapp.rss;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pulpmx.pulpmxapp.R;

import java.io.InputStream;
import java.util.ArrayList;

public class RssAdapter extends ArrayAdapter<Article> {
    private Context context;
    private ArrayList<Article> items;

    public RssAdapter(Context context, int textViewResourceId,
                      ArrayList<Article> items) {
        super(context, textViewResourceId, items);
        this.context = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater i = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = i.inflate(R.layout.rss_list_item, null);
        }
        final Article item = items.get(position);
        if (item != null) {
            view.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getLink().toExternalForm()));
                    context.startActivity(browserIntent);
                }
            });
            ImageView iv = (ImageView) view.findViewById(R.id.rss_image_icon);
            String url = item.getDescription();
            if (url.contains("src=")) {
                int start = url.indexOf("src=\"") + 5;
                int end = url.indexOf("\"", start);
                url = url.substring(start, end);
                new DownloadImageTask(iv).execute(url);
            }

            TextView title = (TextView) view.findViewById(R.id.rss_title);
            title.setText(item.getTitle());

            TextView desc = (TextView) view.findViewById(R.id.rss_desc);
            desc.setText(item.getDescription().substring(
                    item.getDescription().indexOf("<p>") + 3));

            TextView address = (TextView) view.findViewById(R.id.rss_address);
            address.setText(item.getLink().toExternalForm());
        }
        return view;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
