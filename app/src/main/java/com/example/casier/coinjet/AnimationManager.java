package com.example.casier.coinjet;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Casier on 25/04/2017.
 */

public class AnimationManager {
    private Animation[] animations;
    private int animationIndex = 0;

    public AnimationManager(Animation[] animations) {
        this.animations = animations;
    }

    public void playAnim(int index) {
        for (int i = 0; i < animations.length; i++) {
            if (i == index) {
                if (!animations[animationIndex].isPlaying())
                    animations[i].play();
            } else {
                animations[i].stop();
            }
        }
        animationIndex = index;
    }

    public void draw(Canvas canvas, Rect rect) {
        Log.d("panda", "oui");
        if (animations[animationIndex].isPlaying())
            animations[animationIndex].draw(canvas, rect);
    }

    public void update() {
        if (animations[animationIndex].isPlaying())
            animations[animationIndex].update();
    }
}
