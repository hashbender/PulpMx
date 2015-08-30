package com.pulpmx.pulpmxapp;



import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.MediaController;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.pulpmx.pulpmxapp.MediaService.LocalBinder;
import com.pulpmx.pulpmxapp.rss.RssFragment;

public class MainNew extends SherlockFragmentActivity implements
MediaController.MediaPlayerControl, OnMp3SelectedListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.main);
	    ActionBar bar = getSupportActionBar();
	    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    ActionBar.Tab archiveTab = bar.newTab().setText("Archives");
	    ActionBar.Tab dropsTab = bar.newTab().setText("Drops");
	    ActionBar.Tab rssTab = bar.newTab().setText("PulpMX.com");
	    ActionBar.Tab historyTab = bar.newTab().setText("History");
	    ActionBar.Tab infoTab = bar.newTab().setText("Info");
	    
	    Fragment archiveFragment = new ArchiveFragment();
	    Fragment dropsFragment = new DropsFragment();
	    Fragment rssFragment = new RssFragment();
	    Fragment historyFragment = new HistoryFragment();
	    Fragment infoFragment = new InfoFragment();
	    
	    archiveTab.setTabListener(new MyTabsListener(archiveFragment));
	    dropsTab.setTabListener(new MyTabsListener(dropsFragment));
	    rssTab.setTabListener(new MyTabsListener(rssFragment));
	    historyTab.setTabListener(new MyTabsListener(historyFragment));
	    infoTab.setTabListener(new MyTabsListener(infoFragment));

	    bar.addTab(archiveTab);
	    bar.addTab(dropsTab);
	    bar.addTab(rssTab);
	    bar.addTab(historyTab);
	    bar.addTab(infoTab);


	}
	private MediaController mediaController;
	@Override
	public void onResume(){
		super.onResume();
	}
	
	protected class MyTabsListener implements ActionBar.TabListener {

	    private Fragment fragment;

	    public MyTabsListener(Fragment fragment) {
	        this.fragment = fragment;
	    }

		@Override
		public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTabSelected(Tab arg0, FragmentTransaction ft) {
			ft.add(R.id.fragment_container, fragment, null);
			if (mediaController != null)
				mediaController.show(0);
		}

		@Override
		public void onTabUnselected(Tab arg0, FragmentTransaction ft) {
			ft.remove(fragment);
		}
	}

	@Override
	protected void onPause() {
		mService.dismissDialog();
		super.onPause();
	}

	@Override
    public boolean onKeyDown(int keyCode, KeyEvent event){
    	if (keyCode == KeyEvent.KEYCODE_MENU) {
			if (mediaController != null){
				if (mediaController.isShowing()){
					mediaController.hide();
				}  else {
					mediaController.show(0);
				}
			}
	    } 
    	if (keyCode == KeyEvent.KEYCODE_BACK){
	    	this.moveTaskToBack(true);
	    	return true;
	    }
    	return super.onKeyDown(keyCode, event);
    }
	
	@Override
	public void onStart() {
		super.onStart();
		Intent intent = new Intent(this, MediaService.class);
		bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
		mediaController = new MediaController(this);
		mediaController.setAnchorView(this.findViewById(R.id.fragment_container));
		mediaController.setMediaPlayer(this);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}
	MediaService mService;
	boolean mBound = false;
	private ServiceConnection mConnection = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName className, IBinder service) {
			// We've bound to LocalService, cast the IBinder and get
			// LocalService instance
			LocalBinder binder = (LocalBinder) service;
			mService = binder.getService();
			mBound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			mBound = false;
		}
	};
	
	@Override
	public boolean canPause() {
		if (mService != null && mBound)
			return mService.canPause();
		return false;
	}

	@Override
	public boolean canSeekBackward() {
		if (mService != null && mBound)
			return mService.canSeekBackward();
		return false;
	}

	@Override
	public boolean canSeekForward() {
		if (mService != null && mBound)
			return mService.canSeekForward();
		return false;
	}

	@Override
	public int getBufferPercentage() {
		if (mService != null && mBound)
			return mService.getBufferPercentage();
		return 0;
	}

	@Override
	public int getCurrentPosition() {
		if (mService != null && mBound)
			return mService.getCurrentPosition();
		return 0;
	}

	@Override
	public int getDuration() {
		if (mService != null && mBound)
			return mService.getDuration();
		return 0;
	}

	@Override
	public boolean isPlaying() {
		if (mService != null && mBound)
			return mService.isPlaying();
		return false;
	}

	@Override
	public void pause() {
		if (mService != null && mBound) {
			mService.pause();
		}
	}

	@Override
	public void seekTo(int arg0) {
		if (mService != null && mBound)
			mService.seekTo(arg0);
	}

	@Override
	public void start() {
		if (mService != null && mBound) {
			mService.start();
		}
	}

	@Override
	public void onMp3Selected(String url) {
		if (mService != null && mBound){
			mediaController.show(0);
			mService.updateUrl(url, new ProgressDialog(this));
		}
	}

}
