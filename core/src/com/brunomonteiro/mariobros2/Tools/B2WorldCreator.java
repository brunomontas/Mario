package com.brunomonteiro.mariobros2.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.brunomonteiro.mariobros2.MarioBros;
import com.brunomonteiro.mariobros2.Sprites.Brick;
import com.brunomonteiro.mariobros2.Sprites.Coin;

/**
 * Created by codecadet on 22/11/2017.
 */
public class B2WorldCreator {

    public  B2WorldCreator(World world, TiledMap map){
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        // ground
        for (MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rec = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rec.getX() + rec.getWidth() / 2)/ MarioBros.PPM, (rec.getY() + rec.getHeight() / 2)/ MarioBros.PPM);

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

            new Brick(world,map,rec);


        }
        //coin
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {

            Rectangle rec = ((RectangleMapObject) object).getRectangle();

          new Coin(world,map,rec);


        }
    }
}
