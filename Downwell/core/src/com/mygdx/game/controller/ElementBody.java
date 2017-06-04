package com.mygdx.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.model.ElementModel;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public abstract class ElementBody {

    protected Body body;

    protected Fixture above;
    protected Fixture under;

    public ElementBody(World world, ElementModel model, BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(model.getX(), model.getY());
        body = world.createBody(bodyDef);
        body.setUserData(model);
    }

    final void createRectangleFixture(Body body, float width, float height, float density, float friction, float restitution, boolean sensors){
        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width, height);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;

        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        body.createFixture(fixtureDef);
        body.setFixedRotation(true);
        if(sensors)
            createExtraFixtures(width,height);

        rectangle.dispose();
    }

    public void createExtraFixtures(float width, float height) {
        PolygonShape rectangle = new PolygonShape();
        Vector2 v = body.getLocalCenter();
        v.set(v.x,(float)(v.y + height - 0.1));
        rectangle.setAsBox(width, 0.2f,v,0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;

        fixtureDef.density = 1f;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;

        above = body.createFixture(fixtureDef);
        above.setUserData("up");

        v.set(v.x,(float)(v.y - 2*height + 0.2));
        rectangle.setAsBox(0.4f, 0.2f, v,0);
        fixtureDef.shape = rectangle;
        under = body.createFixture(fixtureDef);
        under.setUserData("down");
    }

    public float getX() {
        return body.getPosition().x;
    }
    public float getY() {
        return body.getPosition().y;
    }

}
