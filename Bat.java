package com.example.pong;

import android.graphics.RectF;

class Bat {

    // These are the member variables (fields)
    // They all have the m prefix
    // They are all private
    // because direct access is not required
    private RectF mRect;
    private float mLength;
    private float mXCoord;
    private float mBatSpeed;
    private int mScreenX;

    // These variables are public and final
    // They can be directly accessed by
    // the instance (in PongGame)
    // because they are part of the same
    // package but cannot be changed
    final int STOPPED = 0;
    final int LEFT = 1;
    final int RIGHT = 2;

    // GM adding variable to change bat size or speed for testing
    private int bigBatCounter = 0;
    private long batTimer = System.currentTimeMillis();

    String batBoost = "";




    // Keeps track of if an how the ball is moving
    // Starting with STOPPED
    private int mBatMoving = STOPPED;

    Bat(int sx, int sy){

        // Bat needs to know the screen
        // horizontal resolution
        // Outside of this method
        mScreenX = sx;

        // Configure the size of the bat based on
        // the screen resolution
        // One eighth the screen width to start (but bat gets bigger each level)

        mLength = mScreenX / 8;
        // One fortieth the screen height
        float height = sy / 40;

        // Configure the starting locaion of the bat
        // Roughly the middle horizontally
        mXCoord = mScreenX / 2;
        // The height of the bat
        // off of the bottom of the screen
        float mYCoord = sy - height;

        // Initialize mRect based on the size and position
        mRect = new RectF(mXCoord, mYCoord,
                mXCoord + mLength,
                mYCoord + height);

        // Configure the speed of the bat
        // This code means the bat can cover the
        // width of the screen in 1 second
        mBatSpeed = mScreenX;
    }

    // Return a reference to the mRect object
    RectF getRect(){
        return mRect;
    }

    // Update the movement state passed
    // in by the onTouchEvent method
    void setMovementState(int state){
        mBatMoving = state;
    }

    // GM added for bat size increase of 10%
    void biggerBat () {
        mLength = mLength * 1.1f;

    }

    // GM added for bat speed increase of 10%
    void fasterBat () {
        mBatSpeed = mBatSpeed * 1.1f;
    }

    // GM added to reset bat speed and position
    void batReset () {
        mBatSpeed = mScreenX;
        mLength = mScreenX / 8;
        mXCoord = mScreenX / 2;
        batBoost = "";
        mRect.left = mXCoord;
        mRect.right = mXCoord + mLength;
    }

    // Update the bat- Called each frame/loop
    void update(long fps){

        // Move the bat based on the mBatMoving variable
        // and the speed of the previous frame
        if(mBatMoving == LEFT){
            mXCoord = mXCoord - mBatSpeed / fps;
        }

        if(mBatMoving == RIGHT){
            mXCoord = mXCoord + mBatSpeed / fps;
        }

        // Stop the bat going off the screen
        if(mXCoord < 0){
            mXCoord = 0;
            bigBatCounter++;

            switch (bigBatCounter) {

                case 1:
                    batTimer = System.currentTimeMillis();
                    break;

                case 400:


                    if (System.currentTimeMillis() - batTimer < 8000) {

                        mLength = mScreenX / 4;
                        batBoost = "Boost: Bat Size";

                    }
                    bigBatCounter = 0;
                    break;

            }

        }

        if(mXCoord + mLength > mScreenX){
            mXCoord = mScreenX - mLength;

            bigBatCounter++;

            switch (bigBatCounter) {

                case 1:
                    batTimer = System.currentTimeMillis();
                    break;

                case 400:


                    if (System.currentTimeMillis() - batTimer < 8000) {

                        mBatSpeed = mBatSpeed * 1.3f;
                        batBoost = "Boost: Bat Speed";

                    }
                    bigBatCounter = 0;
                    break;

            }


        }

        // Update mRect based on the results from
        // the previous code in update
        mRect.left = mXCoord;
        mRect.right = mXCoord + mLength;
    }

}
