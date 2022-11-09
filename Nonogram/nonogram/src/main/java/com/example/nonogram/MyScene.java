package com.example.nonogram;

import com.example.engine_common.interfaces.IInput;
import com.example.engine_common.interfaces.IScene;
import com.example.engine_common.interfaces.IRender;
import com.example.engine_common.interfaces.IEngine;
import com.example.engine_common.shared.FontType;
import com.example.engine_common.shared.InputType;

//Clase interna que representa la escena que queremos pintar
public class MyScene implements IScene {
    private float x;
    private float y;
    private int radius;
    private int speed;
    private Board board;

    private IEngine engRef;
    private String sound;

    @Override
    public void init(IEngine eng) {
        this.x=50;
        this.y=50;
        this.radius = 50;
        this.speed = 150;
        board = new Board();
        board.init(8,8, eng);

        engRef = eng;

        sound = eng.getAudio().loadSound("./assets/sounds/doFlauta.wav", 1);
        eng.getAudio().loadMusic("./assets/sounds/doFlauta.wav", 1);
        //eng.getAudio().playMusic();
    }

    @Override
    public void update(double deltaTime) {
        board.update(deltaTime);
    }

    @Override
    public void render(IRender renderMng) {
        board.render(renderMng);
    }

    @Override
    public void handleInput(IInput input) {
        if(input.getType() == InputType.TOUCH_UP){
            if(board.isInBoard(input.getX(), input.getY())){
                engRef.getAudio().playSound(sound);
                board.markCell(input.getX(),input.getY());
            }
            else{
                board.checkear();
            }
        }
    }
}