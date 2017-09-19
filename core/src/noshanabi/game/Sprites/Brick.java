package noshanabi.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import noshanabi.game.MainClass;

/**
 * Created by 2SMILE2 on 18/09/2017.
 */

    public class Brick extends InteractiveTileObject {
    public Brick(World world, TiledMap map, Rectangle bounds)
    {
        super(world,map,bounds);
        fixture.setUserData(this);
        setCategoryFilter(MainClass.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick","Collision");
        setCategoryFilter(MainClass.DESTROYED_BIT);
        getCell().setTile(null);
    }
}
