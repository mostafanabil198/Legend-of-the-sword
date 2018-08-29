package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.GameMain;

import helpers.GameInfo;
import land.Land;
import land.MapRenderer;
import land.ObjectFactory;
import player.Player;

public class GamePlay implements Screen, ContactListener {
    private GameMain game;
    private World world;
    private OrthographicCamera mainCamera;
    private Viewport gameViewport;
    private OrthographicCamera box2dCamera;
    private Box2DDebugRenderer debugRenderer;
    //private Texture bg;
    private Player player;
    private Land land;
    private Body body;
    private boolean climb = false;
    private TiledMap map;
    private MapRenderer mapRenderer;
    private ObjectFactory objectFactory;
    private MapLayers layers;
    private MapObjects objects;
    private boolean climbStair = false;

    public GamePlay(GameMain game) {
        this.game = game;
        world = new World(new Vector2(0, -9.8f), true);
        world.setContactListener(this);
        player = new Player(world, 1000, 500);
        //INITALIZE GAME's PLAYGROUND
        map = new TmxMapLoader().load("map/map1.tmx");
        mapRenderer = new MapRenderer(map);
        objectFactory = new ObjectFactory();
        layers = map.getLayers();
        objects = layers.get("col").getObjects();
        for (MapObject object : objects) {
            objectFactory.createObject(object, world, GameInfo.PPM);
        }
        mapRenderer.addSprite(player, 3, game);
        //bg = new Texture("bg.jpg");
        //MAIN CAMERA FOR ALL THE GAME
        mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);
        gameViewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, mainCamera);
        //BODIES RENDERER
        box2dCamera = new OrthographicCamera();
        box2dCamera.setToOrtho(false, GameInfo.WIDTH / GameInfo.PPM, GameInfo.HEIGHT / GameInfo.PPM);
        debugRenderer = new Box2DDebugRenderer();
        //land = new Land(world, 0, 0);
    }

    void inputsHandle() {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && player.jumpc != 0) {
            player.jumpc = 0;
            player.setAction("Jump");
            player.moveJumpPlayer();
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.right = false;
            player.setAction("Run");
            if (!(player.getBody().getLinearVelocity().x > 2.5f || player.getBody().getLinearVelocity().x < -2.5f)) {
                player.movePlayer(-.5f, 0);
            }
            if (climb && !climbStair) {
                world.destroyBody(body);
                climb = false;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.right = true;
            player.setAction("Run");
            if (!(player.getBody().getLinearVelocity().x > 2.5f || player.getBody().getLinearVelocity().x < -2.5f)) {
                player.movePlayer(.5f, 0);
            }
            if (climb && !climbStair) {
                world.destroyBody(body);
                climb = false;
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP) && climbStair) {
            player.setAction("Climb");
            player.moveClimbPlayer();
            CreateclimbBody();
        } else if (Gdx.input.isKeyPressed(Input.Keys.CONTROL_LEFT)) {
            player.setAction("Attack");

        } else if (player.jumpc == 1) {
            player.setAction("Idle");
        }
    }

    private void updateCamera() {
        //x: 996.0813 y: 432.82983

        if (player.getX() - 50 > 996.0813 && player.getX() - 50 < 2204.633) {
            mainCamera.position.set(player.getX() - 50, mainCamera.position.y, 0);
        }
        if (player.getY() - 50 > 368.92964 && player.getY() - 50 < 1231.3258) {
            mainCamera.position.set(mainCamera.position.x, player.getY() - 50, 0);
        }

       // System.out.println("x: " + (player.getX() - 50) + " y: " + (player.getY() - 50));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        updateCamera();
        inputsHandle();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().begin();
        //game.getBatch().draw(bg, 0, 0);
        mapRenderer.setView(mainCamera);
        mapRenderer.render();
//        player.drawPlayer(game.getBatch());
//        land.drawPlayer(game.getBatch());
        game.getBatch().end();
        debugRenderer.render(world, box2dCamera.combined);
        game.getBatch().setProjectionMatrix(mainCamera.combined);
        mainCamera.update();
        player.updatePlayer();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }

    @Override
    public void beginContact(Contact contact) {
        if (contact.getFixtureA().getUserData() == "player" && contact.getFixtureB().getUserData() == "brick") {
            player.jumpc = 1;
            climbStair = false;
        }
        if (contact.getFixtureA().getUserData() == "brick" && contact.getFixtureB().getUserData() == "player") {
            player.jumpc = 1;
            climbStair = false;
        }
        if (contact.getFixtureA().getUserData() == "stair" && contact.getFixtureB().getUserData() == "player") {
            //check if the up button is pressed and apply the climbing action
            player.jumpc = 1;
            climbStair = true;
        }
        if (contact.getFixtureA().getUserData() == "player" && contact.getFixtureB().getUserData() == "stair") {
            //check if the up button is pressed and apply the climbing action
            player.jumpc = 1;
            climbStair = true;
        }
        if (contact.getFixtureA().getUserData() == "door" && contact.getFixtureB().getUserData() == "player") {
            /*
            if(player has the key){
                layers.remove(layers.get("door")); // remove the layer that contains the door 
                world.destroyBody(contact.getFixtureA().getBody()); // destroy the body
            } else {
                player.jumpc = 1;
            }
            */
        }
        if (contact.getFixtureA().getUserData() == "player" && contact.getFixtureB().getUserData() == "door") {
            /*
            if(player has the key){
                layers.remove(layers.get("door")); // remove the layer that contains the door
                world.destroyBody(contact.getFixtureA().getBody()); // destroy the body
            } else {
                player.jumpc = 1;
            }
            */
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    void CreateclimbBody() {
        if (climb) {
            world.destroyBody(body);
        }
        climb = true;
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((player.getX()) / GameInfo.PPM, (player.getY() - 1) / GameInfo.PPM);
        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(player.getWidth() / 4 / GameInfo.PPM, 1 / GameInfo.PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("land");
        shape.dispose();

    }

}
