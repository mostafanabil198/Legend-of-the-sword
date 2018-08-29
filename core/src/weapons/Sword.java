package weapons;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameMain;

import helpers.GameInfo;
import player.Player;

public class Sword {
    private World world;
    private Body swordBody;
    private GameMain game;
    private Player player;
    private Body body;
    private boolean created = false;

    public Sword(World world, GameMain game, Player player) {
        this.game = game;
        this.world = world;
        this.player = player;
    }

    public void createSwordRight() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((player.getBody().getPosition().x) + .5f, (player.getBody().getPosition().y));
        body = world.createBody(bodyDef);
        body.setFixedRotation(true);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50f / GameInfo.PPM, 50f / GameInfo.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 4;
        fixtureDef.filter.categoryBits = GameInfo.SWORD;
        fixtureDef.filter.maskBits = GameInfo.BRICK;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("sword");
        fixture.setSensor(true);
        shape.dispose();
        created = true;
    }

    public void createSwordLeft() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set((player.getBody().getPosition().x) - .5f, (player.getBody().getPosition().y));
        body = world.createBody(bodyDef);
        body.setFixedRotation(true);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50f / GameInfo.PPM, 50f / GameInfo.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 4;
        fixtureDef.filter.categoryBits = GameInfo.SWORD;
        fixtureDef.filter.maskBits = GameInfo.BRICK;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("sword");
        fixture.setSensor(true);
        shape.dispose();
        created = true;
    }

    public void dispose() {
        world.destroyBody(body);
        created = false;
    }

    public boolean isCreated() {
        return created;
    }
}
