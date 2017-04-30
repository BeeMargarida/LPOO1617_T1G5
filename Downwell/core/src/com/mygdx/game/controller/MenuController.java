package com.mygdx.game.controller;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.model.MenuModel;

public class MenuController {

    public static int ARENA_WIDTH;
    public static int ARENA_HEIGHT;

    private final World world;

    public MenuController(MenuModel model){
        world = new World(new Vector2(0,-0.5f),true);
    }
}
