package noshanabi.game.Item;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import noshanabi.game.MainClass;
import noshanabi.game.Screens.PlayScreen;
import noshanabi.game.Sprites.Mario;

/**
 * Created by 2SMILE2 on 20/09/2017.
 */

public class Mushroom extends Item {

    public Mushroom(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        setRegion(screen.getAtlas().findRegion("mushroom"),0,0,16,16);
        velocity = new Vector2(0.7f,0);
    }

    @Override
    public void defineItem() {
        //create body (center mass of body, body type)
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(),getY());
        bDef.type = BodyDef.BodyType.DynamicBody;
        body  = world.createBody(bDef);

        //create the shape of body
        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ MainClass.PTM);
        fDef.filter.categoryBits = MainClass.ITEM_BIT;
        fDef.filter.maskBits = MainClass.DEFAULT_BIT|MainClass.COIN_BIT|MainClass.BRICK_BIT|MainClass.ENEMY_BIT|MainClass.OBJECT_BIT|MainClass.MARIO_BIT;

        fDef.shape = shape;
        body.createFixture(fDef).setUserData(this);
    }

    @Override
    public void use(Mario mario) {
        mario.grow();
        destroy();
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        if(!destroyed) {
            setPosition(body.getPosition().x - getWidth() / 2, body.getPosition().y - getHeight() / 2);

            velocity.y = body.getLinearVelocity().y;
            body.setLinearVelocity(velocity);
        }
    }
}
