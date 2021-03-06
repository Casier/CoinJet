package com.example.casier.coinjet;

import java.util.ArrayList;

/**
 * Created by Casier on 25/04/2017.
 */

public class TrinketManager {

    private ArrayList<Trinket> trinkets; // List of next trinkets
    private int tWidth; // Trinket width
    private int tHeight; // Trinket height
    private int trinketGap; // Space between two trinkets
    private int trinketObstacleGap; // Space between a trinket & an obstacle

    private int score = 0;

    public TrinketManager(int tWidth, int tHeight, int trinketGap, int trinketObstacleGap) {
        this.tWidth = tWidth;
        this.tHeight = tHeight;
        this.trinketGap = trinketGap;
        this.trinketObstacleGap = trinketObstacleGap;

        trinkets = new ArrayList<>();

        populateTrinkets();
    }

    public boolean playerCollide(RectPlayer player) {
        for (Trinket tk : trinkets) {
            if (tk.playerCollide(player)) {
                score ++;
                int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - tWidth));
                Trinket trinket = new Trinket(xStart, trinkets.get(0).getRectangle().top - tHeight - trinketGap, tWidth, tHeight);
                trinkets.remove(tk);
                trinkets.add(0, trinket);
                return true;
            }
        }
        return false;
    }

    private void populateTrinkets() {
        int currY = -5 * Constants.SCREEN_HEIGHT / 4;
        while (currY < 0) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - tWidth));
            trinkets.add(new Trinket(xStart, currY - trinketObstacleGap, tWidth, tHeight));
            currY += tHeight + trinketGap;
        }
    }

    public void update(float incrY) {

        for (Trinket tk : trinkets) {
            tk.incrementY(incrY);
        }

        if (trinkets.get(trinkets.size() - 1).getRectangle().top >= Constants.SCREEN_HEIGHT) {
            int xStart = (int) (Math.random() * (Constants.SCREEN_WIDTH - tWidth));
            trinkets.add(0, new Trinket(xStart, trinkets.get(0).getRectangle().top - tHeight - trinketGap, tWidth, tHeight));
            trinkets.remove(trinkets.size() - 1);
        }

    }

    public int getScore(){
        return score;
    }

    public ArrayList<Trinket> getTrinkets(){
        return trinkets;
    }
}
