package noshanabi.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;

import noshanabi.game.MainClass;
import noshanabi.game.Screens.PlayScreen;

/**
 * Created by 2SMILE2 on 19/09/2017.
 */

public class Goomba extends Enemy {

    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private boolean setToDestroy;
    private boolean destroyed;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        defineEnemy();
        frames = new Array<TextureRegion>();
        for(int i=0;i<2;i++)
        {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"),i*16,0,16,16));
        }

        walkAnimation = new Animation<TextureRegion>(0.4f,frames);
        stateTime = 0;
        setBounds(getX(),getY(),16/MainClass.PTM,16/MainClass.PTM);

        setToDestroy = false;
        destroyed = false;

    }

    @Override
    public void update(float dt)
    {
        stateTime+=dt;
        if(setToDestroy && !destroyed)
        {
            world.destroyBody(b2body);
            destroyed = true;
            setRegion(new TextureRegion(screen.getAtlas().findRegion("goomba")),32,0,16,16);
            stateTime = 0;
        }
        else
        {
            if(!destroyed) {
                b2body.setLinearVelocity(velocity);
                setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
                setRegion(walkAnimation.getKeyFrame(stateTime, true));
            }
        }

    }

    @Override
    protected void defineEnemy() {


        //create body (center mass of body, body type)
        BodyDef bDef = new BodyDef();
        bDef.position.set(getX(),getY());
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        //create the shape of body
        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ MainClass.PTM);
        fDef.filter.categoryBits = MainClass.ENEMY_BIT;
        fDef.filter.maskBits = MainClass.DEFAULT_BIT|MainClass.COIN_BIT|MainClass.BRICK_BIT|MainClass.ENEMY_BIT|MainClass.OBJECT_BIT|MainClass.MARIO_BIT;

        fDef.shape = shape;
        b2body.createFixture(fDef).setUserData(this);

        //Create the head here
        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5,8).scl(1/MainClass.PTM);
        vertice[1] = new Vector2(5,8).scl(1/MainClass.PTM);
        vertice[2] = new Vector2(-3,3).scl(1/MainClass.PTM);
        vertice[3] = new Vector2(3,3).scl(1/MainClass.PTM);
        head.set(vertice);
        fDef.shape = head;
        fDef.restitution = 0.5f;
        fDef.filter.categoryBits = MainClass.ENEMY_HEAD_BIT;
        b2body.createFixture(fDef).setUserData(this);

        b2body.setActive(false);
    }

    @Override
    public void draw (Batch batch)
    {
        if(!destroyed||stateTime<1)
        {
            super.draw(batch);
        }
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;
    }
}
