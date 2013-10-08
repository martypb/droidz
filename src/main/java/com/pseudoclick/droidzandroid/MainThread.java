package com.pseudoclick.droidzandroid;

import android.util.Log;
import android.view.SurfaceHolder;
import android.graphics.Canvas;

public class MainThread extends Thread {

    private static final String TAG = MainThread.class.getSimpleName();

    private SurfaceHolder surfaceHolder;
    private MainGamePanel gamePanel;
    private boolean running;
    public void setRunning(boolean running) {
        this.running = running;
    }

    public MainThread(SurfaceHolder surfaceHolder, MainGamePanel gamePanel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        Canvas canvas;
        long tickCount = 0L;
        Log.d(TAG, "Starting game loop");
        while (running) {
            canvas = null;
            tickCount++;
            //try locking the canvas for exclusive pixel editing on the surface
            try{
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    // update game state
                    // render state to the screen
                    this.gamePanel.onDraw(canvas);
                }
            } finally{
                // in case of exception the canvas is not left in an inconsistent state
                if (canvas != null){
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
        Log.d(TAG, "Game loop executed " + tickCount + " times");
    }
}