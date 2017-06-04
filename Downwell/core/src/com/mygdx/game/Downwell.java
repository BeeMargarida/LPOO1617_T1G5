package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameConfig;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.GameStats;
import com.mygdx.game.view.GameView;
import com.mygdx.game.view.MainMenuScreen;
import com.mygdx.game.view.ResultsScreen;

public class Downwell extends Game {

	private SpriteBatch batch;
	private AssetManager assetManager;
	private GameConfig config;
	private GameStats stats;
	private final static int MAX_HERO_HP = 4;
	private final static int STARTING_DEPTH = 50;
	private final static int STARTING_ENEMY_NO = 8;

	private void resetGameStats(){
		stats = new GameStats(1, MAX_HERO_HP);
		config = new GameConfig(STARTING_DEPTH, STARTING_ENEMY_NO);
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		loadAssets();
		setMainMenuScreen();
	}

	public void startGame() {
		if(stats.getLevel()%2 == 0)
			config.incDifficulty();
		GameModel model = new GameModel(config, stats);
		setScreen(new GameView(this, model, new GameController(model)));
	}

	public void setMainMenuScreen(){
        resetGameStats();
		setScreen(new MainMenuScreen(this));
	}

	public void setGameScreen(){
		this.startGame();
	}

	public void setResultsScreen(){
		setScreen(new ResultsScreen(this,stats.getScore(),stats.getLevel(),stats.getKills()));
	}


	@Override
	public void dispose() {
        batch.dispose();
		assetManager.dispose();
	}

	private void loadAssets(){
		assetManager.load( "sBox.png" , Texture.class);
		assetManager.load( "mainMenuBackground.png" , Texture.class);

		assetManager.load( "1.png" , Texture.class);
		assetManager.load( "2.png" , Texture.class);
		assetManager.load( "3.png" , Texture.class);
		assetManager.load( "4.png" , Texture.class);
		assetManager.load( "5.png" , Texture.class);
		assetManager.load( "6.png" , Texture.class);
		assetManager.load( "7.png" , Texture.class);

		assetManager.load( "r1.png" , Texture.class);
		assetManager.load( "r2.png" , Texture.class);
		assetManager.load( "r3.png" , Texture.class);
		assetManager.load( "r4.png" , Texture.class);
		assetManager.load( "r5.png" , Texture.class);
		assetManager.load( "r6.png" , Texture.class);
		assetManager.load( "r7.png" , Texture.class);

		assetManager.load( "jr1.png" , Texture.class);
		assetManager.load( "jr2.png" , Texture.class);
		assetManager.load( "jr3.png" , Texture.class);
		assetManager.load( "jr4.png" , Texture.class);
		assetManager.load( "jr5.png" , Texture.class);
		assetManager.load( "jr6.png" , Texture.class);
		assetManager.load( "jr7.png" , Texture.class);

		assetManager.load( "jump.png" , Texture.class);

		assetManager.load( "sleepbat.png", Texture.class);
		assetManager.load( "bat1.png" , Texture.class);
		assetManager.load( "bat2.png" , Texture.class);
		assetManager.load( "bat3.png" , Texture.class);
		assetManager.load( "bat4.png" , Texture.class);
		assetManager.load( "bat5.png" , Texture.class);

		assetManager.load( "snail.png", Texture.class);

		assetManager.load( "b1.png", Texture.class);
		assetManager.load( "b2.png", Texture.class);
		assetManager.load( "b3.png", Texture.class);
		assetManager.load( "b4.png", Texture.class);
		assetManager.load( "b5.png", Texture.class);
		assetManager.load( "b6.png", Texture.class);
		assetManager.load( "b7.png", Texture.class);

		assetManager.load( "berserk-mark-brand-of-sacrifice_1.jpg", Texture.class);
		assetManager.load( "big bullet.png", Texture.class);
		assetManager.load( "shooting.png", Texture.class);

		assetManager.load("dBlock.png", Texture.class);
		assetManager.load("iBlock.png", Texture.class);

		assetManager.load("sideWall.png", Texture.class);

		assetManager.load("lifebar1.png", Texture.class);
		assetManager.load("lifebar2.png", Texture.class);
		assetManager.load("lifebar3.png", Texture.class);
		assetManager.load("lifebar4.png", Texture.class);

		assetManager.load("wb0.png", Texture.class);
		assetManager.load("wb1.png", Texture.class);
		assetManager.load("wb2.png", Texture.class);
		assetManager.load("wb3.png", Texture.class);
		assetManager.load("wb4.png", Texture.class);
		assetManager.load("wb5.png", Texture.class);
		assetManager.load("wb6.png", Texture.class);
		assetManager.load("wb7.png", Texture.class);
		assetManager.load("wb8.png", Texture.class);


		assetManager.load("scoreImage_2.jpg", Texture.class);

		assetManager.finishLoading();
	}

	public AssetManager getAssetManager() {
		return assetManager;
	}

	public SpriteBatch getBatch() {
		return batch;
	}

}
