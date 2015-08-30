package com.pulpmx.pulpmxapp.html;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pulpmx.pulpmxapp.R;

import java.util.ArrayList;

public class HtmlAdapter extends ArrayAdapter<Archive> {
    private Context context;
    private ArrayList<Archive> items;

    public HtmlAdapter(Context c, int textViewResourceId, ArrayList<Archive> items) {
        super(c, textViewResourceId, items);
        this.context = c;
        this.items = items;
    }

    public View getView(int postion, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater i = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = i.inflate(R.layout.rss_list_item, null);
        }
        final Archive item = items.get(postion);
        if (item != null) {
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO start mediaplayer?
                }
            });
            ImageView iv = (ImageView) view.findViewById(R.id.rss_image_icon);
            String url = item.getDescription();

            TextView title = (TextView) view.findViewById(R.id.rss_title);
            title.setText(item.getTitle());

            TextView desc = (TextView) view.findViewById(R.id.rss_desc);
            desc.setText(item.getDescription());
        }
        return view;
    }
}
