package com.example.casier.coinjet;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Casier on 25/04/2017.
 */

public class Animation {
    private Bitmap[] frames;
    private int frameIndex;
    private boolean isPlaying = false;
    private float frameTime;
    private long lastFrame;

    public boolean isPlaying() {
        return isPlaying;
    }

    public void play(){
        isPlaying = true;
        frameIndex = 0;
        lastFrame = System.currentTimeMillis();
    }
    public void stop(){
        isPlaying = false;
        frameIndex = 0;
    }

    public Animation(Bitmap[] frames, float animTime){
        this.frames = frames;
        frameIndex = 0;
        frameTime = animTime / frames.length;

        lastFrame = System.currentTimeMillis();
    }

    public void draw(Canvas canvas, Rect destination){
        if(!isPlaying)
            return;

        scaleRect(destination);

        canvas.drawBitmap(frames[frameIndex], null, destination, new Paint());
    }

    public void update(){
        if(!isPlaying)
            return;

        if(System.currentTimeMillis() - lastFrame > frameTime * 1000){
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }

    private void scaleRect(Rect rect){
        float ratio = (float) frames[frameIndex].getWidth() / frames[frameIndex].getHeight();
        if(rect.width() > rect.height()){
            rect.left = rect.right - (int) (rect.height() * ratio);
        } else {
            rect.top = rect.bottom - (int) (rect.width() * (1/ratio));
        }
    }
}
