package com.brunomonteiro.mariobros2.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.brunomonteiro.mariobros2.MarioBros;
import com.brunomonteiro.mariobros2.Scenes.Hud;
import com.brunomonteiro.mariobros2.Sprites.Mario;

/**
 * Created by codecadet on 21/11/2017.
 */
public class PlayScreen implements Screen {

    private MarioBros game;
    Texture texture;
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;

    private Mario player;


    public PlayScreen(MarioBros game) {
        this.game = game;
        //create cam used to follow mario through cam world
        gameCam = new OrthographicCamera();
        // create a fitviwport to maintain aspect ratio....
        gamePort = new FitViewport(MarioBros.V_WIDTH/MarioBros.PPM, MarioBros.V_HEIGHT/MarioBros.PPM, gameCam);

        //create hud
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("mariomap.tmx");
        renderer = new OrthogonalTiledMapRenderer(map,1/ MarioBros.PPM);
        gameCam.position.set((gamePort.getWorldWidth() / 2), (gamePort.getWorldHeight() / 2), 0);


        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        player = new Mario(world);

// ground
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rec = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rec.getX() + rec.getWidth() / 2)/MarioBros.PPM, (rec.getY() + rec.getHeight() / 2)/ MarioBros.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rec.getWidth() / 2)/MarioBros.PPM, (rec.getHeight() / 2)/MarioBros.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);


        }

        //pipe
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rec = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rec.getX() + rec.getWidth() / 2)/MarioBros.PPM, (rec.getY() + rec.getHeight() / 2)/ MarioBros.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rec.getWidth() / 2)/MarioBros.PPM, (rec.getHeight() / 2)/MarioBros.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);


        }
        //brick
        for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rec = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rec.getX() + rec.getWidth() / 2)/MarioBros.PPM, (rec.getY() + rec.getHeight() / 2)/ MarioBros.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rec.getWidth() / 2)/MarioBros.PPM, (rec.getHeight() / 2)/MarioBros.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);


        }
        //coin
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rec = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rec.getX() + rec.getWidth() / 2)/MarioBros.PPM, (rec.getY() + rec.getHeight() / 2)/ MarioBros.PPM);

            body = world.createBody(bdef);

            shape.setAsBox((rec.getWidth() / 2)/MarioBros.PPM, (rec.getHeight() / 2)/MarioBros.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);


        }
    }

    @Override
    public void show() {

    }

    public void update(float dt) {
        handleInput(dt);
        world.step(1 / 60f, 6, 2);
        gameCam.position.x = player.b2body.getPosition().x;
        gameCam.update();
        renderer.setView(gameCam);


    }

    private void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.b2body.applyLinearImpulse(new Vector2(0,4f), player.b2body.getWorldCenter(),true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)&& player.b2body.getLinearVelocity().x <=2){
            player.b2body.applyLinearImpulse(new Vector2(0.1f,0),player.b2body.getWorldCenter(),true );
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)&& player.b2body.getLinearVelocity().x >=-2){
            player.b2body.applyLinearImpulse(new Vector2(-0.1f,0),player.b2body.getWorldCenter(),true );
        }

    }

    @Override
    public void render(float delta) {

        update(delta);


        //clear the game screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
// render game map
        renderer.render();

        // renderer our Box"DDebuglines

        b2dr.render(world, gameCam.combined);
// set our batch to now draw what the hud camera sees.
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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

    }
}
