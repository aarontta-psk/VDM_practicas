package com.example.nonogram;

import com.example.engine_common.interfaces.IEngine;
import com.example.engine_common.interfaces.IInput;
import com.example.engine_common.interfaces.IRender;
import com.example.engine_common.interfaces.IScene;
import com.example.engine_common.shared.InputType;

public class WinScene implements IScene {
    private Board board;
    private String winText;
    private Button backButton;

    public WinScene(Board b){
        board = b;
    }

    @Override
    public void init(IEngine engine) {
        winText = "¡¡Victoria!!";
        backButton = new Button((engine.getRender().getWidth()- engine.getRender().getWidth()/4)/2,
                engine.getRender().getHeight()*7/8, engine.getRender().getWidth()/4,
                engine.getRender().getHeight()/12, "Back", Resources.IMAGE_BACK_BUTTON,
                Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON);
    }

    @Override
    public void update(double deltaTime, IEngine engine) {

    }

    @Override
    public void render(IRender renderMng) {
        renderMng.setColor(0xFF000000);
        renderMng.setFont(Resources.FONT_EXO_REGULAR_BIG);
        int w = renderMng.getTextWidth(Resources.FONT_EXO_REGULAR_BIG, winText);
        renderMng.drawText((renderMng.getWidth()-w)/2, renderMng.getHeight()/6, winText);
        board.renderWin(renderMng);
        backButton.render(renderMng);
    }

    @Override
    public void handleInput(IInput input, IEngine engine) {
        if(input.getType() == InputType.TOUCH_UP && backButton.isInButton(input.getX(), input.getY())){
            while(engine.getSceneManager().getStackSize() > 1)
                engine.getSceneManager().popScene();
            backButton.clicked(engine.getAudio());
        }
    }
}
