package com.example.nonogram;

import com.example.engine_common.interfaces.IInput;
import com.example.engine_common.interfaces.IScene;
import com.example.engine_common.interfaces.IRender;
import com.example.engine_common.interfaces.IEngine;
import com.example.engine_common.shared.FontType;

public class BootScene implements IScene {
    IEngine eng;

    @Override
    public void init(IEngine engRef) {
        // TODO: Load all files here, assign string to Resources final values, then go to MainMenu
        eng = engRef;
        // images
        Resources.IMAGE_BACK_BUTTON = engRef.getRender().loadImage("images/backbutton.png");
        Resources.IMAGE_CHECK_BUTTON = engRef.getRender().loadImage("images/checkbutton.png");
//        Resources.IMAGE_COIN = engRef.getRender().loadImage("images/coin.png");
//        Resources.IMAGE_LOCK = engRef.getRender().loadImage("images/lock.png");
//        Resources.IMAGE_HEART = engRef.getRender().loadImage("images/heart.png");
//        Resources.IMAGE_NO_HEART = engRef.getRender().loadImage("images/no_heart.png");
//        Resources.IMAGE_TWITTER_BUTTON = engRef.getRender().loadImage("images/twitter_logo.png");

        // fonts
        int size = 400; // TODO: CHANGE THIS WHEN IVE FINISHED REFACTOR
        Resources.FONT_EXO_REGULAR_BIG = engRef.getRender().loadFont("fonts/Exo-Regular.ttf", FontType.DEFAULT, size / 8);
        Resources.FONT_EXO_REGULAR_MEDIUM = engRef.getRender().loadFont("fonts/Exo-Regular.ttf", FontType.DEFAULT, size / 10);
        Resources.FONT_KOMIKAX = engRef.getRender().loadFont("fonts/KOMIKAX.ttf", FontType.DEFAULT, size / 10);
        Resources.FONT_SIMPLY_SQUARE_BIG = engRef.getRender().loadFont("fonts/SimplySquare.ttf", FontType.DEFAULT, size / 20);
        Resources.FONT_SIMPLY_SQUARE_MEDIUM = engRef.getRender().loadFont("fonts/SimplySquare.ttf", FontType.DEFAULT, size / 22);

        // sounds
        Resources.SOUND_BUTTON = engRef.getAudio().loadSound("sounds/button.wav", 1);
        Resources.SOUND_CLICK = engRef.getAudio().loadSound("sounds/click.wav", 1);

        // music
        engRef.getAudio().loadMusic("sounds/puzzleTheme.wav", 0.1f);
    }

    @Override
    public void update(double deltaTime) {
        // change to main menu
        eng.getSceneManager().pushScene(new MainMenu());
    }

    @Override
    public void render(IRender renderMng) {}

    @Override
    public void handleInput(IInput input) {}
}
