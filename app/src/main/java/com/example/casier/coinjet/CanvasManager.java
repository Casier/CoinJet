package com.example.casier.coinjet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import java.util.ArrayList;

/**
 * Created by Casier on 27/04/2017.
 */

public class CanvasManager {

    private Canvas canvas; // optional ?
    private Context context;

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

    private RectPlayer player;
    private float oldLeft = 0.0f;

    //region Animations management
    private AnimationManager animationManager;

    private Animation idle;
    private Animation walkRight;
    private Animation walkLeft;
    //endregion


    public CanvasManager(Context context) {
        this.context = context;

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

        //region Create every Animations
        BitmapFactory bf = new BitmapFactory();
        Bitmap idleImg = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue);
        Bitmap walk1 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk1);
        Bitmap walk2 = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.alienblue_walk2);

        idle = new Animation(new Bitmap[]{idleImg}, 2);
        walkRight = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        Matrix m = new Matrix();
        m.preScale(-1, 1);
        walk1 = Bitmap.createBitmap(walk1, 0, 0, walk1.getWidth(), walk1.getHeight(), m, false);
        walk2 = Bitmap.createBitmap(walk2, 0, 0, walk2.getWidth(), walk2.getHeight(), m, false);

        walkLeft = new Animation(new Bitmap[]{walk1, walk2}, 0.5f);

        animationManager = new AnimationManager(new Animation[]{idle, walkRight, walkLeft});
        //endregion
    }

    public void draw(Canvas canvas) {

        canvas.drawColor(Color.BLACK); // Background color

        //region Core gameplay drawing
        for (Obstacle o : obstacles) {
            canvas.drawRect(o.getRectangle(), obstaclePaint);   // Draw left part of the obstacle
            canvas.drawRect(o.getRectangle2(), obstaclePaint);  // Draw right part of the obstacle
        }
        for (Trinket t : trinkets) {
            Drawable trinketDrawable = ResourcesCompat.getDrawable(context.getResources(), R.drawable.slimblue, null);
            trinketDrawable.setBounds(t.getRectangle());
            trinketDrawable.draw(canvas);
        }

        //endregion

        //region Text drawing
        canvas.drawText("" + trinketScore, Constants.SCREEN_WIDTH - 100, trinketScorePaint.descent() - trinketScorePaint.ascent(), trinketScorePaint);
        canvas.drawText("" + obstacleScore, 50, obstacleScorePaint.descent() - obstacleScorePaint.ascent(), obstacleScorePaint);
        //endregion

        animationManager.draw(canvas, player.getRectangle());
        int state = 0;
        if (player.getRectangle().left - oldLeft > 5)
            state = 1;
        else if (player.getRectangle().left - oldLeft < -5)
            state = 2;

        animationManager.playAnim(state);
        animationManager.update();

        oldLeft = player.getRectangle().left;
    }

    public void setObstacles(ArrayList<Obstacle> obstacles) {
        this.obstacles = obstacles;
    }

    public void setTrinkets(ArrayList<Trinket> trinkets) {
        this.trinkets = trinkets;
    }

    public void setObstacleScore(int score) {
        this.obstacleScore = score;
    }

    public void setTrinketScore(int score) {
        this.trinketScore = score;
    }

    public void setPlayer(RectPlayer player) {
        this.player = player;
    }
}
