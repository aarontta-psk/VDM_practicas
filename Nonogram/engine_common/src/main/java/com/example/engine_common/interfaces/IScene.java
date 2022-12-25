package com.example.engine_common.interfaces;

public interface IScene {
    public void init();
    public void update(double deltaTime, IEngine engine);
    public void render(IRender renderMng);
    public void handleInput(IInput input, IEngine engine);
}
