package com.example.nonogram;

import com.example.engine_common.interfaces.IInput;
import com.example.engine_common.interfaces.IScene;
import com.example.engine_common.interfaces.IRender;
import com.example.engine_common.interfaces.IEngine;
import com.example.engine_common.shared.FontType;
import com.example.engine_common.shared.InputType;

//Clase interna que representa la escena que queremos pintar
public class BoardScene implements IScene {
    private int dim_w, dim_h;

    private Board board;
    private Button checkButton;
    private Button backButton;

    public BoardScene(int w, int h) {
        this.dim_w = w;
        this.dim_h = h;
    }

    @Override
    public void init(IEngine engine) {
        board = new Board();
        board.init(dim_w, dim_h, engine);

        String fontButtons = engine.getRender().loadFont("fonts/SimplySquare.ttf", FontType.DEFAULT, engine.getRender().getWidth() / 22);
        String btAudio = engine.getAudio().loadSound("sounds/button.wav", 1);
        checkButton = new Button((engine.getRender().getWidth() - (engine.getRender().getWidth() / 3)) / 5,
                engine.getRender().getHeight() / 9, engine.getRender().getWidth() / 3,
                engine.getRender().getHeight() / 12, "Check", Resources.IMAGE_CHECK_BUTTON,
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
        backButton = new Button((engine.getRender().getWidth() - (engine.getRender().getWidth() / 3)) * 4 / 5,
                engine.getRender().getHeight() / 9, engine.getRender().getWidth() / 3,
                engine.getRender().getHeight() / 12, "Back", Resources.IMAGE_BACK_BUTTON,
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
    }

    @Override
    public void update(double deltaTime, IEngine engine) {
        board.update(deltaTime);
    }

    @Override
    public void render(IRender renderMng) {
        board.render(renderMng);
        checkButton.render(renderMng);
        backButton.render(renderMng);
    }

    @Override
    public void handleInput(IInput input, IEngine engine) {
        if (input.getType() == InputType.TOUCH_UP) {
            if (board.isInBoard(input.getX(), input.getY())) {
                engine.getAudio().playSound(Resources.SOUND_CLICK);
                board.markCell(input.getX(), input.getY());
            } else if (checkButton.isInButton(input.getX(), input.getY())) {
                board.checkear();
                checkButton.clicked(engine.getAudio());
                if (board.win)
                    engine.getSceneManager().pushScene(new WinScene(board), engine);
            } else if (backButton.isInButton(input.getX(), input.getY())) {
                backButton.clicked(engine.getAudio());
                engine.getSceneManager().popScene();
            }
        }
    }
}