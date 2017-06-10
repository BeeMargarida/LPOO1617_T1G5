package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.Downwell;
import com.mygdx.game.controller.GameController;
import com.mygdx.game.model.BatModel;
import com.mygdx.game.model.BubbleModel;
import com.mygdx.game.model.BulletModel;
import com.mygdx.game.model.EnemyModel;
import com.mygdx.game.model.GameModel;
import com.mygdx.game.model.HeroModel;
import com.mygdx.game.model.MapTileModel;
import com.mygdx.game.model.SnailModel;
import java.util.ArrayList;

/**
 * GameView is a class that deals with the visualization of every element of the game, having methods to update the view of the game,
 * including its viewport and camera, and also drawing the elements.
 */
public class GameView extends ScreenAdapter{

    private static final boolean DEBUG_PHYSICS = false;
    public final static float PIXEL_TO_METER = 0.04f;
    private static final float VIEWPORT_WIDTH = 600;

    private final Downwell game;
    private final GameModel model;
    private final GameController controller;

    private HealthBarView healthBar;
    private BulletBarView bulletBar;
    private LevelView levelView;

    private final Viewport viewport;
    private final OrthographicCamera camera;
    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugCamera;
    private ArrayList<EnemyView> enemyViews;

    /**
     * Constructor of the class, it creates a healthBarView, a bulletBarView, a LevelView, sets the viewport and camera and fills the
     * enemyViews arraylist with all the views of the enemies.
     * @param game Downwell game, has the assets
     * @param model GameModdel, has the status of all the game entities
     * @param controller GameController, has the bodies of all the game entities
     */
    public GameView(Downwell game, GameModel model, GameController controller) {
        this.game = game;
        this.model = model;
        this.controller = controller;

        this.healthBar = new HealthBarView(game);
        this.bulletBar = new BulletBarView(game);
        levelView = new LevelView(game, model);

        camera = createCamera();
        float ratio = ((float) Gdx.graphics.getHeight() / (float) Gdx.graphics.getWidth());
        viewport = new FitViewport(VIEWPORT_WIDTH, VIEWPORT_WIDTH * ratio, camera);
        camera.position.set(camera.viewportHeight / 2f, camera.viewportHeight / 2f , 0);
        camera.update();
        viewport.apply();

        addEnemiesView();
    }

    /**
     * Goes through all the enemies in the game and adds the respective view of each of them to a ArrayList.
     */
    private void addEnemiesView(){
        ArrayList<EnemyModel> enemyModel = model.getEnemies();
        enemyViews = new ArrayList<EnemyView>();
        for(int i = 0; i < enemyModel.size(); i++){
            if(enemyModel.get(i) instanceof BatModel)
                enemyViews.add(new BatView(game));
            else if(enemyModel.get(i) instanceof BubbleModel)
                enemyViews.add(new BubbleView(game));
            else if(enemyModel.get(i) instanceof SnailModel)
                enemyViews.add(new SnailView(game));
        }
    }

    /**
     * Creates the camera for the game.
     * @return camera created
     */
    private OrthographicCamera createCamera() {
        OrthographicCamera camera = new OrthographicCamera(VIEWPORT_WIDTH / PIXEL_TO_METER, VIEWPORT_WIDTH / PIXEL_TO_METER* ((float) Gdx.graphics.getHeight() / (float)Gdx.graphics.getWidth()) );
        if (DEBUG_PHYSICS) {
            debugRenderer = new Box2DDebugRenderer();
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
        }

        return camera;
    }

    /**
     * Receives the inputs made and translate them to instructions, changing the state of the game.
     * @param delta time interval
     */
    private void handleInputs(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            camera.position.set(camera.position.x-2, camera.position.y,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            camera.position.set(camera.position.x+2, camera.position.y,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.position.set(camera.position.x, camera.position.y+2,0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.position.set(camera.position.x, camera.position.y-2,0);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            controller.moveHeroLeft();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            controller.moveHeroRight();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            controller.jumpHero();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.K)){
            controller.shootHero();
        }
    }

    /**
     * Resizes the viewport, stages and centers the camera.
     * @param width new width
     * @param height new height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        healthBar.resize(width,height);
        bulletBar.resize(width,height);
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2,0);
        camera.update();

    }

    /**
     * Disposes of the stages of the healthBar and the bulletBar.
     */
    @Override
    public void dispose() {
        healthBar.dispose();
        bulletBar.dispose();
    }

    /**
     * It starts by handling the inputs, updating the game and checking if the game is over. If the game isn't over, it will render all
     * the elements of the game and set the camera in the correct position. If the level is over, it request for a new one to be created
     * and, if the game is over, it requests the game to set the results screen.
     * @param delta tine interval
     */
    @Override
    public void render(float delta) {
        handleInputs(delta);

        controller.update(delta);
        if(model.getNextLevel()){
            model.getStats().incLevel();
            model.getStats().setHeroHp(model.getHeroModel().getHp());
            game.startGame();
            return;
        }
        if(model.getGameOver()){
            game.setResultsScreen();
            return;
        }

        Gdx.gl.glClearColor( 1/255f, 1/255f, 1/255f, 1 );
        Gdx.gl.glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );

        camera.position.set(model.getWidth()/2f / PIXEL_TO_METER, model.getHeroModel().getY() / PIXEL_TO_METER, 0);
        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        levelView.draw();
        drawEntities();
        game.getBatch().end();

        healthBar.update(model.getHeroModel());
        healthBar.draw();
        bulletBar.update(controller);
        bulletBar.draw();

        if (DEBUG_PHYSICS) {
            debugCamera = camera.combined.cpy();
            debugCamera.scl(1 / PIXEL_TO_METER);
            debugRenderer.render(controller.getWorld(), debugCamera);
        }
    }

    /**
     * Draw all the elements of the game, such has map tiles, enemies and the hero, updating each view before drawing. Except the
     * enemies, which we have in an arraylist of views, the rest of the entities we have to use the ViewFactory to get the. This ViewFactory
     * translates the information in the model to a view that is updated.
     */
    private void drawEntities() {
        MapTileModel[][] map =  model.getMap();
        for(int i = 0; i < model.getDepth(); i++){
            for(int j = 0; j < model.getWidth(); j++)
                if (map[i][j] != null) {
                    ElementView view = ViewFactory.makeView(game, map[i][j]);
                    view.update(map[i][j]);
                    view.draw(game.getBatch());
                }
        }

        ArrayList<EnemyModel> enemies = model.getEnemies();

        for(int i = 0; i < enemies.size(); i++){
            if(enemies.get(i) == null){
                enemyViews.remove(i);
                enemies.remove(i);
                i--;
                continue;
            }
            enemyViews.get(i).update(enemies.get(i));
            enemyViews.get(i).act(0.1f);
            enemyViews.get(i).draw(game.getBatch());
        }

        ArrayList<BulletModel> bullets = model.getBullets();
        for(int i = 0; i < bullets.size(); i++){
            ElementView view = ViewFactory.makeView(game, bullets.get(i));
            view.update(bullets.get(i));
            view.draw(game.getBatch());
        }

        HeroModel hero = model.getHeroModel();
        HeroView view = new HeroView(game);
        //ElementView view = ViewFactory.makeView(game, hero);
        view.update(hero);
        view.act(0.1f);
        view.draw(game.getBatch());

    }
}
