package com.Dev13B.NumbersRound;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

        // For tracking how many random tiles the user has chosen
    int smallNum, bigNum, totalNum;
    int maxNum = 6;


        // When user taps a tile. Get the tile row to determine if its big.
    public void randomTileClicked(View view){

        Boolean bigFlag = false;

        ViewGroup bigVG = (ViewGroup)findViewById(R.id.bigBacking);
        if (view.getParent() == bigVG) {
                bigFlag = true;
        }

            // If clicked, reverse Alpha and score
        if (view.getAlpha() == (1.0f)){
            view.setAlpha(0.5f);
            if (bigFlag == true){
                bigNum--;
            } else {
                smallNum--;
            }
        } else { // choosing, make alpha 1 and click counter
            view.setAlpha(1.0f);
            if (bigFlag == true){
                bigNum++;
            } else {
                smallNum++;
            }
        } // end of clicking/unclicking (if)




        totalNum = smallNum + bigNum;
        if (totalNum >= maxNum){
            Toast.makeText(getApplicationContext(), "Starting!\nBig: " + Integer.toString(bigNum) + "\nSmall: " + Integer.toString(smallNum), Toast.LENGTH_LONG).show();
        }


    } // end of randomTileClicked


    public void startGameScreen(View view){

            // Need to: hide choosing screen.
            // Show game screen
            // separate method that takes big and small and chooses numbers
            //      fills the origArray tiles and makes them blue


    }

        // takes big and small and generates 6x numbers
    public void numberChooser(View view){


    }
    

        // Make a method that devises a target answer and the workings from the origArray
    public void

        // Make all mystery tiles 50% transparent, the pickerScreen visible, and reset tile counts to 0
    public void refreshPicker(View view){

        smallNum = 0;
        bigNum = 0;
        totalNum = 0;

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
    } // end of refreshPicker





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            // Starts the game with pickerScreen Visible and all buttons Alpha 0.5f
        ImageView nullIV = (ImageView)findViewById(R.id.big0);
        refreshPicker(nullIV);



    }


}
