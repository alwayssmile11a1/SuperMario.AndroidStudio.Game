package noshanabi.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by 2SMILE2 on 18/09/2017.
 */

public class Mario extends Sprite {

    public World world;
    public Body b2body;

    public Mario(World world)
    {
        this.world = world;
        defineMario();
    }

    public void defineMario()
    {
        BodyDef bDef = new BodyDef();
        bDef.position.set(32,32);
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5f);
        fDef.shape = shape;
        b2body.createFixture(fDef);
    }


}
