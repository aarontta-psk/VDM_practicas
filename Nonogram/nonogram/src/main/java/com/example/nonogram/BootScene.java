package com.example.nonogram;

import com.example.engine_common.interfaces.IInput;
import com.example.engine_common.interfaces.IScene;
import com.example.engine_common.interfaces.IRender;
import com.example.engine_common.interfaces.IEngine;
import com.example.engine_common.shared.FontType;

public class BootScene implements IScene {

    @Override
    public void init() {}

    @Override
    public void update(double deltaTime, IEngine engine) {
        // TODO: Load all files here, assign string to Resources final values, then go to MainMenu
        // images
        Resources.IMAGE_BACK_BUTTON = engine.getRender().loadImage("images/backbutton.png");
        Resources.IMAGE_CHECK_BUTTON = engine.getRender().loadImage("images/checkbutton.png");
//        Resources.IMAGE_COIN = engRef.getRender().loadImage("images/coin.png");
//        Resources.IMAGE_LOCK = engRef.getRender().loadImage("images/lock.png");
//        Resources.IMAGE_HEART = engRef.getRender().loadImage("images/heart.png");
//        Resources.IMAGE_NO_HEART = engRef.getRender().loadImage("images/no_heart.png");
//        Resources.IMAGE_TWITTER_BUTTON = engRef.getRender().loadImage("images/twitter_logo.png");

        // fonts
        int size = Math.min(GameManager.getInstance().getWidth(), GameManager.getInstance().getHeight());
        Resources.FONT_EXO_REGULAR_BIG = engine.getRender().loadFont("fonts/Exo-Regular.ttf", FontType.DEFAULT, size / 8);
        Resources.FONT_EXO_REGULAR_MEDIUM = engine.getRender().loadFont("fonts/Exo-Regular.ttf", FontType.DEFAULT, size / 10);
        Resources.FONT_KOMIKAX = engine.getRender().loadFont("fonts/KOMIKAX.ttf", FontType.DEFAULT, size / 10);
        Resources.FONT_SIMPLY_SQUARE_BIG = engine.getRender().loadFont("fonts/SimplySquare.ttf", FontType.DEFAULT, size / 20);
        Resources.FONT_SIMPLY_SQUARE_MEDIUM = engine.getRender().loadFont("fonts/SimplySquare.ttf", FontType.DEFAULT, size / 22);

        // sounds
        Resources.SOUND_BUTTON = engine.getAudio().loadSound("sounds/button.wav", 1);
        Resources.SOUND_CLICK = engine.getAudio().loadSound("sounds/click.wav", 1);

        // music
        engine.getAudio().loadMusic("sounds/puzzleTheme.wav", 0.1f);

        // change to main menu
        engine.getSceneManager().pushScene(new MainMenu());
    }

    @Override
    public void render(IRender renderMng) {}

    @Override
    public void handleInput(IInput input, IEngine engine) {}
}
