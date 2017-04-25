package com.example.casier.coinjet;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Casier on 25/04/2017.
 */

public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void recieveTouch(MotionEvent event);
}
