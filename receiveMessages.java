package com.example.rahulkanojiya.profilechanger;

import android.app.AlertDialog;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Process;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;



public class receiveMessages extends BroadcastReceiver{

    String gen,sil,vib;

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedpref = context.getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        final AudioManager adm = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);

        gen = sharedpref.getString("general","null");
        sil = sharedpref.getString("silent","null");
        vib = sharedpref.getString("vibrate","null");
        Log.v("qscvhg",gen);
        Log.v("qscvhg",sil);
        Log.v("qscvhg",vib);

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Log.v("qscvhg","Receiver called");
            Bundle bundle = intent.getExtras();
            SmsMessage[] msgs = null;
            String msgbody =null;
            if(bundle != null){

                try{

                    Object []pdus = (Object [])bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int  i=0 ;i<msgs.length ;i++) {
                        msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                        msgbody = msgs[i].getMessageBody();
                    }

                    if(msgbody.equals(gen)){
                        Log.v("qscvhg","general");
                        adm.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                    }
                    else if(msgbody.equals(sil)){
                        Log.v("qscvhg","silent");
                        adm.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                    }
                    else if(msgbody.equals(vib)){
                        Log.v("qscvhg","vibrate");
                        adm.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    }

                }
                catch(Exception e) {
                    Log.d("Exception occured", e.getMessage());
                }
            }


        }



    }
}
/*
public class receiveMessages extends IntentService {

    BroadcastReceiver broadcast;

    public receiveMessages(){
        super("receiveMessages");
    }
    @Override
    public void onCreate(){
        super.onCreate();
        broadcast  = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
                    Log.v("qscvhg","Receiver called");
                    Bundle bundle = intent.getExtras();
                    SmsMessage[] msgs = null;
                    String msgbody =null;
                    if(bundle != null){

                        try{

                            Object []pdus = (Object [])bundle.get("pdus");
                            msgs = new SmsMessage[pdus.length];
                            for(int  i=0 ;i<msgs.length ;i++) {
                                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                                msgbody = msgs[i].getMessageBody();
                            }
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, msgbody, duration);
                            toast.show();

                        }
                        catch(Exception e) {
                            Log.d("Exception occured", e.getMessage());
                        }
                    }


                }
            }
        };
        Log.v("qscvhg","oncreate starts");
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(broadcast,filter);
        Log.v("qscvhg","oncreate ends");
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        unregisterReceiver(broadcast);
        Log.v("qscvhg","Service Destroyed");
    }



    @Override
    protected void onHandleIntent(Intent intent) {



        try {
            Thread.sleep(500000);
        } catch (InterruptedException e) {
            // Restore interrupt status.
            Thread.currentThread().interrupt();
        }

    }
}

*/
















/*
public class receiveMessages extends Service{

    private Looper mServiceLooper;
    private serviceHandler mServiceHandler;
    SharedPreferences sharedpref;
    private class serviceHandler extends Handler {

        public serviceHandler(Looper looper) {
            super(looper);
        }
    }

    @Override
    public void onCreate(){
        BroadcastReceiver broadcast;
        sharedpref = getApplicationContext().getSharedPreferences("MyPrefs",Context.MODE_PRIVATE);
        final String gen = sharedpref.getString("general","null");
        final String sil = sharedpref.getString("silent","null");
        final String vib = sharedpref.getString("vibrate","null");
        Log.v("qscvhg",gen);
        Log.v("qscvhg",sil);
        Log.v("qscvhg",vib);
        HandlerThread thread = new HandlerThread("service", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        final AudioManager adm = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        broadcast  = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
                    Log.v("qscvhg","Receiver called");
                    Bundle bundle = intent.getExtras();
                    SmsMessage[] msgs = null;
                    String msgbody =null;
                    if(bundle != null){

                        try{

                            Object []pdus = (Object [])bundle.get("pdus");
                            msgs = new SmsMessage[pdus.length];
                            for(int  i=0 ;i<msgs.length ;i++) {
                                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                                msgbody = msgs[i].getMessageBody();
                            }
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, msgbody, duration);
                            toast.show();
                              if(msgbody.equals(gen)){
                                  Log.v("qscvhg","general");
                                  adm.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                              }
                              else if(msgbody.equals(sil)){
                                  Log.v("qscvhg","silent");
                                  adm.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                              }
                              else if(msgbody.equals(vib)){
                                  Log.v("qscvhg","vibrate");
                                  adm.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                              }

                        }
                        catch(Exception e) {
                            Log.d("Exception occured", e.getMessage());
                        }
                    }


                }
            }
        };
        Log.v("qscvhg","oncreate starts");
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(broadcast,filter);
        Log.v("qscvhg","oncreate ends");
        mServiceLooper = thread.getLooper();
        mServiceHandler = new serviceHandler(mServiceLooper);

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}
*/


