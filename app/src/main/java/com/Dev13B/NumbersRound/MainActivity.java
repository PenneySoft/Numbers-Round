package com.Dev13B.NumbersRound;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

        // Global variables
    // -------------------------------------------------

        // For tracking how many random tiles the user has chosen
    int smallNum, bigNum, totalNum;
    int maxNum = 6;

        // values of the tiles, in array form
    int[] bigNumsArray = new int[] {25, 50, 75, 100};
    int[] smallNumsArray = new int[] {1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8, 9, 9, 10, 10};

        // Store all (big then small) indexes here
    ArrayList<Integer> indexesForAllTiles;

        // Array that stores numbers to go on the orig tiles
    int[] origArr = new int[6];

    Random rand = new Random();






        // Custom methods
    // -------------------------------------------------

        // Use this to get and set tile data
    public class TileControl {

        /* private ArrayList<ArrayList<boolean>> tileClickableArray = new ArrayList<ArrayList<boolean>>();

            // Clears clickable array
        public void resetClickableArray() {
            tileClickableArray = new ArrayList<ArrayList<boolean>>();
            tileClickableArray.add(ArrayList<boolean>());
            for (int i=0; i<6; i++){
                tileClickableArray.get(0).add(true);
            }
            tileClickableArray.add(ArrayList<boolean>());
            for (int i=0; i<4; i++){
                tileClickableArray.get(1).add(true);
            }
        } */

            // Sets tileBG (0, 5, "blue")
        public void setBG(int row, int column, String colour){

            ViewGroup parentVG = null;
            ImageView childIV;


            switch (row) {
                case (0):
                parentVG = (ViewGroup)findViewById(R.id.origTileLinear);
                break;
                case (1):
                parentVG = (ViewGroup)findViewById(R.id.sumTileLinear);
                break;
                default:
            } // End switch

            childIV = (ImageView)parentVG.getChildAt(column);

            switch(colour) {
                case "blue":
                childIV.setImageResource(R.drawable.orig);
                break;
                case "grey":
                childIV.setImageResource(R.drawable.origgrey);
                break;
            }
        } // End of setBG()

            // All tiles blue GF
        public void allBlue(){
            ViewGroup parentVG = (ViewGroup)findViewById(R.id.origTileLinear);
            ImageView childIV;

            for (int i=0; i<parentVG.getChildCount(); i++){
                childIV = (ImageView)parentVG.getChildAt(i);
                childIV.setImageResource(R.drawable.orig);
            }
        } // end allBlue()


            // Set text of tile
        public void setText(int row, int column, int content){
            ViewGroup parentVG = null;
            TextView childTV;

            switch (row) {
                case 0:
                parentVG = (ViewGroup)findViewById(R.id.origTextLinear);
                break;
                case 1:
                parentVG = (ViewGroup)findViewById(R.id.sumTextLinear);
                break;
                default:
            } // End switch

            childTV = (TextView)parentVG.getChildAt(column);
            childTV.setText(Integer.toString(content));
        } // End of setText




            // Clear Text of tile
        public void clearText(int row, int column){
            ViewGroup parentVG = null;
            TextView childTV;

            switch (row) {
                case 0:
                parentVG = (ViewGroup)findViewById(R.id.origTextLinear);
                break;
                case 1:
                parentVG = (ViewGroup)findViewById(R.id.sumTextLinear);
                break;
                default:
            } // End switch

            childTV = (TextView)parentVG.getChildAt(column);
            childTV.setText("");
        } // End of clearText





        /*
            // Can we click this?
        public boolean clickable(int row, int column) {
            return tileClickableArray.get(row).get(column);
        } // End of clickable
       */

    } // End of tileControl Class


    // -------------------------------------------------


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
            startGameScreen(view);
        }


    } // end of randomTileClicked




    public void startGameScreen(View view){

            // Hide choosing screen.
        ViewGroup pickerScreen = (ViewGroup)findViewById(R.id.pickerScreen);
        pickerScreen.setVisibility(View.GONE);

            // Show game screen
        ViewGroup gameScreen = (ViewGroup)findViewById(R.id.gameScreen);
        gameScreen.setVisibility(View.VISIBLE);

            // Separate method that takes big and small and chooses numbers
        numberChooser(bigNum, smallNum);


            // method to fill the origArray tiles and makes them blue

    }




        // Takes big and small and generates 6x numbers
    public void numberChooser(int big, int small){

        indexesForAllTiles = new ArrayList<Integer>();
        TileControl tileControl = new TileControl();

            // Pick indexes for the big tiles. Send it: [amount of numbers needed] [size of list to choose indexes from]
        int[] indexesForBigTiles = pickRandomTilesIndex(bigNum, bigNumsArray.length);
        for (int i=0; i<indexesForBigTiles.length; i++) {
            indexesForAllTiles.add(indexesForBigTiles[i]);
        }

            // Pick indexes for the small tiles
        int[] indexesForSmallTiles = pickRandomTilesIndex(smallNum, smallNumsArray.length);
        for (int i=0; i<indexesForSmallTiles.length; i++) {
            indexesForAllTiles.add(indexesForSmallTiles[i]);
        }

            // Fill origArr with big numbers from index
        for (int i=0; i<bigNum; i++) {
            origArr[i] = bigNumsArray[indexesForAllTiles.get(i)];
        }
            // Fill origArr with small numbers from index, starting where we left off
        for (int i=bigNum; i<maxNum; i++) {
            origArr[i] = smallNumsArray[indexesForAllTiles.get(i)];
        }
            // origArr is now filled with our 6 numbers/values

        tileControl.allBlue();

            // Fill tiles with values
        for (int i=0; i<6; i++){
            tileControl.setText(0, i, origArr[i]);
        }
        for (int i=0; i<4; i++){
            tileControl.clearText(1, i);
        }


    }   // end of numberChooser()




        // Generates array of unique index positions for one numArray
    public int[] pickRandomTilesIndex(int amount, int indexSize) {


        int[] uniqueArray = new int[amount];		// New array to be returned
        Boolean passed = false;

            // Looping through the elements in our new array to pick new unique values
        for (int i=0; i<amount; i++) {
            passed = false;

                // Choose a random number, then check it in a second
            while (passed == false) {
                passed = true;
                    // Temporarily chooses a possible random number [0 -> indexSize] for current position [i] in uniqueArray to return
                uniqueArray[i] = rand.nextInt(indexSize);

                    // Loop through the [unique values] already chosen up until the current position [i], and check against current iteration's possible value to prevent duplicates
                for (int j=0; j<i; j++) {
                    if ( uniqueArray[i] == uniqueArray[j] ) {
                        passed = false;
                        break;
                    }
                }
            } // End of [while], random number correctly chosen for current element in [uniqueArray]
        } // End of [for], all elements in [uniqueArray] are chosen

        return uniqueArray;

    }   // End of pickRandomTilesIndex()






        // Make a method that devises a target answer and the workings from the origArray
    public void generateTarget(View view){


    }



        // Make all mystery tiles 50% transparent, the pickerScreen visible, and reset tile counts to 0
    public void refreshPicker(View view){

        smallNum = 0;
        bigNum = 0;
        totalNum = 0;

        ViewGroup bigVG = (ViewGroup)findViewById(R.id.gameScreen);
        bigVG.setVisibility(View.GONE);

        bigVG = (ViewGroup)findViewById(R.id.pickerScreen);
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
