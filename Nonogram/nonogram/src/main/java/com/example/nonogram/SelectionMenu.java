package com.example.nonogram;

import com.example.engine_common.interfaces.IEngine;
import com.example.engine_common.interfaces.IInput;
import com.example.engine_common.interfaces.IRender;
import com.example.engine_common.interfaces.IScene;
import com.example.engine_common.shared.FontType;
import com.example.engine_common.shared.InputType;

public class SelectionMenu implements IScene {

    Button t4x4;
    Button t5x5;
    Button t5x10;
    Button t8x8;
    Button t10x10;
    Button t10x15;
    Button backButton;

    private IEngine engRef;

    @Override
    public void init(IEngine engine) {
        engRef = engine;

        int x = engRef.getRender().getWidth()/3;
        int y = engRef.getRender().getHeight()/6;
        t4x4 = new Button(x / 4, y*2, x/2, x/2, "4x4", "",
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
        t5x5 = new Button(x * 5 / 4, y*2, x/2, x/2, "5x5", "",
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
        t5x10 = new Button(x * 9 / 4, y*2, x/2, x/2, "5x10", "",
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
        t8x8 = new Button(x / 4, y*3, x/2, x/2, "8x8", "",
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
        t10x10 = new Button(x * 5 / 4, y*3, x/2, x/2, "10x10", "",
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
        t10x15 = new Button(x * 9 / 4, y*3, x/2, x/2, "10x15", "",
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
        backButton = new Button(x, y*5, x, y/3, "Back", "",
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
    }

    @Override
    public void update(double deltaTime) {}

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
    public void handleInput(IInput input) {
        if (input.getType() == InputType.TOUCH_UP) {
            int x = 0, y = 0;
            if (t4x4.isInButton(input.getX(), input.getY())){
                x=4;
                y=4;
            }
            else if(t5x5.isInButton(input.getX(), input.getY())){
                x=5;
                y=5;
            }
            else if(t5x10.isInButton(input.getX(), input.getY())){
                x=5;
                y=10;
            }
            else if(t8x8.isInButton(input.getX(), input.getY())){
                x=8;
                y=8;
            }
            else if(t10x10.isInButton(input.getX(), input.getY())){
                x=10;
                y=10;
            }
            else if(t10x15.isInButton(input.getX(), input.getY())){
                x=10;
                y=15;
            }
            else if(backButton.isInButton(input.getX(), input.getY())){
                engRef.getSceneManager().popScene();
                backButton.clicked(engRef.getAudio());
            }

            if(x != 0){
                t4x4.clicked(engRef.getAudio());
                engRef.getSceneManager().pushScene(new BoardScene(x, y));
            }
        }
    }
}
