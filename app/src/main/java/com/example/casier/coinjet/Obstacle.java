package com.example.casier.coinjet;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * Created by Casier on 25/04/2017.
 */

public class Obstacle implements GameObject {

    private Rect rectangle;
    private Rect rectangle2;
    private int color;
    private Drawable leftWall; // Will be used later with correct drawables
    private Drawable rightWall;

    public Rect getRectangle() {
        return rectangle;
    }

    public Rect getRectangle2() {
        return rectangle2;
    }

    public void incrementY(float y) {
        rectangle.top += y;
        rectangle.bottom += y;

        rectangle2.top += y;
        rectangle2.bottom += y;
    }

    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap) {
        this.color = color;
        rectangle = new Rect(0, startY, startX, startY + rectHeight);
        rectangle2 = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight);

        //leftWall = Constants.CURRENT_CONTEXT.getResources().getDrawable(R.drawable.snakelava_left);
        //rightWall = Constants.CURRENT_CONTEXT.getResources().getDrawable(R.drawable.snakelava_right);
    }

    public boolean playerCollide(RectPlayer player) {
        return Rect.intersects(rectangle, player.getRectangle()) || Rect.intersects(rectangle2, player.getRectangle());
    }

    @Override
    public void update() {
    }
}
