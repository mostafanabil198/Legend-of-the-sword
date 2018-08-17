package player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import helpers.GameInfo;

public class Player extends Sprite {
    private World world;
    private Body body;
    private String action = "Idle";
    private TextureAtlas playerAtlas;
    private Animation<TextureRegion> animation;
    private float elapsedTime;
    public int jumpc = 1;
    public boolean right = true;

    public Player(World world, float x, float y) {

        super(new Texture("player/Idle__000.png"));
        this.world = world;
        setPosition(x, y);
        createBody();
        playerAtlas = new TextureAtlas("Player Animations/Idle/Idle Animation.atlas");
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);
        body = world.createBody(bodyDef);
        body.setFixedRotation(true);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(getWidth() / 2f / GameInfo.PPM, getHeight() / 2f / GameInfo.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 4f;
        //fixtureDef.friction = 2f;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("player");
        shape.dispose();
    }


    public void drawPlayer(SpriteBatch batch) {
        TextureAtlas temp = playerAtlas;
        temp.dispose();
        playerAtlas = new TextureAtlas("Player Animations/" + action + "/" + action + " Animation.atlas");
        elapsedTime += Gdx.graphics.getDeltaTime();
        Array<TextureAtlas.AtlasRegion> frames = playerAtlas.getRegions();
        for (TextureRegion frame : frames) {
            if ((body.getLinearVelocity().x < 0 || !right) && !frame.isFlipX()) {
                frame.flip(true, false);
            } else if ((body.getLinearVelocity().x > 0 || right) && frame.isFlipX()) {
                frame.flip(true, false);
            }
        }
        animation = new Animation<TextureRegion>(1 / 10f, playerAtlas.getRegions());
        batch.draw(animation.getKeyFrame(elapsedTime, true), getX(), getY());
    }

    public void movePlayer(float x, float y) {
        if (x > 0 && this.isFlipX()) {
            this.flip(true, false);
        } else if (x < 0 && !this.isFlipX()) {
            this.flip(true, false);
        }
        //body.setLinearVelocity(x, y);
        body.applyLinearImpulse(new Vector2(x,y),body.getWorldCenter(),true);
        //body.applyLinearImpulse(new Vector2(x, y), body.getWorldCenter(), true);

    }

    public void moveJumpPlayer() {

        body.applyLinearImpulse(new Vector2(0, 15), body.getWorldCenter(), true);

    }

    public void moveClimbPlayer() {
        body.setLinearVelocity(0, 2);

    }

    public void updatePlayer() {
        setPosition(body.getPosition().x * GameInfo.PPM, body.getPosition().y * GameInfo.PPM);
    }


    public void setAction(String action) {
        this.action = action;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
