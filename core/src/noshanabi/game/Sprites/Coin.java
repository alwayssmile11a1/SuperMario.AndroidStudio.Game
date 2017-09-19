package noshanabi.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import noshanabi.game.MainClass;

/**
 * Created by 2SMILE2 on 18/09/2017.
 */

public class Coin extends InteractiveTileObject {
    public Coin(World world, TiledMap map, Rectangle bounds)
    {
        super(world,map,bounds);
        fixture.setUserData(this);
        setCategoryFilter(MainClass.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin","Collision");
    }
}
