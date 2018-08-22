package land;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import helpers.GameInfo;

public class Land extends Sprite {
    private World world;
    private Body body;
    private String action = "Idle";
    private TextureAtlas playerAtlas;
    private Animation<TextureRegion> animation;
    private float elapsedTime;

    public Land(World world, float x, float y) {

        super(new Texture("player/Idle__000.png"));
        this.world = world;
        setPosition(x, y);
        createBody();
        playerAtlas = new TextureAtlas("Player Animations/Idle/Idle Animation.atlas");
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((getX()+50)/GameInfo.PPM,getY()/GameInfo.PPM);
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(10000 /2/ GameInfo.PPM, 10/ GameInfo.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("land");
        shape.dispose();
    }


    public void drawPlayer(SpriteBatch batch) {
        batch.draw(this,getX(),getY());
    }

    public void movePlayer(float x, float y) {
        if (x > 0 && isFlipX()) {
            flip(true, false);
        } else if (x < 0 && !isFlipX()) {
            flip(true, false);
        }
        body.setLinearVelocity(x, y);

    }

    public void updatePlayer() {
        setPosition(body.getPosition().x * GameInfo.PPM, body.getPosition().y * GameInfo.PPM);
    }


    public void setAction(String action) {
        this.action = action;
    }
}

