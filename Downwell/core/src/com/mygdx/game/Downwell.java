package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.GameConfig;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.GameSoundFX;
import com.mygdx.game.model.GameStats;
import com.mygdx.game.view.GameView;
import com.mygdx.game.view.MainMenuScreen;
import com.mygdx.game.view.ResultsScreen;

/**
 * Downwell class deals with everything regarding the many screens of the game, the reset of stats and configurations, the loading of
 * assets and means to load them.
 */
public class Downwell extends Game {

	private SpriteBatch batch;
	private AssetManager assetManager;
	private GameConfig config;
	private GameStats stats;
	private GameSoundFX soundFX;
	private long start;
	private final static int MAX_HERO_HP = 4;
	private final static int STARTING_DEPTH = 50;
	private final static int STARTING_ENEMY_NO = 8;

	/**
	 * Puts the game stats and config to the root ones.
	 */
	private void resetGameStats(){
		stats = new GameStats(1, MAX_HERO_HP);
		config = new GameConfig(STARTING_DEPTH, STARTING_ENEMY_NO);
	}

	/**
	 * Creates a SpriteBatch, a AssetManager, loads the assets, sound a sets the main menu screen.
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();
		assetManager = new AssetManager();
		loadAssets();
		soundFX = new GameSoundFX(this);
		setMainMenuScreen();
	}

	/**
	 * Increases the difficulty every two levels, sets a new GameModel with the new information and sets the screen to a
	 * new gameView.
	 */
	public void startGame() {
		soundFX.randomize();
		soundFX.playBackGroundMusic();
		if(stats.getLevel()%2 == 0)
			config.incDifficulty();
		GameModel model = new GameModel(config, stats, soundFX);
		setScreen(new GameView(this, model, new GameController(model)));
	}

	/**
	 * Resets the stats of the game and sets the screen to the Main Menu.
	 */
	public void setMainMenuScreen(){
		soundFX.stopGameOverMusic();
		soundFX.playTitleScreenMusic();
        resetGameStats();
		setScreen(new MainMenuScreen(this));
	}

	/**
	 * Starts the game's music and start's the game, with new screen and model.
	 */
	public void setGameScreen(){
		soundFX.stopTitleScreenMusic();
		start = System.nanoTime();
		this.startGame();
	}

	/**
	 * Stops the game's music and sets the screen to the results one.
	 */
	public void setResultsScreen(){
		soundFX.stopBackGroundMusic();
		soundFX.playGameOverMusic();
		long end = System.nanoTime();
		long timeDiff = end-start;
		setScreen(new ResultsScreen(this,stats,timeDiff));
	}

	/**
	 * Disposes of the resources when they are no longer needed.
	 */
	@Override
	public void dispose() {
		soundFX.dispose();
        batch.dispose();
		assetManager.dispose();
	}

	/**
	 * Loads all the resources needed for the game, such as all the images and sounds.
	 */
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


		assetManager.load("Artificial Intelligence Bomb.mp3", Music.class);
		assetManager.load("devil1.mp3", Music.class);
		assetManager.load("gameover.mp3", Music.class);
		assetManager.load("mainmenu.mp3", Music.class);
		assetManager.load("bexplosion.wav", Sound.class);
		assetManager.load("eexplosion.wav", Sound.class);
		assetManager.load("hit.wav", Sound.class);
		assetManager.load("hitbullet.wav", Sound.class);
		assetManager.load("jump.wav", Sound.class);
		assetManager.load("land.wav", Sound.class);
		assetManager.load("shoot.wav", Sound.class);
		assetManager.load("menuselect.wav", Sound.class);
		assetManager.load("gamestart.ogg", Sound.class);

		assetManager.finishLoading();
	}

	/**
	 * Returns the asset manager.
	 * @return asset manager
	 */
	public AssetManager getAssetManager() {
		return assetManager;
	}

	/**
	 * Returns the batch.
	 * @return batch
	 */
	public SpriteBatch getBatch() {
		return batch;
	}
}
