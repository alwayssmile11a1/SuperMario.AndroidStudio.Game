package noshanabi.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import noshanabi.game.Screens.PlayScreen;

/**
 * Created by 2SMILE2 on 22/09/2017.
 */

public class Turtle extends Enemy{

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public Turtle(PlayScreen screen, float x, float y)
    {
        super(screen,x,y);
    }

    @Override
    protected void defineEnemy() {

    }

    @Override
    public void hitOnHead() {

    }

    @Override
    public void update(float dt) {

    }
}
