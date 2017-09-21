package noshanabi.game.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import noshanabi.game.MainClass;
import noshanabi.game.Screens.PlayScreen;

/**
 * Created by 2SMILE2 on 18/09/2017.
 */

public class Mario extends Sprite {

    public enum State {FALLING, JUMPING, STANDING, RUNNING, GROWING};
    public State currentState;
    public State previousState;

    public World world;
    public Body b2body;

    private TextureRegion marioStand;
    private Animation<TextureRegion> marioRun;
    private TextureRegion marioJump;
    private TextureRegion bigMarioStand;
    private TextureRegion bigMarioJump;
    private Animation<TextureRegion> bigMarioRun;
    private Animation<TextureRegion> growMario;


    private boolean runningRight;
    private float stateTimer;
    public boolean marioIsBig;
    private boolean runGrowAnimarion;
    private boolean timeToDefineBigMario;


    public Mario(PlayScreen screen)
    {

        this.world = screen.getWorld();

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer =0;
        runningRight=true;
        runGrowAnimarion = false;
        marioIsBig = false;
        Array<TextureRegion> frames = new Array<TextureRegion>();
        for(int i=0;i<4;i++)
        {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("little_mario"),i*16,0,16,16));

        }
        marioRun = new Animation<TextureRegion>(0.1f,frames);


        marioJump = new TextureRegion(screen.getAtlas().findRegion("little_mario"),80,0,16,16);

        defineMario();

        marioStand = new TextureRegion(screen.getAtlas().findRegion("little_mario"),0, 0, 16, 16);
        setBounds(0,0,16/ MainClass.PTM, 16/MainClass.PTM);
        setRegion(marioStand);

        //bigMario
        bigMarioStand = new TextureRegion(screen.getAtlas().findRegion("big_mario"),0,0,16,32);
        bigMarioJump = new TextureRegion(screen.getAtlas().findRegion("big_mario"),80,0,16,32);
        frames.clear();
        for(int i=1;i<4;i++)
        {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),i*16,0,16,32));

        }
        bigMarioRun = new Animation<TextureRegion>(0.1f,frames);


        //growing mario
        frames.clear();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),240,0,16,32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),0,0,16,32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),240,0,16,32));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("big_mario"),0,0,16,32));
        growMario = new Animation<TextureRegion>(0.2f,frames);


    }

    public void defineMario()
    {
        //create body (center mass of body, body type)
        BodyDef bDef = new BodyDef();
        bDef.position.set(32/ MainClass.PTM,32/MainClass.PTM);
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        //create the shape of body
        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ MainClass.PTM);
        fDef.filter.categoryBits = MainClass.MARIO_BIT;
        fDef.filter.maskBits = MainClass.ITEM_BIT|MainClass.DEFAULT_BIT|MainClass.COIN_BIT|MainClass.BRICK_BIT|MainClass.ENEMY_BIT|MainClass.OBJECT_BIT|MainClass.ENEMY_HEAD_BIT;

        fDef.shape = shape;
        b2body.createFixture(fDef).setUserData(this);

        //the top sensor of mario
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2/MainClass.PTM,6/MainClass.PTM),new Vector2(2/MainClass.PTM,6/MainClass.PTM));
        fDef.filter.categoryBits = MainClass.MARIO_HEAD_BIT;
        fDef.shape = head;
        fDef.isSensor =true;
        b2body.createFixture(fDef).setUserData(this);

    }

    public void defineBigMario()
    {
        Vector2 currentPosition = b2body.getPosition();
        world.destroyBody(b2body);

        //create body (center mass of body, body type)
        BodyDef bDef = new BodyDef();
        bDef.position.set(currentPosition.add(0,10/MainClass.PTM));
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bDef);

        //create the shape of body
        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/ MainClass.PTM);
        fDef.filter.categoryBits = MainClass.MARIO_BIT;
        fDef.filter.maskBits = MainClass.ITEM_BIT|MainClass.DEFAULT_BIT|MainClass.COIN_BIT|MainClass.BRICK_BIT|MainClass.ENEMY_BIT|MainClass.OBJECT_BIT|MainClass.ENEMY_HEAD_BIT;

        fDef.shape = shape;
        b2body.createFixture(fDef).setUserData(this);
        shape.setPosition(new Vector2(0,-14/MainClass.PTM));
        b2body.createFixture(fDef).setUserData(this);

        //the top sensor of mario
        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2/MainClass.PTM,6/MainClass.PTM),new Vector2(2/MainClass.PTM,6/MainClass.PTM));
        fDef.filter.categoryBits = MainClass.MARIO_HEAD_BIT;
        fDef.shape = head;
        fDef.isSensor =true;
        b2body.createFixture(fDef).setUserData(this);
        timeToDefineBigMario = false;
    }

    public void update(float dt)
    {
        if(marioIsBig) {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2 -6/MainClass.PTM);
        }
        else
        {
            setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        }
        setRegion(getFrame(dt));
        if(timeToDefineBigMario)
        {
            defineBigMario();
        }
    }

    public TextureRegion getFrame(float dt)
    {
        currentState = getState();

        TextureRegion region;
        switch (currentState)
        {
            case GROWING:
                region=growMario.getKeyFrame(stateTimer);
                if(growMario.isAnimationFinished(stateTimer))
                {
                    runGrowAnimarion=false;
                }
                break;
            case JUMPING:
                region = marioIsBig?bigMarioJump:marioJump;
                break;
            case RUNNING:
                region = marioIsBig?bigMarioRun.getKeyFrame(stateTimer,true):marioRun.getKeyFrame(stateTimer,true);;
                break;
            case FALLING:
            case STANDING:
            default:
                region = marioIsBig? bigMarioStand:marioStand;
                break;
        }
        if((b2body.getLinearVelocity().x<0||!runningRight)&&!region.isFlipX())
        {
            region.flip(true,false);
            runningRight = false;
        }
        else
        {
            if((b2body.getLinearVelocity().x>0 ||runningRight)&& region.isFlipX())
            {
                region.flip(true,false);
                runningRight = true;
            }
        }

        stateTimer = currentState == previousState?stateTimer+dt:0;
        previousState = currentState;
        return region;

    }

    public State getState()
    {
        if(runGrowAnimarion)
        {
            return State.GROWING;
        }

        if(b2body.getLinearVelocity().y>0 || (b2body.getLinearVelocity().y<0 && previousState == State.JUMPING))
        {
            return State.JUMPING;
        }
        else {
            if (b2body.getLinearVelocity().y < 0) {
                    return State.FALLING;
            } else {
                if (b2body.getLinearVelocity().x != 0) {
                    return State.RUNNING;
                } else {
                    return State.STANDING;
                }
            }
        }

    }

    public void grow()
    {
        timeToDefineBigMario = true;
        runGrowAnimarion=true;
        marioIsBig = true;
        setBounds(getX(),getY(),getWidth(),getHeight()*2);
        MainClass.audioManager.get("audio/sounds/powerup.wav", Sound.class).play();
    }
}
