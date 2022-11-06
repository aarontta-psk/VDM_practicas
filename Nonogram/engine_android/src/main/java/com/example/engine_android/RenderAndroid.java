package com.example.engine_android;

import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.engine_common.interfaces.IRender;
import com.example.engine_common.interfaces.IScene;
import com.example.engine_common.shared.FontType;

import java.io.File;
import java.util.HashMap;

public class RenderAndroid implements IRender {

    private SurfaceView myView;
    private SurfaceHolder holder;
    private Canvas canvas;
    private Paint paint;

    private HashMap<String, ImageAndroid> images;
    private HashMap<String, FontAndroid> fonts;
    private AssetManager assetManager;

    public RenderAndroid(SurfaceView myView, AssetManager aM) {
        this.myView = myView;
        this.holder = this.myView.getHolder();
        this.assetManager = aM;
        this.paint = new Paint();
        this.fonts = new HashMap<>();
        this.images = new HashMap<>();
    }

    public boolean surfaceValid() {
        return this.holder.getSurface().isValid();
    }

    public void clear() {
        this.canvas = this.holder.lockCanvas();
        this.canvas.drawColor(0xFFFFFFFF);
    }

    public void present() {
        this.holder.unlockCanvasAndPost(canvas);
    }

    @Override
    public String loadImage(String filePath) {
        File imageFile = new File(filePath);
        String convFilepath = filePath.replaceAll("./assets/", "");
        images.put(imageFile.getName(), new ImageAndroid(convFilepath, assetManager));
        return imageFile.getName();
    }

    @Override
    public String loadFont(String filePath, FontType type, int size) {
        File fontFile = new File(filePath);
        String convFilepath = filePath.replaceAll("./assets/", "");
        fonts.put(fontFile.getName(), new FontAndroid(convFilepath, assetManager, size, type));
        return fontFile.getName();
    }

    @Override
    public void setColor(int hexColor) {
        paint.setColor(hexColor);
    }

    @Override
    public void setFont(String fontID) {
        FontAndroid font = fonts.get(fontID);
        paint.setTypeface(font.getFont());
    }

    @Override
    public void drawImage(int x, int y, int width, int height, String imageID) {
        canvas.drawBitmap(images.get(imageID).getImage(), x, y, paint);
    }

    @Override
    public void drawText(int x, int y, String text) {
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height, boolean fill) {
        if (fill)
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        else
            paint.setStyle(Paint.Style.STROKE);
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