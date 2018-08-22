package land;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public interface IObject {
    public void setObject(MapObject object);
    public void setWorld(World world);
    public void setPPM(float ppm);
    public Body create();
}
