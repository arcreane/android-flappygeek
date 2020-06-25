package com.example.flappygeek;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class CharacterSprite {

    private Bitmap image;

    public CharacterSprite(Bitmap bmp){
        image = bmp;
    }

    public void draw(Canvas canvas) {

    }

    public boolean isAlive(){
        return true;
    }

}
