package com.example.nonogram;

import com.example.engine_common.interfaces.IInput;
import com.example.engine_common.interfaces.IScene;
import com.example.engine_common.interfaces.IRender;
import com.example.engine_common.interfaces.IEngine;
import com.example.engine_common.shared.InputType;

//Clase interna que representa la escena que queremos pintar
public class MyScene implements IScene {
    private int dim_w, dim_h;

    private Board board;
    private Button checkButton;
    private Button backButton;

    private IEngine engRef;
    private String sound;

    public MyScene(int w, int h) {
        this.dim_w = w; this.dim_h = h;
    }

    @Override
    public void init(IEngine engine) {
        board = new Board();
        board.init(dim_w, dim_h, engine);

        checkButton = new Button(10, 50, 100, 30, "Check" ,eng.getRender().loadImage("./assets/images/nomires.jpeg"), board.getFont());
        backButton = new Button(200, 50, 100, 30, "Back", eng.getRender().loadImage("./assets/images/nomires.jpeg"), board.getFont());

        engRef = engine;

        sound = engine.getAudio().loadSound("./assets/sounds/click.wav", 1);
        engine.getAudio().loadMusic("./assets/sounds/puzzleTheme.wav", 1);
        engine.getAudio().playMusic();
    }

    @Override
    public void update(double deltaTime) {
        board.update(deltaTime);
    }

    @Override
    public void render(IRender renderMng) {
        board.render(renderMng);
        checkButton.render(renderMng);
        backButton.render(renderMng);
    }

    @Override
    public void handleInput(IInput input) {
        if(input.getType() == InputType.TOUCH_UP){
            if(board.isInBoard(input.getX(), input.getY())){
                engRef.getAudio().playSound(sound);
                board.markCell(input.getX(),input.getY());
            }
            else if(checkButton.isInBUtton(input.getX(), input.getY())){
                board.checkear();
            }
            else if(backButton.isInBUtton(input.getX(), input.getY())){

                engRef.getSceneManager().popScene();
            }
        }
    }
}