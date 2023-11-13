package com.ndonald.towergame.models;

import javafx.scene.image.Image;

public class WalkAnimation {

    public static final int ANIMATION_LOOPING = 0;
    public static final int ANIMATION_NONLOOPING = 1;

    private Image[] sprites;
    private float frameDuration;

    public WalkAnimation(Image[] sprites, float frameDuration) {

        this.sprites = sprites;
        this.frameDuration = frameDuration;
    }

    public Image getKeyFrame(float stateTime, int mode) {


        int frameNumber = (int) (stateTime / frameDuration);

        if (mode == ANIMATION_NONLOOPING) {
            frameNumber = Math.min(sprites.length, frameNumber);
        } else {
            frameNumber = frameNumber % sprites.length;
        }

        return sprites[frameNumber];
    }

}
