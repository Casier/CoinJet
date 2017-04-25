package com.example.casier.coinjet;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

/**
 * Created by Casier on 25/04/2017.
 */

public class GameplayScene implements Scene {

    private Rect r = new Rect();

    private RectPlayer player;
    private Point playerPoint;

    private ObstacleManager obstacleManager;
    private TrinketManager trinketManager;

    private boolean movingPlayer = false;

    private boolean gameOver = false;
    private long gameOverTime;

    //region Important variables

    public static int PLAYER_WIDTH = 100;     // Ratio will be saved
    public static int PLAYER_HEIGHT = 100;    // Ratio will be saved
    public static int PLAYER_COLOR = Color.rgb(255, 0, 0);

    public static int PLAYER_GAP = 350;         // Represents the space between left and right "walls"
    public static int OBSTACLE_GAP = 400;       // Represents the space between two "walls"
    public static int OBSTACLE_HEIGHT = 80;     // Represents the thickness of the "walls"
    public static int OBSTACLE_COLOR = Color.YELLOW;

    public static int TRINKET_COLOR = Color.BLUE;
    public static int TRINKET_WIDTH = 80;
    public static int TRINKET_HEIGHT = 80;
    public static int TRINKET_GAP = 400;


    //endregion

    public GameplayScene(){
        player = new RectPlayer(new Rect(0, 0, PLAYER_WIDTH, PLAYER_HEIGHT), PLAYER_COLOR);
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(PLAYER_GAP, OBSTACLE_GAP, OBSTACLE_HEIGHT, OBSTACLE_COLOR);
        trinketManager = new TrinketManager(TRINKET_COLOR, TRINKET_WIDTH, TRINKET_HEIGHT, TRINKET_GAP);
    }

    public void reset(){
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(350, 350, 75, Color.YELLOW);
        movingPlayer = false;
    }

    @Override
    public void update() {
        if(!gameOver){
            player.update(playerPoint);
            obstacleManager.update();
            if(obstacleManager.playerCollide(player)){
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
            trinketManager.update();
            if(trinketManager.playerCollide(player)){
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.BLACK);

        player.draw(canvas);
        obstacleManager.draw(canvas);
        trinketManager.draw(canvas);

        if(gameOver){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.CYAN);
            drawCenterText(canvas, paint, "Game Over");
        }
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void recieveTouch(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY()))
                    movingPlayer = true;
                if(gameOver && System.currentTimeMillis() - gameOverTime >= 2000){
                    reset();
                    gameOver = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(movingPlayer && !gameOver)
                    playerPoint.set((int)event.getX(), (int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
        }
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text){
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f - r.height() / 2f - r.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
