package com.example.zhz256.startservice;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Button;
import android.widget.Toast;

public class DemoService extends Service {

    SharedPreferences sharedPreferences;

    public DemoService() {
    }


    final class MyThread implements Runnable{
        int startId;
        public MyThread(int startId){
            this.startId = startId;
        }
        @Override
        public void run(){
            synchronized (this){
                try {
                    wait(15000);

                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                stopSelf(startId);
                sharedPreferences = getSharedPreferences("info_pair", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", "Harry");
                editor.apply();
            }
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        Toast.makeText(DemoService.this, "Service started", Toast.LENGTH_SHORT).show();
        Thread thread = new Thread(new MyThread(startId));
        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy(){
        Toast.makeText(DemoService.this, "Service stopped", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
