package com.Dev13B.NumbersRound;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        // Make all mystery tiles 50% transparent
    public void refreshPicker(View view){

        // All change smallNum, bigNum and totalNum to 0 ?

        ViewGroup bigVG = (ViewGroup)findViewById(R.id.pickerScreen);
        bigVG.setVisibility(View.VISIBLE);

        bigVG = (ViewGroup)findViewById(R.id.bigBacking);
        for (int i=0; i<bigVG.getChildCount(); i++) {
            bigVG.getChildAt(i).setAlpha(0.5f);
        }

        bigVG = (ViewGroup)findViewById(R.id.smallRow0);
        for (int i=0; i<bigVG.getChildCount(); i++) {
            bigVG.getChildAt(i).setAlpha(0.5f);
        }

        bigVG = (ViewGroup)findViewById(R.id.smallRow1);
        for (int i=0; i<bigVG.getChildCount(); i++) {
            bigVG.getChildAt(i).setAlpha(0.5f);
        }

        bigVG = (ViewGroup)findViewById(R.id.smallRow2);
        for (int i=0; i<bigVG.getChildCount(); i++) {
            bigVG.getChildAt(i).setAlpha(0.5f);
        }

        bigVG = (ViewGroup)findViewById(R.id.smallRow3);
        for (int i=0; i<bigVG.getChildCount(); i++) {
            bigVG.getChildAt(i).setAlpha(0.5f);
        }


    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }


}
