package noshanabi.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import noshanabi.game.Item.ItemDef;
import noshanabi.game.Item.Mushroom;
import noshanabi.game.MainClass;
import noshanabi.game.Screens.PlayScreen;

/**
 * Created by 2SMILE2 on 18/09/2017.
 */

public class Coin extends InteractiveTileObject {

    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;
    public Coin(PlayScreen screen, Rectangle bounds)
    {
        super(screen,bounds);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(MainClass.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin","Collision");
        if(getCell().getTile().getId() == BLANK_COIN)
        {
            MainClass.audioManager.get("audio/sounds/bump.wav", Sound.class).play();
        }
        else
        {
            MainClass.audioManager.get("audio/sounds/coin.wav", Sound.class).play();
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x,body.getPosition().y+16/MainClass.PTM), Mushroom.class));
        }
        getCell().setTile(tileSet.getTile(BLANK_COIN));
        PlayScreen.hud.addScore(100);


    }
}
