package com.example.engine_android.DataStructures;

import com.example.engine_android.EngineAndroid;
import com.example.engine_android.Modules.RenderAndroid;

public interface IScene {
    public String getId();
    public void init(EngineAndroid engine);
    public void update(double deltaTime);
    public void render(RenderAndroid renderMng);
    public void handleInput(InputAndroid input);
}