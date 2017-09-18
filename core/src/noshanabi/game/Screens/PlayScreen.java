package noshanabi.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import noshanabi.game.MainClass;
import noshanabi.game.Scenes.Hud;
import noshanabi.game.Sprites.Mario;
import noshanabi.game.Tools.B2WorldCreator;
import noshanabi.game.Tools.WorldContactListener;

/**
 * Created by 2SMILE2 on 17/09/2017.
 */

public class PlayScreen implements Screen {

    //mainClass
    private MainClass game;
    private TextureAtlas atlas;

    //how well we want to see our map
    private Viewport gamePort;

    //a Camera to adjust viewport position
    private OrthographicCamera gameCam;


    //manage things like score, health, etc
    private Hud hud;

    //these variables help to create and render a tiled map
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //World manages physics, collisions,etc.
    private World world;

    //this variable helps us to see the virtual shape of our world (virtual shape of all objects for example)
    //this variable should be eliminated when public the game
    private Box2DDebugRenderer b2dr;

    //Main Character
    Mario mario;

    //Constructor
    public PlayScreen(MainClass game)
    {
        //set MainClass
        this.game = game;

        atlas = new TextureAtlas("Mario_and_Enemies.pack");

        //declare an orthographic Cam
        gameCam = new OrthographicCamera();

        //determine the portion you want to see of a map
        //gamePort = new FitViewport(MainClass.V_WIDTH, MainClass.V_HEIGHT,gameCam);
        gamePort = new StretchViewport(MainClass.V_WIDTH/MainClass.PTM, MainClass.V_HEIGHT/MainClass.PTM,gameCam);

        //set camera position
        gameCam.position.set(gamePort.getWorldWidth()/2,gamePort.getWorldHeight()/2,0);

        //create a new hud
        hud = new Hud(game.batch);

        //render our map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/MainClass.PTM);


        //Create our World
        world = new World(new Vector2(0,-10),true);

        //create 2D debug renderer (we can see virtual shape by this)
        b2dr = new Box2DDebugRenderer();


        new B2WorldCreator(world,map);

        mario = new Mario(world, this);

        world.setContactListener(new WorldContactListener());
    }

    @Override
    public void show() {

    }

    public TextureAtlas getAtlas()
    {
        return atlas;
    }

    public void handleInput(float dt)
    {
        if(Gdx.input.isKeyJustPressed(Input.Keys.UP))
        {
            mario.b2body.applyLinearImpulse(new Vector2(0,4f),mario.b2body.getWorldCenter(),true);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) && mario.b2body.getLinearVelocity().x<=1 ) {
            mario.b2body.applyLinearImpulse(new Vector2(0.1f, 0), mario.b2body.getWorldCenter(),true);
            System.out.printf("%f \n",mario.b2body.getLinearVelocity().x);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT) && mario.b2body.getLinearVelocity().x>=-1) {
            mario.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), mario.b2body.getWorldCenter(),true);
            System.out.printf("%f \n",mario.b2body.getLinearVelocity().x);
        }

    }

    public void update(float dt)
    {
        handleInput(dt);

        world.step(1/60f,6,2);

        mario.update(dt);

        gameCam.position.x = mario.b2body.getPosition().x;

        gameCam.update();

        renderer.setView(gameCam);

    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        mario.draw(game.batch);
        game.batch.end();


        b2dr.render(world,gameCam.combined);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }
}
