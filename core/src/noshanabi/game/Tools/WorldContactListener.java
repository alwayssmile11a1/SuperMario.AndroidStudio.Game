package noshanabi.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import noshanabi.game.Item.Item;
import noshanabi.game.MainClass;
import noshanabi.game.Sprites.Enemy;
import noshanabi.game.Sprites.InteractiveTileObject;
import noshanabi.game.Sprites.Mario;

/**
 * Created by 2SMILE2 on 18/09/2017.
 */

    public class WorldContactListener implements ContactListener {


    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits|fixB.getFilterData().categoryBits;


        switch (cDef)
        {
            case MainClass.MARIO_HEAD_BIT|MainClass.BRICK_BIT:
            case MainClass.MARIO_HEAD_BIT|MainClass.COIN_BIT:
                if(fixA.getFilterData().categoryBits==MainClass.MARIO_HEAD_BIT)
                {
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Mario) fixA.getUserData());
                }
                else
                {
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Mario) fixB.getUserData());
                }
                break;
            case MainClass.ENEMY_HEAD_BIT|MainClass.MARIO_BIT:
                if(fixA.getFilterData().categoryBits==MainClass.ENEMY_HEAD_BIT)
                {
                    ((Enemy)(fixA.getUserData())).hitOnHead();
                }
                else
                {
                        ((Enemy)(fixB.getUserData())).hitOnHead();
                }
                break;
            case MainClass.ENEMY_BIT| MainClass.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits==MainClass.ENEMY_BIT)
                {
                    ((Enemy)(fixA.getUserData())).reverseVelocity(true,false);
                }
                else
                {
                        ((Enemy)(fixB.getUserData())).reverseVelocity(true,false);
                }
                break;
            case MainClass.MARIO_BIT|MainClass.ENEMY_BIT:
                Gdx.app.log("MARIO","DIED");
                break;
            case MainClass.ENEMY_BIT|MainClass.ENEMY_BIT:
                ((Enemy)(fixA.getUserData())).reverseVelocity(true,false);
                ((Enemy)(fixB.getUserData())).reverseVelocity(true,false);
                break;
            case MainClass.ITEM_BIT| MainClass.OBJECT_BIT:
                if(fixA.getFilterData().categoryBits==MainClass.ITEM_BIT)
                {
                    ((Item)(fixA.getUserData())).reverseVelocity(true,false);
                }
                else
                {
                    ((Item)(fixB.getUserData())).reverseVelocity(true,false);
                }
                break;
            case MainClass.ITEM_BIT| MainClass.MARIO_BIT:
                if(fixA.getFilterData().categoryBits==MainClass.ITEM_BIT)
                {
                    ((Item)(fixA.getUserData())).use((Mario) fixB.getUserData());
                }
                else
                {
                    ((Item)(fixB.getUserData())).use((Mario) fixA.getUserData());
                }
                break;
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
