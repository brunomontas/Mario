package com.brunomonteiro.mariobros2.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.brunomonteiro.mariobros2.MarioBros;
import com.brunomonteiro.mariobros2.Screens.PlayScreen;


/**
 * Created by codecadet on 22/11/2017.
 */
public class Mario extends Sprite {
public enum State {FALLING, JUNPING, STANDING, RUNNING};
public State currentState;
public State previousState;
    public World world;
    public Body b2body;
    private TextureRegion marioStand;
    private Animation<TextureRegion> marioRun;
    private Animation<TextureRegion> marioJump;



    private boolean runningright;
    private float stateTimer;

    public Mario(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("little_mario"));
        this.world = world;
        defineMario();
        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);
        setBounds(0, 0, 16 / MarioBros.PPM, 16 / MarioBros.PPM);
        setRegion(marioStand);
        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0;
        runningright  =true;

        Array<TextureRegion> frames =  new Array<TextureRegion>();
        for(int i=1; i<4; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 0,16,16));
            marioRun = new Animation<TextureRegion>(0.1f,frames);


        }
        frames.clear();
        for(int i=4; i<6; i++){
            frames.add(new TextureRegion(getTexture(), i*16, 0,16,16));
            marioJump = new Animation<TextureRegion>(0.1f,frames);


        }
        frames.clear();


    }

    public void update(float dt){
        setPosition(b2body.getPosition().x-getWidth()/2, b2body.getPosition().y-getHeight()/2);
        setRegion(getFrame(dt));
    }

    private TextureRegion getFrame(float dt) {
        currentState = getSate();
        TextureRegion region;
        switch (currentState){
            case JUNPING:
                region = marioJump.getKeyFrame(stateTimer);
            break;
            case RUNNING:
                region = marioRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            case STANDING:
                default:
                    region = marioStand;
                    break;
        }

        if((b2body.getLinearVelocity().x <0 || !runningright) && !region.isFlipX()){
            region.flip(true,false);
            runningright = false;
        }else  if ((b2body.getLinearVelocity().x > 0 || runningright) && region.isFlipX()){
            region.flip(true,false);
            runningright = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    private State getSate() {
        if(b2body.getLinearVelocity().y >0 || b2body.getLinearVelocity().y <0 && previousState == State.JUNPING){
            return State.JUNPING;
        }else if(b2body.getLinearVelocity().y <0){
            return State.FALLING;
        }else if(b2body.getLinearVelocity().x !=0){
            return State.RUNNING;
        }else {
            return State.STANDING;
        }
    }

    private void defineMario() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / MarioBros.PPM, 32 / MarioBros.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / MarioBros.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
}
