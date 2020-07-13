package com.rexthonyy.animatedintegertextviewcounter.helpers;

import android.os.Handler;

public abstract class Reloader {
    private boolean isPaused = false;
    private boolean isStopped = false;

    private Handler handler;
    public Reloader(final int time){
        handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try{
                    if(!isPaused){
                        execute();
                    }
                }finally {
                    if(!isStopped){
                        handler.postDelayed(this, time);
                    }else{
                        handler = null;
                    }
                }
            }
        };
        handler.postDelayed(runnable, time);
    }

    public void onPause(){
        isPaused = true;
    }

    public void onResume(){
        isPaused = false;
    }

    public void onDestroy(){
        isStopped = true;
    }

    public abstract void execute();
}
