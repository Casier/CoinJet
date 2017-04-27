package com.example.casier.coinjet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

/**
 * Created by Casier on 27/04/2017.
 */

public class CanvasManager {

    private Canvas canvas; // optional ?

    // Define canvas elements color
    public static int TRINKET_COLOR = Color.BLUE;
    public static int TRINKET_SCORE_COLOR = Color.BLUE;
    public static int TRINKET_SCORE_SIZE = 100;
    public static int OBSTACLE_COLOR = Color.YELLOW;
    public static int OBSTACLE_SCORE_COLOR = Color.YELLOW;
    public static int OBSTACLE_SCORE_SIZE = 100;

    private int trinketScore;
    private int obstacleScore;

    private ArrayList<Obstacle> obstacles;
    private ArrayList<Trinket> trinkets;

    private Paint trinketPaint;
    private Paint trinketScorePaint;
    private Paint obstaclePaint;
    private Paint obstacleScorePaint;

    public CanvasManager(){
        //region create every Paint objects needed
        trinketPaint = new Paint();
        trinketPaint.setColor(TRINKET_COLOR);

        trinketScorePaint = new Paint();
        trinketScorePaint.setColor(TRINKET_SCORE_COLOR);
        trinketScorePaint.setTextSize(TRINKET_SCORE_SIZE);

        obstaclePaint = new Paint();
        obstaclePaint.setColor(OBSTACLE_COLOR);

        obstacleScorePaint = new Paint();
        obstacleScorePaint.setColor(OBSTACLE_SCORE_COLOR);
        obstacleScorePaint.setTextSize(OBSTACLE_SCORE_SIZE);
        //endregion
    }

    public void draw(Canvas canvas) {

        canvas.drawColor(Color.BLACK); // Background color

        //region Core gameplay drawing
        for (Obstacle o : obstacles) {
            canvas.drawRect(o.getRectangle(), obstaclePaint);   // Draw left part of the obstacle
            canvas.drawRect(o.getRectangle2(), obstaclePaint);  // Draw right part of the obstacle
        }
        for (Trinket t : trinkets)
            canvas.drawRect(t.getRectangle(), trinketPaint);
        //endregion

        //region Text drawing
        canvas.drawText("" + trinketScore, Constants.SCREEN_WIDTH - 100, trinketScorePaint.descent() - trinketScorePaint.ascent(), trinketScorePaint);
        canvas.drawText("" + obstacleScore, 50, obstacleScorePaint.descent() - obstacleScorePaint.ascent(), obstacleScorePaint);
        //endregion
    }

    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public void setTrinkets(ArrayList<Trinket> trinkets) {
        this.trinkets = trinkets;
    }

    public void setObstacleScore(int score){
        this.obstacleScore = score;
    }

    public void setTrinketScore(int score){
        this.trinketScore = score;
    }
}
