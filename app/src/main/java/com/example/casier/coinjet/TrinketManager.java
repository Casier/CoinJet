package com.example.casier.coinjet;

import android.graphics.Canvas;

import java.util.ArrayList;

/**
 * Created by Casier on 25/04/2017.
 */

public class TrinketManager {

    private ArrayList<Trinket> trinkets; // List of next trinkets
    private int color; // Trinket color
    private int tWidth; // Trinket width
    private int tHeight; // Trinket height
    private int trinketGap; // Space between two trinkets

    private long startTime; // timestamp for game start
    private long initTime; // complement to startTime allowing us to speed the game

    public TrinketManager(int color, int tWidth, int tHeight, int trinketGap){
        this.color = color;
        this.tWidth = tWidth;
        this.tHeight = tHeight;
        this.trinketGap = trinketGap;

        startTime = initTime = System.currentTimeMillis();

        trinkets = new ArrayList<>();

        populateTrinkets();
    }

    public boolean playerCollide(RectPlayer player){
        for(Trinket tk : trinkets){
            if(tk.playerCollide(player)){
                trinkets.remove(tk);
                return true;
            }
        }
        return false;
    }

    private void populateTrinkets(){
        int currY = -5*Constants.SCREEN_HEIGHT/4;
        while(currY < 0){
            int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH - tWidth));
            // initX, initY, width, height, color
            trinkets.add(new Trinket(xStart, currY, tWidth, tHeight, color));
            currY += tHeight + trinketGap;
        }
    }

    public void update(){
        //region Handle game speed acceleration over time
        int elapsedTime = (int) (System.currentTimeMillis() - startTime);
        startTime = System.currentTimeMillis();
        float speed = (float) Math.sqrt(1 + (startTime - initTime) / 2000.0) * Constants.SCREEN_HEIGHT / 5000.0f;

        for(Trinket tk : trinkets){
            tk.incrementY(speed * elapsedTime);
        }
        //endregion

        if(trinkets.get(trinkets.size() -1).getRectangle().top >= Constants.SCREEN_HEIGHT) { // TODO TEST
            int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH - tWidth));
            trinkets.add(0, new Trinket(xStart, trinkets.get(0).getRectangle().top - tHeight - trinketGap, tWidth, tHeight, color ));
            trinkets.remove(trinkets.size() -1);
        }

    }

    public void draw(Canvas canvas){
        for(Trinket tk : trinkets){
            tk.draw(canvas);
        }
    }
}
