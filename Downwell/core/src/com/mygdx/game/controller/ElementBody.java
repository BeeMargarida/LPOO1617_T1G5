package com.mygdx.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.model.ElementModel;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * ElementBody is class that creates keeps information about the body and fixtures of the entity. it has the methods to create the fixtures
 * of the body and defines it's type and position.
 */
public abstract class ElementBody {

    protected Body body;

    protected Fixture above;
    protected Fixture under;

    /**
     * Constructor that defines a body, sets it's position and type and adds it it's data(model).
     * @param world the physics world
     * @param model the model of the element to be used in the super class constructor
     * @param bodyType Dynamic or Kinematic were the ones used
     */
    public ElementBody(World world, ElementModel model, BodyDef.BodyType bodyType) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = bodyType;
        bodyDef.position.set(model.getX(), model.getY());
        body = world.createBody(bodyDef);
        body.setUserData(model);
    }

    /**
     * Creates the fixture with the data given and checks if it is necessary to create extra fixtures.
     * @param body the physics world
     * @param width width of the fixture to be created
     * @param height height of the fixture to be created
     * @param density density of the fixture to be created
     * @param friction friction of the fixture to be created
     * @param restitution restitution of the fixture to be created
     * @param sensors if true creates extra fixtures, else is doesn't
     * @see ElementBody#createExtraFixtures(float, float)
     */
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

    /**
     * Creates extra fixtures for the body. This fixtures, one above and other under, will serve to check collisions in certain areas
     * and with certain bodies.
     * @param width width of the body
     * @param height height of the body
     */
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

    /**
     * Returns the x coordinate of the current position of the body.
     * @return x coordinate
     */
    public float getX() {
        return body.getPosition().x;
    }

    /**
     * Returns the y coordinate of the current position of the body.
     * @return y coordinate
     */
    public float getY() {
        return body.getPosition().y;
    }

}
