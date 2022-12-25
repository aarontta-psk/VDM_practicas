package com.example.nonogram;

import com.example.engine_common.interfaces.IEngine;
import com.example.engine_common.interfaces.IInput;
import com.example.engine_common.interfaces.IRender;
import com.example.engine_common.interfaces.IScene;
import com.example.engine_common.shared.InputType;

public class MainMenu implements IScene {
    private Button playButton;
    private String title;

    @Override
    public void init(IEngine engine) {
        title = "NONOGRAMAS";
        playButton = new Button((engine.getRender().getWidth() - engine.getRender().getWidth() / 3) / 2,
                (int) (engine.getRender().getHeight() / 1.5), engine.getRender().getWidth() / 3,
                engine.getRender().getHeight() / 8, "PLAY", "", Resources.FONT_EXO_REGULAR_BIG,
                Resources.SOUND_BUTTON);
    }

    @Override
    public void update(double deltaTime, IEngine engine) {}

    @Override
    public void render(IRender renderMng) {
        renderMng.setColor(0xFF000000);
        renderMng.setFont(Resources.FONT_KOMIKAX);
        int wi = renderMng.getTextWidth(Resources.FONT_KOMIKAX, title);
        renderMng.drawText((renderMng.getWidth() - wi) / 2, renderMng.getHeight() / 6, title);
        playButton.render(renderMng);
    }

    @Override
    public void handleInput(IInput input, IEngine engine) {
        if (input.getType() == InputType.TOUCH_UP && playButton.isInButton(input.getX(), input.getY())) {
            engine.getSceneManager().pushScene(new SelectionMenu(), engine);
            playButton.clicked(engine.getAudio());
        }
    }
}
