package com.pulpmx.pulpmxapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.pulpmx.pulpmxapp.rss.RssFragment;

public class TabLayoutActivity extends Fragment implements OnTabChangeListener {
    private static final String TAG = "FragmentTabs";

    private View mRoot;
    private TabHost mTabHost;
    private int mCurrentTab;
    public static final String TAB_ARCHIVE = "archive";
    public static final String TAB_DROPS = "drops";
    public static final String TAB_INFO = "info";
    public static final String TAB_RSS = "rss";
    public static final String TAB_HISTORY = "history";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.tabs_fragment, null);
        mTabHost = (TabHost) mRoot.findViewById(android.R.id.tabhost);
        setupTabs();
        return mRoot;
    }

    private void setupTabs() {
        mTabHost.setup(); // you must call this before adding your tabs!
        mTabHost.addTab(newTab(TAB_ARCHIVE, "Archive",
                R.id.archive_tab));
        mTabHost.addTab(newTab(TAB_DROPS, "Drops", R.id.drops_tab));
        mTabHost.addTab(newTab(TAB_RSS, "PulpMX.com", R.id.rss_tab));
        mTabHost.addTab(newTab(TAB_HISTORY, "History", R.id.history_tab));
        mTabHost.addTab(newTab(TAB_INFO, "Info", R.id.info_tab));
    }

    private TabSpec newTab(String tag, String labelId, int tabContentId) {
        Log.d(TAG, "buildTab(): tag=" + tag);

        View indicator = LayoutInflater.from(getActivity()).inflate(
                R.layout.tab,
                (ViewGroup) mRoot.findViewById(android.R.id.tabs), false);
        ((TextView) indicator.findViewById(R.id.text)).setText(labelId);

        TabSpec tabSpec = mTabHost.newTabSpec(tag);
        tabSpec.setIndicator(indicator);
        tabSpec.setContent(tabContentId);
        return tabSpec;
    }

    @Override
    public void onTabChanged(String tabId) {
        Log.d(TAG, "onTabChanged(): tabId=" + tabId);
        if (TAB_ARCHIVE.equals(tabId)) {
            updateTab(tabId, R.id.archive_tab);
            mCurrentTab = 0;
            return;
        }
        if (TAB_DROPS.equals(tabId)) {
            updateTab(tabId, R.id.drops_tab);
            mCurrentTab = 1;
            return;
        }
        if (TAB_RSS.equals(tabId)) {
            updateTab(tabId, R.id.rss_tab);
            mCurrentTab = 2;
            return;
        }
        if (TAB_HISTORY.equals(tabId)) {
            updateTab(tabId, R.id.history_tab);
            mCurrentTab = 3;
            return;
        }
        if (TAB_INFO.equals(tabId)) {
            updateTab(tabId, R.id.info_tab);
            mCurrentTab = 4;
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);

        mTabHost.setOnTabChangedListener(this);
        mTabHost.setCurrentTab(mCurrentTab);
        // manually start loading stuff in the first tab
        updateTab(TAB_ARCHIVE, R.id.archive_tab);
    }

    private void updateTab(String tabId, int placeholder) {
        FragmentManager fm = getFragmentManager();
        if (fm.findFragmentByTag(tabId) == null) {
            if (TAB_ARCHIVE.equals(tabId)) {
                fm.beginTransaction()
                        .replace(placeholder, new ArchiveFragment(), tabId)
                        .commit();
                mCurrentTab = 0;
                return;
            }
            if (TAB_DROPS.equals(tabId)) {
                fm.beginTransaction()
                        .replace(placeholder, new DropsFragment(), tabId)
                        .commit();
                mCurrentTab = 1;
                return;
            }
            if (TAB_RSS.equals(tabId)) {
                fm.beginTransaction().replace(placeholder, new RssFragment(), tabId).commit();
                mCurrentTab = 2;
                return;
            }
            if (TAB_HISTORY.equals(tabId)) {
                fm.beginTransaction().replace(placeholder, new HistoryFragment(), tabId).commit();
                mCurrentTab = 3;
            }
            if (TAB_INFO.equals(tabId)) {
                fm.beginTransaction()
                        .replace(placeholder, new InfoFragment(), tabId)
                        .commit();
                mCurrentTab = 4;
            }
        }
    }


}
