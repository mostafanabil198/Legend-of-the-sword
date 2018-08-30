package land;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.TextureMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.mygdx.game.GameMain;

import java.util.ArrayList;
import java.util.List;

import player.Player;
import weapons.BulletController;

public class MapRenderer extends OrthogonalTiledMapRenderer {
    private Sprite sprite;
    private List<Sprite> sprites;
    private int drawSpritesAfterLayer = 3;
    private Player player;
    private GameMain game;
    private BulletController bulletController;

    public MapRenderer(TiledMap map) {
        super(map);
        sprites = new ArrayList<Sprite>();
    }

    public void addSprite(Sprite sprite, int drawSpritesAfterLayer, GameMain game, BulletController bulletController) {
        sprites.add(sprite);
        player = (Player) sprite;
        this.game = game;
        this.drawSpritesAfterLayer = drawSpritesAfterLayer;
        this.bulletController = bulletController;
    }

    @Override
    public void render() {
        beginRender();
        int currentLayer = 0;

        for (MapLayer layer : map.getLayers()) {
            bulletController.drawBullets(game.getBatch());
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer(((TiledMapTileLayer) layer));
                    currentLayer++;
                    if (currentLayer == drawSpritesAfterLayer) {
//                        for(Sprite sprite : sprites)
//                            sprite.draw(this.getBatch());
                        player.drawPlayer(this.getBatch());

                    }
                } else {
                    for (MapObject object : layer.getObjects()) {
                        renderObject(object);
                    }
                }
            }
        }
        endRender();
    }

}