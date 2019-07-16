package com.Dev13B.NumbersRound;

import android.animation.ObjectAnimator;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import java.lang.Math;

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

        // ArrayList that logs all the equation lines
    ArrayList<ArrayList<Integer>> equationLog; // might need to be defined as null, maybe not though

    Random rand = new Random();

    boolean gameActive;
    int posInCalc;
    ArrayList<ArrayList<Integer>> historyAL;


        // Custom methods
    // -------------------------------------------------

        // Use this to get and set tile data
    public class TileControl {

            // Sets tileBG (0, 5, "blue")
        public void setBG(int column, String colour){

            ViewGroup parentVG = (ViewGroup)findViewById(R.id.origTileLinear);
            ImageView childIV = (ImageView)parentVG.getChildAt(column);

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
        public void clearText(int column){
            ViewGroup parentVG = (ViewGroup)findViewById(R.id.sumTextLinear);
            TextView childTV = (TextView)parentVG.getChildAt(column);

            childTV.setText("");
        } // End of clearText

    } // End of tileControl Class



    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


        // Equation operator String generator

    public class EqOp {
            // make an oeprator int, turned into a character + - x /
        public String toString(int x){
            String string = "";
            switch (x) {
                case 0:
                    string = "+";
                    break;
                case 1:
                    string = "-";
                    break;
                case 2:
                    string = "x";
                    break;
                case 3:
                    string = "/";
                    break;
                default:
                    string = "error";
            }
            return string;
        } // End of toString()

            // Take an equation in int form, and return a user-readable string for ArrayList<String>
        public String toLine(int[] arr){
            // Given arr as: 10 + 5 = 15
            // e.g.   arr: 10 1 5 15
            // Turn this into a string
            String line = "";
            line += Integer.toString(arr[0]);
            line += " ";
            line += toString(arr[1]);
            line += " ";
            line += Integer.toString(arr[2]);
            line += " = ";
            line += Integer.toString(arr[3]);
            line += "\n";

            return line;
        } // end of toLine

    } // End of EqOp class


    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

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

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public void showEquation(View view){
        EqOp eqOp = new EqOp();
        TextView boardTV = (TextView)findViewById(R.id.boardTV);
        int[] equationBuffer;
        StringBuilder stringBuffer = new StringBuilder();

        if (equationLog.size() < 4 ){
            boardTV.setTextSize(40.0f);
        } else {
            boardTV.setTextSize(34.0f);
        }



        // main for loop iterates through lines to fill stringBuffer

        for (int i=0; i<equationLog.size(); i++){

            equationBuffer = new int[4];

            // inner loop iterates through single line to take each element and set to equation buffer
            for (int j=0; j<4; j++){
                equationBuffer[j] = equationLog.get(i).get(j);
            }

            // We now have an equationBuffer full of one line of instructions.
            // Transfer this into stringBuffer
             stringBuffer.append(eqOp.toLine(equationBuffer));
        } // end of main for loop

        boardTV.setText(stringBuffer.toString());

    } // end of showEquation method

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public void startGameScreen(View view){

            // Hide choosing screen.
        ViewGroup pickerScreen = (ViewGroup)findViewById(R.id.pickerScreen);

        ObjectAnimator animation = ObjectAnimator.ofFloat(pickerScreen, "translationX", -1500f);
        animation.setDuration(500);
        animation.start();
        // pickerScreen.setVisibility(View.GONE);

             // Show game screen
        ViewGroup gameScreen = (ViewGroup)findViewById(R.id.gameScreen);
        gameScreen.setVisibility(View.VISIBLE);

        gameScreen.setTranslationX(1500f);

        ObjectAnimator animation2 = ObjectAnimator.ofFloat(gameScreen, "translationX", 0f);
        animation2.setDuration(500);
        animation2.start();

        ImageView background = (ImageView)findViewById(R.id.backgroundIV);
        ObjectAnimator animationBG = ObjectAnimator.ofFloat(background, "translationX", -50f);
        animationBG.setDuration(500);
        animationBG.start();


            // Separate method that takes big and small and chooses numbers
        numberChooser(bigNum, smallNum);



            // method to fill the origArray tiles and makes them blue
        generateTarget();


    } // end of start game screen

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    // test, clear
    public void renew(View view){
        numberChooser(bigNum, smallNum);
        generateTarget();
        TextView startButton = (TextView)findViewById(R.id.startButton);
        startButton.setVisibility(View.VISIBLE);


    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public void startTimer(View view){

        gameActive = true;
        posInCalc = 0;
        historyAL = new ArrayList<ArrayList<Integer>>();
        historyAL.add(new ArrayList<Integer>());

        TextView startButton = (TextView)findViewById(R.id.startButton);
        startButton.setVisibility(View.GONE);

        TextView boardTV = (TextView)findViewById(R.id.boardTV);
        boardTV.setText("");

            // Fill target with final number in equation log
        TextView targetTV = (TextView)findViewById(R.id.targetTV);
        String targetBuffer = "";
        if ( equationLog.get(equationLog.size()-1).get(3) < 100) {
            targetBuffer += "0";
            if ( equationLog.get(equationLog.size()-1).get(3) < 10) {
                targetBuffer += "0";
            }
        }
        targetBuffer += Integer.toString(equationLog.get(equationLog.size()-1).get(3));

        targetTV.setText(targetBuffer);

            // Hide GO button, show clock hand, start rotation

        // Hide GO

        ImageView clockHandIV = (ImageView)findViewById(R.id.clockhandIV);
        clockHandIV.setVisibility(View.VISIBLE);
        clockHandIV.clearAnimation();

        RotateAnimation rotate = new RotateAnimation(180, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(30000);
        rotate.setInterpolator(new LinearInterpolator());
        clockHandIV.startAnimation(rotate);


    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        // Takes big and small and generates 6x numbers, equation too but in separate method
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
        for (int i=0; i<3; i++){
            tileControl.clearText(i);
        }

            // Hide clock hand, replace with GO button
        ImageView clockHandIV = (ImageView)findViewById(R.id.clockhandIV);
        clockHandIV.setVisibility(View.GONE);
            // GO button appear


    }   // end of numberChooser()

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

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

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        // Devises a target answer from global origArr, and stores the workings for global equationLog
    public void generateTarget(){

        Set<Integer> easyAnswerTS;

        // Clear target at top of screen
        TextView targetTV = (TextView)findViewById(R.id.targetTV);
        targetTV.setText("");

        // Keeping track of available values we can choose from to compute: [0] is original, [1] is result of our calculations
        ArrayList<ArrayList<Integer>> editedAL;

        // ArrayList to store our calculation workings to later be translated for the user when they want to see how to do it
        // store equation lines in here
        equationLog = new ArrayList<ArrayList<Integer>>();


        // Random number generator for random decisions
        Random rand = new Random();

        // AL holder/buffer to store vales we take numbers from editedAL while we are choosing a 2nd value. No need to hold where it came from in this version
        ArrayList<Integer> valuesUsedInCurrentCalcAL;

        // Assorted int variables to hold random numbers of different sizes
        int randRow, randElement, randOperator;

        // What we will have as the answer target for user to find
        int finalTarget = 0;

        //  Blank array for our [Success?Y/N, Sum] operatorChooser() method to return to
        int[] calcArray;

        // For our (while 1)
        boolean gotTarget;




        // Master (while 0). Instantiate all ALs etc again to reset everything if 1/999 while fails.
        // This will loop once we have passed it an answer, and this checks to make sure the answer is valid
        while (finalTarget<1 || finalTarget>999) {

            gotTarget = false;

            // Clear AL. New 1st row using origAL values. New 2nd row left blank
            editedAL = new ArrayList<ArrayList<Integer>>();
            editedAL.add(new ArrayList<Integer>());

            for (int i=0; i<origArr.length; i++){
                editedAL.get(0).add(origArr[i]);
            }

            editedAL.add(new ArrayList<Integer>());

            /*
            editedAL: 100 50 2 5 8 7    // for values from orig
                    : blank             // for values as sum
            */

            // For our returned result of the operator method e.g. [success, sum]
            calcArray = new int[2];

            // Our equations for later when user wants to see solution to problem
            equationLog = new ArrayList<ArrayList<Integer>>();

            /*  row  [ 1st , op , 2nd , sum ]
                [0]     100 * 5 500
                [1]     500 + 8 508
                etc
            */

            // Temporary buffer AL to take values out of editedAL so that we can't pick them twice
            valuesUsedInCurrentCalcAL = new ArrayList<Integer>();

            int intBuffer = 0;





            // (while 1) start, answer has to be 1->999. Otherwise loop and find another answer/calculation for the numbers that are already chosen in orig
            while (gotTarget == false) {

                // Reset buffer AL for taking values out of editedAL
                valuesUsedInCurrentCalcAL = new ArrayList<Integer>();

                // Choose our two numbers to sum together - when we get to 2, done.
                while (valuesUsedInCurrentCalcAL.size() < 2){

                    // Check to see if there are numbers in both rows of editedAL to see which row we can choose from
                    if ( editedAL.get(0).size()>0 && editedAL.get(1).size()>0 ){
                        // Choose either row
                        randRow = rand.nextInt(2);

                    } else if (editedAL.get(0).size()>0) {
                        // Only top row has elements
                        randRow = 0;

                    } else {
                        // Only bottom row has elements
                        randRow = 1;

                    } // end of if (for picking row to choose element from)

                    // Pick an element index from the randomly chosen row above
                    randElement = rand.nextInt(editedAL.get(randRow).size());

                    // Put random value into our buffer AL: "valuesUsedInCurrentCalcAL"

                    if (valuesUsedInCurrentCalcAL.size()==0) {
                        // First nubmer is chosen, put it in array
                        valuesUsedInCurrentCalcAL.add( (int)editedAL.get(randRow).get(randElement) );

                    } else {
                        // Chosen second number on this loop iteration, put biggest value as first element in valuesUsedInCurrentCalcAL
                        if ( editedAL.get(randRow).get(randElement) > valuesUsedInCurrentCalcAL.get(0)) {
                            // New second number is bigger than the first, squeeze into first position
                            valuesUsedInCurrentCalcAL.add(0, (int)editedAL.get(randRow).get(randElement));

                        } else {
                            // New second number is smaller, just add onto the end of AL
                            valuesUsedInCurrentCalcAL.add((int)editedAL.get(randRow).get(randElement));
                        } // end of if/else

                    } // end of if (for putting biggest number first, on second for loop iteration)

                    // Taking values out of editedAL and putting them in bufferAL temporarily
                    editedAL.get(randRow).remove(randElement);

                } // end of for loop, we now should have our 2 values stored in buffer for calcualtion. Now to choose random operator


                    /*  e.g
                    editedAL[0]: 50 2 8 7
                            [1]: blank
                    valuesUsedInCurrentCalcAL: [100, 5]
                    */


                // Start with our operator success being false/0
                calcArray[0] = 0;

                // Instantiate just to prevent potential error
                randOperator = rand.nextInt(4);



                // Start (while 2): cycle through operators until we get one that gives a valid sum i.e not 0 or fraction/!whole number
                while (calcArray[0]==0) {
                    // Pick a random operator of 0-4 for Add, Subtract, Multiple, Dividide.
                    randOperator = rand.nextInt(4);
                    // Pass our chosen nums and random operator to see if it gives a valid sum
                    calcArray = operatorGenerator(valuesUsedInCurrentCalcAL.get(0), valuesUsedInCurrentCalcAL.get(1), randOperator);
                } // end of inner (while 2):
                // we have finished choosing operator and we should have a valid sum in calcArray[1];

                // Should have a:
                // calcArray = {1, sum} for success and the sum. Now add this to the workingsAL, and remove/clear AL where necessary
                // equationLog design:
                // num1 , operator , num2 , sum

                // .size()-1 gives us the latest row in workingsAL

                equationLog.add(new ArrayList<Integer>());

                intBuffer = (int)valuesUsedInCurrentCalcAL.get(0);
                equationLog.get(equationLog.size()-1).add(intBuffer);

                intBuffer = (int)randOperator;
                equationLog.get(equationLog.size()-1).add(intBuffer);

                intBuffer = (int)valuesUsedInCurrentCalcAL.get(1);
                equationLog.get(equationLog.size()-1).add(intBuffer);

                intBuffer = (int)calcArray[1];
                equationLog.get(equationLog.size()-1).add(intBuffer);

                // workingsAL[1]: [100 * 5 500]
                editedAL.get(1).add(calcArray[1]);
                // editedAL[0]: [val val val val]
                //         [1]: [sum]

                // Clear our ALs and buffers etc for the next while loop
                valuesUsedInCurrentCalcAL.clear();
                valuesUsedInCurrentCalcAL = new ArrayList<Integer>();
                calcArray = new int[2];


                // How many lines of calculations should we have as a minimum? 3?
                // Also check if we have too many sums in editedAL[1]. Must have exactly 1 to finish, otherwise we've done unecessary calculations
                // If we could finish but could carry on, coin toss to carry on or finish

                // Now to decide whether we should finish our calculations or iterate more

                if (editedAL.get(0).size()==0 && editedAL.get(1).size()==1) {
                    // Last value, no option other to finish with waht we have
                    finalTarget = (int)editedAL.get(1).get(0);
                    gotTarget = true;
                }    // end of (too many sum elements) if




                // Just a catch
                /*
                if (editedAL.get(0).size()+editedAL.get(1).size()<2) {
                        // Only one value left, in either
                    gotTarget = true;
                    finalTarget = 999;
                    continue;
                } // end of if (small choice)
                */


                if ( editedAL.get(1).size() == 1 && editedAL.get(0).size()>0) {
                    // We have a possible single answer but leftover values from origAL, flip coin to end it now

                    if (rand.nextInt(8) == 0) {
                        // Flipping coin...
                        // 0 = finish now
                        // Calculations done! Continue isn't necessary, while 1 loop will start again if finalTarget is too big/small
                        finalTarget = (int)editedAL.get(1).get(0);
                        gotTarget = true;

                    } // end of (coin toss) inner if

                } // end of (coin toss) outer if


                // Check to see if the answer can be easily/quickly made in 1 line. If so, continue while loop.
                easyAnswerTS = new TreeSet<Integer>();

                for (int i=0; i<origArr.length; i++){
                    easyAnswerTS.add(origArr[i]);
                    for (int j=i+1; j<origArr.length; j++){

                        easyAnswerTS.add(origArr[i] + origArr[j]);
                        easyAnswerTS.add(Math.abs(origArr[i] - origArr[j]));
                        easyAnswerTS.add(origArr[i] * origArr[j]);
                    }
                }

                System.out.println(easyAnswerTS);

                if (easyAnswerTS.contains(finalTarget)) {
                    gotTarget=false;
                }


            } // (while 1) end. Made sum(s) and calc(s), have we finished though and got a potential answer?



        } // end of Master while 0

    }   // End of monster generateTarget() method

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public int[] operatorGenerator(int a, int b, int operator){
        int result, successOrFail =1;   // 0 is fail, 1 is pass
        float divideResult;

        switch(operator) {
            case 0: result = a+b; break;
            case 1: result = a-b; if (result<1){
                successOrFail=0;
            } break;
            case 2: result = a*b; if (result == a){successOrFail=0;} break;
            case 3: if (a%b != 0){
                successOrFail=0;
                result = 0;
            } else {
                divideResult = a/b;
                result = (int)divideResult;
                if (result == a) {successOrFail = 0;}
            } break;
            default: successOrFail = 0; result = 0;
        } // end of switch

        int[] arrayToReturn = {successOrFail, result};
        return arrayToReturn;

    } // end of operatorGenorator() method

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        // Make all mystery tiles 50% transparent, the gameScreen GONE, the pickerScreen VISIBLE, and reset tile counts to 0
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

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public void tileClicked(View view){

        if (gameActive && posInCalc != 1) {

            Log.i("Info", "tileClicked 01");

            ViewGroup parentVG;
            ViewGroup parentVGVG;

            parentVG = (ViewGroup)view.getParent();
            parentVGVG = (ViewGroup)parentVG.getParent();
            int row = 1;
            int column = 0;

                // Get row.
            if ( parentVGVG == (ViewGroup)findViewById(R.id.origTileCont) ){
                row = 0;
                Log.i("Info", "tileClicked we clicked an origTileCont");
            }

            Log.i("Info", "tileClicked 02");

                // Get column.
            for (int i=0; i<parentVG.getChildCount(); i++){
                Log.i("Info", "tileClicked looping...");
                if (view == parentVG.getChildAt(i)){
                    Log.i("Info", "tileClicked found child.");
                    column = i;
                    Log.i("Info", "tileClicked child(i) is: " + i);
                    break;
                }
            }
            // We now have the row and column of the tiles clicked.

            Log.i("Info", "tileClicked getting textViews");
            Log.i("Info", "tileClicked ");

                // Get TextView contents
            ViewGroup origTextLinear = (ViewGroup)parentVGVG.getChildAt(1);
            Log.i("Info", "tileClicked getting textView...");
            TextView textView = (TextView)origTextLinear.getChildAt(column);
            Log.i("Info", "tileClicked getting text...");
            String tileString = (String)textView.getText();
            Log.i("Info", "tileClicked converting text in view to int variable tileInt...");
            int tileInt = Integer.parseInt(tileString);

            boolean firstNum = (posInCalc == 0);


            Log.i("Info", "tileClicked 03");

            // need to have a function that passes full line of calc if we're on secondNum, and returns

            if (firstNum) {
                // Add to history
                // Grey out tile
            } else {
                int[] operatorReturn = operatorGenerator(historyAL.get(historyAL.size()-1).get(3), tileInt, historyAL.get(historyAL.size()-1).get(4));
                    // Check for valid sum
                boolean validSum = (operatorReturn[0] == 1);
                if (!validSum){
                    Toast.makeText(getApplicationContext(), "Invalid sum!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Yep.", Toast.LENGTH_LONG).show();
                }
            }

            Log.i("Info", "tileClicked 04");

            // void refreshWhiteBoard();

                // Pass these to if statements
            boolean fullLines = (historyAL.size() > 1);
            boolean currentLineEmpty = historyAL.get(historyAL.size()-1).size() == 0;

                // Make sure we have at least a full line, to prevent indexOutOfBounds
            if (fullLines){
                    // Check to see if we have a winning answer at the end
                if (currentLineEmpty){
                            // Latest sum equals target, game is won
                    if ( historyAL.get(historyAL.size()-2).get(7) == equationLog.get(equationLog.size()-1).get(3) ){
                        Toast.makeText(getApplicationContext(), "Well done!", Toast.LENGTH_LONG).show();
                    }
                }
            } // end of game won checker






        } // end of if gameActive

    } // end of tileClicked

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public void operatorClicked(View view){

        if (gameActive){

            Log.i("Info", "operatorClicked 01");

            ViewGroup operatorVG = (ViewGroup)findViewById(R.id.operatorLinear);
            int operator = 0;
            String operatorString;

            for (int i=0; i<operatorVG.getChildCount(); i++){
                if (view == operatorVG.getChildAt(i)){
                    operator = i;
                }
            }

            Log.i("Info", "operatorClicked 02");

                // Clear is operator 2
            if (operator == 2) {
                clear();
                return;
            } else {
                if (posInCalc == 1){
                    Log.i("Info", "operatorClicked 03");
                    if (operator > 2) {
                        Log.i("Info", "operatorClicked 04");
                        // Because we've messed up with clear button (01 2 34) is (+- c */)
                        operator--;
                    }
                    historyAL.get(historyAL.size()-1).add(operator);
                    posInCalc++;
                } else {
                        // Not ready for operator
                    Toast.makeText(getApplicationContext(), "Tap a number tile...", Toast.LENGTH_LONG).show();
                } // end of posInCalc

            } // end of for loop




            Log.i("Info", "operatorClicked 05");




        } // end of if gameActive

    } // end of operatorClicked

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    public String userOpToString(int operator) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ");

        switch(operator) {
            case 0:
                stringBuilder.append("+");
                break;
            case 1:
                stringBuilder.append("-");
                break;
            case 3:
                stringBuilder.append("x");
                break;
            case 4:
                stringBuilder.append("/");
                break;
        }
        stringBuilder.append(" ");

        return stringBuilder.toString();
    } // end of userOpToString

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        // clears the current/previous user calculation
    public void clear(){
        int currentLine = historyAL.size()-1;

            // Not on 0th row -or- there is something to delete on current row
        if (currentLine > 0 || historyAL.get(currentLine).size() > 0){

                // Blank line with equation above, so remove current empty row
            if (historyAL.get(currentLine).size() == 0){
                historyAL.remove(historyAL.size()-1);
                currentLine--;
            }

                // undo what is on current line
            int arraySize = (int)historyAL.get(currentLine).size();
            Integer[] arrayBuffer = historyAL.get(currentLine).toArray(new Integer[arraySize]);
            undo(arrayBuffer);
            historyAL.get(currentLine).clear();
        }

        posInCalc = 0;

    } // end of clear

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        // Takes array of instructions from a line of history and implements those changes in tiles
    public void undo (Integer[] instructions){
        //       [ 0   1   2  3   4   5   6 ]
        // given [row col val op row col val] ... or a smaller array

        TileControl tileControl = new TileControl();
        int[] passToSwitch = {instructions[0], instructions[1], instructions[2]};
        boolean flag = false;


        for (int i=0; i<2; i++){

            if (passToSwitch[0] == 0) {
                tileControl.setBG(passToSwitch[1], "blue");
            }
            tileControl.setText(passToSwitch[0], passToSwitch[1], passToSwitch[2]);

            if (i==0 && instructions.length > 4){
                passToSwitch[0] = instructions[4];
                passToSwitch[1] = instructions[5];
                passToSwitch[2] = instructions[6];
            } else {
                i++;
            }
        } // end of for loop

    } // end of undo

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -


    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            // Starts the game with pickerScreen Visible and all buttons Alpha 0.5f
        ImageView nullIV = (ImageView)findViewById(R.id.big0);
        refreshPicker(nullIV);



    }


}
