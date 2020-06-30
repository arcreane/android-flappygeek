package com.example.flappygeek;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

public class GeekView extends SurfaceView implements SurfaceHolder.Callback{

    private Context context;
    private Bitmap PLAYER_BMP = BitmapFactory.decodeResource(getResources(), R.drawable.cartman);
    private Bitmap Tubes = BitmapFactory.decodeResource(getResources(), R.drawable.tube);
    private Bitmap resizedBitmap;
    private Bitmap resizedPlayerBitmap;
    public float x = 0;
    public float y = 0;
    public float velY;
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
        return true;
        //return super.onTouchEvent(event);
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

        if(y <= 0){
            y = 1;
        }else if(y > 1301){
            y = 1300;
        }
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
    @SuppressLint("WrongConstant")
    public void render(Canvas canvas){

        //Game rendering here

        canvas.drawBitmap(resizedBitmap,0 ,0 , null );

        canvas.drawBitmap(Tubes,100 ,1300 , null );

        canvas.drawBitmap(resizedPlayerBitmap, x, y, null);

        if(x == 300){
            System.out.println("tu as perdu");
        }else if(y == 1300){

            System.out.println("tu as encore perdu");
            String text = "tu perdu";
            int duration = 2;
            Toast.makeText(context, text, duration).show();
        }

    }

    public GeekView(Context context) {
        super(context);
        mainThread = new MainThread(getHolder(), this);

        Bitmap Background = BitmapFactory.decodeResource(getResources(), R.drawable.bus);

        Bitmap Character = BitmapFactory.decodeResource(getResources(), R.drawable.cartman);

        int width = Background.getWidth();
        int height = Background.getHeight();
        int newWidth = 2120;
        int newHeight = 1920;
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        float scalePersoWidth = ((float) newWidth) / width;
        float scalePersoHeight = ((float) newHeight) / height;


        Matrix toto = new Matrix();
        toto.postScale(scaleWidth, scaleHeight);

        resizedBitmap = Bitmap.createBitmap(Background, 0, 0, width, height, toto, false);

        Matrix perso = new Matrix();
        perso.postScale(scalePersoWidth, scalePersoHeight);
        resizedPlayerBitmap  = Bitmap.createBitmap(Character, 100, 200, WIDTH + 300, HEIGHT + 300, perso, false );
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
