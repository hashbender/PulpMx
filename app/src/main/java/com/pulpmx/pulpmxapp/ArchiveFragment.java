package com.pulpmx.pulpmxapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ArchiveFragment extends WebViewFragment {

    public static final String MP3_URL = "mp3_url";
    WebView myWebView;
    private OnMp3SelectedListener mListener;

    @Override
    public void onAttach(Activity a) {
        super.onAttach(a);
        try {
            mListener = (OnMp3SelectedListener) a;
        } catch (ClassCastException c) {
            throw new ClassCastException(a.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @SuppressLint("NewApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LayoutInflater mInflater = (LayoutInflater) getActivity()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        myWebView = (WebView) mInflater
                .inflate(R.layout.activity_archive, null);
        myWebView.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this.getActivity();

        myWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                activity.setProgress(progress * 1000);
            }
        });
        myWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode,
                                        String desc, String failingUrl) {
                Toast.makeText(activity, "Network Error", Toast.LENGTH_SHORT)
                        .show();
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.endsWith("mp3")) {
                    mListener.onMp3Selected(url);
                    return true;
                } else return false;
            }

            public void onScaleChanged(WebView view, float old, float newScale) {

            }
        });

        // myWebView.loadUrl("http://www.pulpmx.com/rss.xml");
        // myWebView.loadUrl("http://apptabs.pulpmx.com/drops.html");
        myWebView.loadUrl("http://apptabs.pulpmx.com/index.html");
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int pX = displaymetrics.widthPixels;
        // display.getRealSize(p);
        float webX = (float) 320;
        float scale = (pX / webX) * 100;
        myWebView.setInitialScale((int) scale);
        myWebView.setHorizontalScrollBarEnabled(false);
        // myWebView.loadUrl("http://apptabs.pulpmx.com/history.xml");
        // myWebView.loadUrl("http://apptabs.pulpmx.com/info.html");
        return myWebView;
    }

}