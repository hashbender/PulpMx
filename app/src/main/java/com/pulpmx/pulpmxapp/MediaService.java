package com.pulpmx.pulpmxapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.io.IOException;

public class MediaService extends Service implements OnPreparedListener {
    public static final int PULP_ID = 600;
    private static MediaPlayer mediaPlayer;
    LocalBinder mBinder;
    private static final int MSG_MP_RELEASE = 601;

    @Override
    public IBinder onBind(Intent arg0) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mBinder = new LocalBinder();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                return false;
            }
        });
    }


    public class LocalBinder extends Binder {
        MediaService getService() {
            // Return this instance of LocalService so clients can call public
            // methods
            return MediaService.this;
        }
    }

    private Handler handler = new Handler();

    public void onPrepared(final MediaPlayer mediaPlayer) {
        Log.d("ArchiveFragment", "onPrepared");
        handler.post(new Runnable() {
            public void run() {
                if (progressDialog != null) {
                    progressDialog.cancel();
                }
                addNotificaton();
                mediaPlayer.start();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            removeNotification();
            mediaPlayer.stop();
            mediaPlayer.reset();
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    // --MediaPlayerControl
    // methods----------------------------------------------------
    public void start() {
        mediaPlayer.start();
        addNotificaton();
    }

    public void pause() {
        mediaPlayer.pause();
        removeNotification();
    }

    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    public void seekTo(int i) {
        mediaPlayer.seekTo(i);
    }

    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    public int getBufferPercentage() {
        return 0;
    }

    public boolean canPause() {
        return true;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    ProgressDialog progressDialog;

    public void updateUrl(String url, ProgressDialog pd) {
        if (mediaPlayer == null)
            return;
        progressDialog = pd;
        progressDialog.setTitle("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();
        mediaPlayer.stop();
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(url);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mediaPlayer.prepareAsync();
    }

    public void addNotificaton() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this).setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle("Playing Pulp MX").setContentText("Pulp Mx");
        Intent resultIntent = new Intent(this, MainNew.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(PULP_ID, mBuilder.build());
    }

    public void removeNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(PULP_ID);
    }

    public void dismissDialog() {
        if (progressDialog != null) progressDialog.cancel();
    }

}
