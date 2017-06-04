package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Downwell;
import com.mygdx.game.model.GameModel;

/**
 * Created by Utilizador on 03/06/2017.
 */

public class LevelView {

    private Label label;
    private Downwell game;
    private GameModel model;

    private BitmapFont font;
    public LevelView(Downwell game, GameModel model){
        this.model = model;
        this.game = game;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("5x5.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 32;
        font = generator.generateFont(parameter);
        addLevelLabel(model.getStats().getLevel());
    }

    private void addLevelLabel(int level){
        label = new Label("LEVEL "+level, new Label.LabelStyle(font, Color.WHITE));
        float newH = 100;
        float newW = 200;
        label.setFontScale(newW/label.getWidth(),newH/label.getHeight());
        label.setWidth(200);
        label.setHeight(100);
        label.setPosition((model.getWidth()/2+0.5f)/GameView.PIXEL_TO_METER-label.getWidth()/2,(model.getDepth()-2*GameModel.HERO_POS_OFFSET)/GameView.PIXEL_TO_METER);
    }

    public void draw(){
        label.draw(game.getBatch(),1);
    }
}
