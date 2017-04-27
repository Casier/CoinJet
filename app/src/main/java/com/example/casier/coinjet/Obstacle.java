package com.example.casier.coinjet;

import android.graphics.Rect;

/**
 * Created by Casier on 25/04/2017.
 */

public class Obstacle implements GameObject {

    private Rect rectangle;
    private Rect rectangle2;

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

    public Obstacle(int rectHeight, int startX, int startY, int playerGap) {
        rectangle = new Rect(0, startY, startX, startY + rectHeight);
        rectangle2 = new Rect(startX + playerGap, startY, Constants.SCREEN_WIDTH, startY + rectHeight);
    }

    public boolean playerCollide(RectPlayer player) {
        return Rect.intersects(rectangle, player.getRectangle()) || Rect.intersects(rectangle2, player.getRectangle());
    }

    @Override
    public void update() {
    }
}
