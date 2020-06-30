package com.example.flappygeek;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;


public class MainActivity extends Activity {

    GeekView geekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        geekView = new GeekView(this);


        setContentView(R.layout.activity_main);

        //final ImageButton btn = findViewById(R.id.imageButton);

        //btn.setOnClickListener(new View.OnClickListener() {
           // @Override
            //public void onClick(View v) {
                //btn.setVisibility(View.GONE);

                RelativeLayout layout = findViewById(R.id.background);

                layout.addView(MainActivity.this.geekView);

                geekView.startGame();

            //}
       // });
    }



}
