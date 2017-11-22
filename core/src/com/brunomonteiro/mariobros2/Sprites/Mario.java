package com.brunomonteiro.mariobros2.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.brunomonteiro.mariobros2.MarioBros;

/**
 * Created by codecadet on 22/11/2017.
 */
public class Mario extends Sprite {

    public World world;
    public Body b2body;

    public Mario(World world) {
        this.world = world;
        defineMario();

    }

    private void defineMario() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/ MarioBros.PPM, 32/MarioBros.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5/MarioBros.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);

    }
}
