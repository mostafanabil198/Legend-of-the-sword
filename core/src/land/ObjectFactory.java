package land;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
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
        } else if (object.getName().equals("stairs")) {
            Stairs s = new Stairs();
            s.setObject(object);
            s.setWorld(world);
            s.setPPM(ppm);
            s.create();
            return s;
        }
        return null;
    }
}
