package land;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class Stairs implements IObject {
    private MapObject object;
    private World world;
    private float ppm;
    @Override
    public void setObject(MapObject object) {
        this.object = object;
    }
    @Override
    public void setWorld(World world) {
        this.world = world;
    }
    @Override
    public void setPPM(float ppm) {
        this.ppm = ppm;
        
    }
    @Override
    public Body create() {
        Rectangle rect = ((RectangleMapObject) object).getRectangle();
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set((rect.getX() + rect.getWidth() / 2) / ppm,
                (rect.getY() + rect.getHeight() / 2) / ppm);
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((rect.width * 0.5f) / ppm, (rect.getHeight() * 0.5f) / ppm);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        body.setUserData("stair");
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }


}
