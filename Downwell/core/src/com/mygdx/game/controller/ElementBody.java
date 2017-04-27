package com.mygdx.game.controller;

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

    public ElementBody(World world, ElementModel model) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(model.getX(), model.getY());
        //bodyDef.angle = model.getRotation();

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

    public float getX() {
        return body.getPosition().x;
    }
    public float getY() {
        return body.getPosition().y;
    }
}
