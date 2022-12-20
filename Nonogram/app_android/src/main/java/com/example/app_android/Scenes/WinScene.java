package com.example.app_android.Scenes;

import com.example.app_android.GameManager;
import com.example.app_android.Resources;
import com.example.app_android.Objects.Board;
import com.example.app_android.Objects.Button;

import com.example.engine_android.EngineAndroid;
import com.example.engine_android.Enums.InputType;
import com.example.engine_android.DataStructures.IScene;
import com.example.engine_android.DataStructures.InputAndroid;
import com.example.engine_android.Modules.IntentSystemAndroid;
import com.example.engine_android.Modules.RenderAndroid;

public class WinScene implements IScene {
    private Board board;
    private boolean victory;
    private int category, level;

    private String winText;
    private String winFont;

    private Button backButton;
    private Button coinsButton;
    private Button shareButton;

    public WinScene(Board b, boolean win, int categ, int level){
        this.board = b;
        this.level = level;
        this.victory = win;
        this.category = categ;
    }

    @Override
    public String getId() { return "WinScene"; }

    @Override
    public void init(EngineAndroid engRef) {
        // title
        this.winFont = Resources.FONT_EXO_REGULAR_BIG;
        this.winText = this.victory ? "¡¡Victoria!!" : "Derrota :(";

        // what coins to add
        int coins = this.victory ? (this.board.getWidth() * this.board.getHeight()) / 2 : 0;
        GameManager.getInstance().addCoins(coins);

        // buttons
        int w = GameManager.getInstance().getWidth() / 4;
        int h = GameManager.getInstance().getHeight();
        this.backButton = new Button(w / 2, h * 7 / 8, w, h / 12, "Back",
                Resources.IMAGE_BACK_BUTTON, Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON, false);
        this.coinsButton = new Button(5 * w / 2, h * 7 / 8, w, h / 12, "+ " + coins,
                Resources.IMAGE_COIN, Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON, false);
        this.shareButton = new Button(3 * w / 2, h, w, h / 12, "Share",
                Resources.IMAGE_TWITTER_BUTTON, Resources.FONT_SIMPLY_SQUARE_MEDIUM, Resources.SOUND_BUTTON, false);
    }

    @Override
    public void rearrange(EngineAndroid engRef) {

    }

    @Override
    public void update(double deltaTime, EngineAndroid engine) {

    }

    @Override
    public void render(RenderAndroid renderMng) {
        // render text
        renderMng.setColor(0xFF000000);
        renderMng.setFont(this.winFont);
        int w = renderMng.getTextWidth(this.winFont, this.winText);
        renderMng.drawText((GameManager.getInstance().getWidth() - w) / 2, GameManager.getInstance().getHeight() / 6, winText);

        // render solved board
        if (this.victory){
            this.board.renderWin(renderMng);
            this.shareButton.render(renderMng);
        }


        // render buttons
        this.backButton.render(renderMng);
        this.coinsButton.render(renderMng);
    }

    @Override
    public void handleInput(InputAndroid input, EngineAndroid engRef) {
        if(input.getType() == InputType.TOUCH_UP && this.backButton.isInButton(input.getX(), input.getY())){
            if(this.category == 0)
                engRef.getSceneManager().changeScene(new SelectionMenu(), engRef);
            else
                engRef.getSceneManager().changeScene(new CategoryLevelSelectionMenu(this.category), engRef);
            this.backButton.clicked(engRef.getAudio());
        }
        else if (input.getType() == InputType.TOUCH_UP && this.shareButton.isInButton(input.getX(), input.getY())){
            if (this.victory){
                String text = " ";
                if (level == -1)
                    text =  "I just won a super awesome random level on Nonograms, I dare you to beat it as well!";
                else
                    text = "I just won level " + level + " on Nonograms, I dare you to beat it as well!";
                engRef.getIntentSystem().share(IntentSystemAndroid.SocialNetwork.TWITTER, text);
            }
        }
    }
}
