package com.example.engine_android;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.fonts.Font;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.engine_common.interfaces.IFont;
import com.example.engine_common.interfaces.IImage;
import com.example.engine_common.interfaces.IRender;
import com.example.engine_common.interfaces.IScene;
import com.example.engine_common.shared.FontType;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Render implements IRender {

    private SurfaceView myView;
    private SurfaceHolder holder;
    private Canvas canvas;
    private Paint paint;

    private HashMap<String, ImageApp> images;
    private HashMap<String, FontApp> fonts;
    private AssetManager assetManager;

    public void init (SurfaceView myView, AssetManager aM){
        this.myView = myView;
        this.holder = this.myView.getHolder();
        this.assetManager = aM;
    }

    public void render(IScene currentScene) {
        // Pintamos el frame
        while (!this.holder.getSurface().isValid());
        this.canvas = this.holder.lockCanvas();

        // "Borramos" el fondo.
        this.canvas.drawColor(0xFF0000FF); // ARGB
        currentScene.render(this);

        this.holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public String loadImage(String filePath) {
        File imageFile = new File(filePath);
        images.put(imageFile.getName(), new ImageApp(filePath, assetManager));
        return imageFile.getName();
    }

    @Override
    public String loadFont(String filePath, FontType type, int size) {
        File fontFile = new File(filePath);
        fonts.put(fontFile.getName(), new FontApp(fontFile, size, type));
        return fontFile.getName();
    }

    @Override
    public void setResolution() {

    }

    @Override
    public void setColor(int hexColor) {

    }

    @Override
    public void setFont() {

    }

    @Override
    public void drawImage(int x, int y, int width, int height, String imageID) {
        canvas.drawBitmap(images.get(imageID).getImage(), x, y, paint);
    }

    @Override
    public void drawText(int x, int y, String text, String fontID) {
        FontApp font = fonts.get(fontID);
        paint.setTypeface(font.getFont());
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height, boolean fill) {
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    @Override
    public void drawLine(int og_x, int og_y, int dst_x, int dst_y) {
        canvas.drawLine(og_x, og_y, dst_x, dst_y, paint);
    }

    @Override
    public void drawCircle(int x, int y, int r) {
        canvas.drawCircle(x, y, r, paint);
    }

    @Override
    public int getWidth() {
        return this.myView.getWidth();
    }

    @Override
    public int getHeight() {
        return this.myView.getHeight();
    }
}