package com.example.mycounter;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;

public class VolumeContentObserver extends ContentObserver {

    private static final String TAG = "VolumeObserver";
    private Context context;
    private AudioManager audioManager;
    private int previousVolume;

    public VolumeContentObserver(Context context, Handler handler) {
        super(handler);
        this.context = context;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        previousVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        onChange(selfChange, null);
    }

    @Override
    public void onChange(boolean selfChange, Uri uri) {
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        System.out.print("onChange");
        if (currentVolume != previousVolume) {
            Log.d(TAG, "Volume changed from " + previousVolume + " to " + currentVolume);
            // Implement your counter logic here
            // ここで音量が変化したことだけを通知するよう変更してコミット
            ((MainActivity) context).updateCounterBasedOnVolumeChange(currentVolume - previousVolume);
            previousVolume = currentVolume;
        }
    }
}
