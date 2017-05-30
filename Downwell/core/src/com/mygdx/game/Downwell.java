package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.controller.MenuController;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.MenuModel;
import com.mygdx.game.view.GameView;
import com.mygdx.game.view.MainMenuScreen;
import com.mygdx.game.view.MenuView;

public class Downwell extends Game {

	private SpriteBatch batch;
	private AssetManager assetManager;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		startGame();
	}

	public void startGame() {
		GameModel model = new GameModel(50, 8);
		//setScreen(new MainMenuScreen(this));
		setScreen(new GameView(this, model, new GameController(model)));
		/*MenuModel model = new MenuModel(30,50);
		setScreen(new MenuView(this,model,new MenuController(model)));*/
	}


	@Override
	public void dispose() {
        batch.dispose();
		assetManager.dispose();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public SpriteBatch getBatch() {
		return batch;
	}
	/*@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.end();
	}*/

}
