package land;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.World;

public class ObjectFactory {
    
    public IObject createObject(MapObject object, World world, float ppm) {
        if(object.getName().equals("collision")) {
            Bricks b = new Bricks();
            b.setObject(object);
            b.setWorld(world);
            b.setPPM(ppm);
            b.create();
            return b;
        } else if (object.getName().equals("stair")) {
            Stairs s = new Stairs();
            s.setObject(object);
            s.setWorld(world);
            s.setPPM(ppm);
            s.create();
            return s;
        } else if (object.getName().equals("stair")) {
            Door d = new Door();
            d.setObject(object);
            d.setWorld(world);
            d.setPPM(ppm);
            d.create();
            return d;
        }
        return null;
    }
}
