package com.example.nonogram;

import com.example.engine_common.interfaces.IEngine;
import com.example.engine_common.interfaces.IInput;
import com.example.engine_common.interfaces.IRender;
import com.example.engine_common.interfaces.IScene;
import com.example.engine_common.shared.FontType;
import com.example.engine_common.shared.InputType;

public class MainMenu implements IScene {
    Button playButton;
    String title;

    private IEngine engRef;

    @Override
    public void init(IEngine engine) {
        engRef = engine;
        title = "NONOGRAMAS";
        playButton = new Button((engRef.getRender().getWidth() - engRef.getRender().getWidth() / 3) / 2,
                (int) (engRef.getRender().getHeight() / 1.5), engRef.getRender().getWidth() / 3,
                engRef.getRender().getHeight() / 8, "PLAY", "", Resources.FONT_EXO_REGULAR_BIG,
                Resources.SOUND_BUTTON);
    }

    @Override
    public void update(double deltaTime) {}

    @Override
    public void render(IRender renderMng) {
        renderMng.setColor(0xFF000000);
        renderMng.setFont(Resources.FONT_KOMIKAX);
        int wi = engRef.getRender().getTextWidth(Resources.FONT_KOMIKAX, title);
        renderMng.drawText((engRef.getRender().getWidth() - wi) / 2, engRef.getRender().getHeight() / 6, title);
        playButton.render(renderMng);
    }

    @Override
    public void handleInput(IInput input) {
        if (input.getType() == InputType.TOUCH_UP && playButton.isInButton(input.getX(), input.getY())) {
            engRef.getSceneManager().pushScene(new SelectionMenu());
            playButton.clicked(engRef.getAudio());
        }
    }
}
