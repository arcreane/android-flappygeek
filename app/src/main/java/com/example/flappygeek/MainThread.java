package com.example.flappygeek;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
    private SurfaceHolder holder;
    private GeekView geekview;
    private boolean surfaceReady = false;
    private Thread drawThread;
    private boolean drawingActive = false;
    private static final String LOGTAG = "surface";
    private static final int MAX_FRAME_TIME = (int) (1000.0 / 60.0);

    public MainThread(SurfaceHolder holder, GeekView geekview) {
        this.holder = holder;
        this.geekview = geekview;
        surfaceReady = true;
    }

    public void run() {
        Log.d(LOGTAG, "Draw thread started");
        long frameStartTime;
        long frameTime;

        /*
         * In order to work reliable on Nexus 7, we place ~500ms delay at the start of drawing thread
         * (AOSP - Issue 58385)
         */
        if (android.os.Build.BRAND.equalsIgnoreCase("google") && android.os.Build.MANUFACTURER.equalsIgnoreCase("asus") && android.os.Build.MODEL.equalsIgnoreCase("Nexus 7")) {
            Log.w(LOGTAG, "Sleep 500ms (Device: Asus Nexus 7)");
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}
        }

        while (drawingActive) {
            if (holder == null) {
                return;
            }

            frameStartTime = System.nanoTime();
            Canvas canvas = holder.lockCanvas();
            if (canvas != null) {
                try {
                    synchronized (holder) {
                        geekview.tick();
                        geekview.render(canvas);
                    }
                } finally {

                    holder.unlockCanvasAndPost(canvas);
                }
            }

            // calculate the time required to draw the frame in ms
            frameTime = (System.nanoTime() - frameStartTime) / 1000000;

            if (frameTime < MAX_FRAME_TIME){
                try {
                    Thread.sleep(MAX_FRAME_TIME - frameTime);
                } catch (InterruptedException e) {
                    // ignore
                }
            }

        }
        Log.d(LOGTAG, "Draw thread finished");
    }

    public void startDrawThread(){
        if (surfaceReady && drawThread == null){
            drawThread = new Thread(this, "Draw thread");
            drawingActive = true;
            drawThread.start();
        }
    }


}
