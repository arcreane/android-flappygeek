package com.example.flappygeek;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;


public class GeekView extends SurfaceView implements SurfaceHolder.Callback{

    private Context context;
    private Bitmap PLAYER_BMP = BitmapFactory.decodeResource(getResources(), R.drawable.cartman);

    private Bitmap Background = BitmapFactory.decodeResource(getResources(), R.drawable.bus);

    int width = Background.getWidth();
    int height = Background.getHeight();
    int newWidth = 1080;
    int newHeight = 1920;
    float scaleWidth = ((float) newWidth) / width;
    float scaleHeight = ((float) newHeight) / height;

    Matrix toto = new Matrix();

    toto.


    //private Bitmap resizedBitmap = Bitmap.createBitmap(Background, 0, 0, width, height, matrix, false);

    public int x = 0;
    public int y;
    public int velY;
    boolean up = false;

    MainThread mainThread;

    public static int WIDTH, HEIGHT;

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            up = true;
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            up = false;
        }
        return super.onTouchEvent(event);
    }

    public void tick(){
        //Game logic here
        if(up){
            velY -=1;
        }
        else{
            velY +=1;
        }
        if(velY >14)velY = 14;
        if(velY <-14)velY = -14;
        y += velY *2;
    }


    public void init(Context c) {
        this.context = c;

        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setFocusable(true);
        //Initialize other stuff here later
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        WIDTH = size.x;
        HEIGHT = size.y;
        x = WIDTH/ 2 - PLAYER_BMP.getWidth();

    }
    public void render(Canvas canvas){

        //Game rendering here
        canvas.drawBitmap(Background,0 ,0 , null );


        canvas.drawBitmap(PLAYER_BMP, x, y, null);

    }

    public GeekView(Context context) {
        super(context);
        mainThread = new MainThread(getHolder(), this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void startGame() {
        mainThread.startDrawThread();
    }
}
