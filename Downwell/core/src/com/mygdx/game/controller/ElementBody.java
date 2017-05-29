package com.mygdx.game.controller;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.model.ElementModel;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody;

import static com.mygdx.game.view.GameView.PIXEL_TO_METER;

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

    final void createFixture(Body body, float[] vertexes, int width, int height, float density, float friction, float restitution) {
        // Transform pixels into meters, center and invert the y-coordinate
        for (int i = 0; i < vertexes.length; i++) {
            if (i % 2 == 0) vertexes[i] -= width / 2;   // center the vertex x-coordinate
            if (i % 2 != 0) vertexes[i] -= height / 2;  // center the vertex y-coordinate

            if (i % 2 != 0) vertexes[i] *= -1;          // invert the y-coordinate

            vertexes[i] *= PIXEL_TO_METER;              // scale from pixel to meter
        }

        PolygonShape polygon = new PolygonShape();
        polygon.set(vertexes);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = polygon;

        fixtureDef.density = density;
        fixtureDef.friction = friction;
        fixtureDef.restitution = restitution;

        body.createFixture(fixtureDef);

        polygon.dispose();
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
            createExtraFixtures();

        rectangle.dispose();
    }

    public void createExtraFixtures() {
        PolygonShape rectangle = new PolygonShape();
        Vector2 v = body.getLocalCenter();
        v.set(v.x,(float)(v.y + 0.35));
        rectangle.setAsBox(0.35f, 0.2f,v,0);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;

        fixtureDef.density = 1f;
        fixtureDef.friction = 0;
        fixtureDef.restitution = 0;

        above = body.createFixture(fixtureDef);
        above.setUserData("up");

        v.set(v.x,(float)(v.y - 0.8));
        rectangle.setAsBox(0.3f, 0.1f, v,0);
        fixtureDef.shape = rectangle;
        under = body.createFixture(fixtureDef);
        under.setUserData("down");
    }

    public Fixture getAbove() { return above;}
    public Fixture getUnder() { return under;}

    public float getX() {
        return body.getPosition().x;
    }
    public float getY() {
        return body.getPosition().y;
    }

    public void setTransform(float x, float y, float angle) {
        body.setTransform(x, y, angle);
    }

    /*public Object getUserData() {
        return body.getUserData();
    }*/
}
