package noshanabi.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;

import noshanabi.game.MainClass;
import noshanabi.game.Screens.PlayScreen;

/**
 * Created by 2SMILE2 on 18/09/2017.
 */

    public class Brick extends InteractiveTileObject {
    public Brick(PlayScreen screen, Rectangle bounds)
    {
        super(screen,bounds);
        fixture.setUserData(this);
        setCategoryFilter(MainClass.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick","Collision");
        setCategoryFilter(MainClass.DESTROYED_BIT);
        getCell().setTile(null);
        PlayScreen.hud.addScore(200);
        MainClass.audioManager.get("audio/sounds/breakblock.wav", Sound.class).play();
    }
}
