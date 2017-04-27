package com.example.casier.coinjet;

import android.content.Context;
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

    private Context context;

    private Rect r = new Rect();

    private RectPlayer player;
    private Point playerPoint;

    private ObstacleManager obstacleManager;
    private TrinketManager trinketManager;
    private CanvasManager canvasManager;

    private boolean movingPlayer = false;

    private boolean gameOver = false;
    private long gameOverTime;

    private long startTime;
    private long initTime;
    private int elapsedTime;
    private float speed;

    public static int PLAYER_WIDTH = 100;     // Ratio will be saved
    public static int PLAYER_HEIGHT = 100;    // Ratio will be saved

    public static int PLAYER_GAP = 350;         // Represents the space between left and right "walls"
    public static int OBSTACLE_GAP = 400;       // Represents the space between two "walls"
    public static int OBSTACLE_HEIGHT = 80;     // Represents the thickness of the "walls"

    public static int TRINKET_WIDTH = 80;
    public static int TRINKET_HEIGHT = 80;
    public static int TRINKET_GAP = 400; // Represent the space between two trinkets
    public static int TRINKET_OBSTACLE_GAP = 200; // represent the space between a trinket and an obstacle


    public float incrY;
    public SpeedModifier speedModifier;
    public long startModifierTime = 0; // Represents the timestamp when the speedModifier starts

    public enum SpeedModifier {
        HALVE_SPEED,
        THIRD_SPEED,
        DOUBLE_SPEED,
        TRIPLE_SPEED
    }

    public GameplayScene(Context context) {
        this.context = context;
        player = new RectPlayer(new Rect(0, 0, PLAYER_WIDTH, PLAYER_HEIGHT));
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(PLAYER_GAP, OBSTACLE_GAP, OBSTACLE_HEIGHT);
        trinketManager = new TrinketManager(TRINKET_WIDTH, TRINKET_HEIGHT, TRINKET_GAP, TRINKET_OBSTACLE_GAP);

        canvasManager = new CanvasManager(context);

        startTime = initTime = System.currentTimeMillis();
    }

    public void reset() {
        playerPoint = new Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4);
        player.update(playerPoint);
        obstacleManager = new ObstacleManager(PLAYER_GAP, OBSTACLE_GAP, OBSTACLE_HEIGHT);
        trinketManager = new TrinketManager(TRINKET_WIDTH, TRINKET_HEIGHT, TRINKET_GAP, TRINKET_OBSTACLE_GAP);
        movingPlayer = false;

        canvasManager = new CanvasManager(context);

        startTime = initTime = System.currentTimeMillis();
    }

    @Override
    public void update() {
        if (!gameOver) {
            calculateSpeed();
            player.update(playerPoint);
            canvasManager.setPlayer(player);
            if (obstacleManager.playerCollide(player)) {
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
            if (trinketManager.playerCollide(player)) {
                // TODO : Handle speed modification
                //speedModifier = SpeedModifier.HALVE_SPEED;
                //startModifierTime = System.currentTimeMillis();
            }

        }
    }

    public void calculateSpeed() {
        // TODO : use that
        if (startModifierTime != 0 && System.currentTimeMillis() - startModifierTime < 2000) {
            switch (speedModifier) {
                case HALVE_SPEED:
                    break;
                case THIRD_SPEED:
                    break;
                case DOUBLE_SPEED:
                    break;
                case TRIPLE_SPEED:
                    break;
            }
        } else {
            speedModifier = null;
            startModifierTime = 0;

            elapsedTime = (int) (System.currentTimeMillis() - startTime);
            startTime = System.currentTimeMillis();
            speed = (float) Math.sqrt(1 + (startTime - initTime) / 2000) * Constants.SCREEN_HEIGHT / 5000.0f;
            incrY = speed * elapsedTime;
        }

        obstacleManager.update(incrY);
        trinketManager.update(incrY);
    }

    @Override
    public void draw(Canvas canvas) {

        canvasManager.setObstacles(obstacleManager.getObstacles());
        canvasManager.setTrinkets(trinketManager.getTrinkets());
        canvasManager.setObstacleScore(obstacleManager.getScore());
        canvasManager.setTrinketScore(trinketManager.getScore());
        canvasManager.setPlayer(player);

        canvasManager.draw(canvas);

        Paint topPaint = new Paint();
        topPaint.setColor(Color.rgb(128, 128, 128));
        topPaint.setAlpha(70);

        if (gameOver) {
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
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!gameOver && player.getRectangle().contains((int) event.getX(), (int) event.getY()))
                    movingPlayer = true;
                if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset();
                    gameOver = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (movingPlayer && !gameOver)
                    playerPoint.set((int) event.getX(), (int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
        }
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
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
