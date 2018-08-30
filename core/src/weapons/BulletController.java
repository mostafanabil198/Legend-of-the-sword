package weapons;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class BulletController {

    private World world;
    private Array<Bullet> bullets = new Array<Bullet>();

    public BulletController(World world) {
        this.world = world;
    }

    public void AddBullets(float pX, float pY, float dir) {

        bullets.add(new Bullet(world, pX, pY, dir));

    }

    public void BulletRemoval() {
        for (int i = 0; i < bullets.size; i++) {
            if (bullets.get(i).collide) {
                bullets.removeIndex(i);
                i--;
            }
        }
    }

    public void drawBullets(SpriteBatch batch) {
        for (Bullet bullet : bullets) {
            bullet.updateBullet();
            batch.draw(bullet, bullet.getX() - bullet.getWidth() / 2 , bullet.getY() - bullet.getHeight() / 2);
        }
    }

    public void updateBullets() {
        for (Bullet bullet : bullets) {
            bullet.getBody().setLinearVelocity(bullet.SPEED, 0);
        }
    }


}
