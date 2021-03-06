package com.example.casier.coinjet;

import android.graphics.Rect;

/**
 * Created by Casier on 25/04/2017.
 */

public class Trinket implements GameObject {

    private Rect rectangle;

    public Rect getRectangle() {
        return rectangle;
    }

    public void incrementY(float y) {
        rectangle.top += y;
        rectangle.bottom += y;
    }

    public Trinket(int startX, int startY, int width, int height) {
        rectangle = new Rect(startX, startY, startX + width, startY + height);
    }

    public boolean playerCollide(RectPlayer player) {
        return Rect.intersects(rectangle, player.getRectangle());
    }

    @Override
    public void update() {

    }
}
