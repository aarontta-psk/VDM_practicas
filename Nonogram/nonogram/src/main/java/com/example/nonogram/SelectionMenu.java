package com.example.nonogram;

import com.example.engine_common.interfaces.IEngine;
import com.example.engine_common.interfaces.IInput;
import com.example.engine_common.interfaces.IRender;
import com.example.engine_common.interfaces.IScene;
import com.example.engine_common.shared.FontType;
import com.example.engine_common.shared.InputType;

public class SelectionMenu implements IScene {
    private Button t4x4;
    private Button t5x5;
    private Button t5x10;
    private Button t8x8;
    private Button t10x10;
    private Button t10x15;
    private Button backButton;

    @Override
    public void init() {
        int gameWidth = GameManager.getInstance().getWidth() / 3;
        int gameHeight = GameManager.getInstance().getHeight() / 6;
        t4x4 = new Button(gameWidth / 4, gameHeight * 2, gameWidth / 2, gameWidth / 2, "4x4", "",
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
        t5x5 = new Button(gameWidth * 5 / 4, gameHeight * 2, gameWidth / 2, gameWidth / 2, "5x5", "",
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
        t5x10 = new Button(gameWidth * 9 / 4, gameHeight * 2, gameWidth / 2, gameWidth / 2, "5x10", "",
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
        t8x8 = new Button(gameWidth / 4, gameHeight * 3, gameWidth / 2, gameWidth / 2, "8x8", "",
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
        t10x10 = new Button(gameWidth * 5 / 4, gameHeight * 3, gameWidth / 2, gameWidth / 2, "10x10", "",
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
        t10x15 = new Button(gameWidth * 9 / 4, gameHeight * 3, gameWidth / 2, gameWidth / 2, "10x15", "",
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
        backButton = new Button(gameWidth, gameHeight * 5, gameWidth, gameHeight / 3, "Back", "",
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
    }

    @Override
    public void update(double deltaTime, IEngine engine) {
    }

    @Override
    public void render(IRender renderMng) {
        renderMng.setColor(0xFF000000);
        t4x4.render(renderMng);
        t5x5.render(renderMng);
        t5x10.render(renderMng);
        t8x8.render(renderMng);
        t10x10.render(renderMng);
        t10x15.render(renderMng);
        backButton.render(renderMng);
    }

    @Override
    public void handleInput(IInput input, IEngine engine) {
        if (input.getType() == InputType.TOUCH_UP) {
            int numRows = 0, numCols = 0;
            if (t4x4.isInButton(input.getX(), input.getY())) {
                numRows = 4; numCols = 4;
            }
            else if (t5x5.isInButton(input.getX(), input.getY())) {
                numRows = 5; numCols = 5;
            }
            else if (t5x10.isInButton(input.getX(), input.getY())) {
                numRows = 5; numCols = 10;
            }
            else if (t8x8.isInButton(input.getX(), input.getY())) {
                numRows = 8; numCols = 8;
            }
            else if (t10x10.isInButton(input.getX(), input.getY())) {
                numRows = 10; numCols = 10;
            }
            else if (t10x15.isInButton(input.getX(), input.getY())) {
                numRows = 10; numCols = 15;
            }
            else if (backButton.isInButton(input.getX(), input.getY())) {
                engine.getSceneManager().popScene();
                backButton.clicked(engine.getAudio());
            }

            if (numRows != 0 && numCols != 0) {
                t4x4.clicked(engine.getAudio());
                engine.getSceneManager().pushScene(new BoardScene(numRows, numCols));
            }
        }
    }
}
