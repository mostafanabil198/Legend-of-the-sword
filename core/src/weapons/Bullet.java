package weapons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


import helpers.GameInfo;

public class Bullet extends Sprite {
    public int SPEED;
    private World world;
    private Body body;
    boolean collide = false;

    public Bullet(World world, float x, float y, float dir) {
        super(new Texture("weapons/knife.png"));
        this.world = world;

        if (dir >= 0) {
            SPEED = 10;
            setPosition(x, y);
        } else if (dir < 0) {
            SPEED = -10;
            setPosition(x, y);
        }
        createBody();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 3f) / GameInfo.PPM, (getHeight() / 3f) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        //fixtureDef.friction=2f;


        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Bullet");
        shape.dispose();

    }

    public Body getBody() {
        return body;
    }

    public void updateBullet() {
        this.setPosition(body.getPosition().x * GameInfo.PPM, body.getPosition().y * GameInfo.PPM);
    }


}
