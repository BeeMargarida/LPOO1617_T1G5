package com.mygdx.game.model;


import com.mygdx.game.controller.MenuController;

public class MenuModel {

    public MenuModel(int width, int height){
        MenuController.ARENA_WIDTH = width;
        MenuController.ARENA_HEIGHT = height;
    }
}
