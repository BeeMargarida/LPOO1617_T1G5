package com.mygdx.game.view;


import com.badlogic.gdx.ScreenAdapter;
import com.mygdx.game.Downwell;
import com.mygdx.game.controller.MenuController;
import com.mygdx.game.model.MenuModel;

public class MenuView extends ScreenAdapter{

    private final Downwell game;
    private final MenuModel model;
    private final MenuController controller;

    public MenuView(Downwell game, MenuModel model, MenuController controller){
        this.game = game;
        this.model = model;
        this.controller = controller;
    }
}
