package noshanabi.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import noshanabi.game.MainClass;
import noshanabi.game.Screens.PlayScreen;
import noshanabi.game.Sprites.Brick;
import noshanabi.game.Sprites.Coin;
import noshanabi.game.Sprites.Goomba;

/**
 * Created by 2SMILE2 on 18/09/2017.
 */

public class B2WorldCreator {

    private Array<Goomba> goombas;

    public B2WorldCreator(PlayScreen screen)
    {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
        //these variables are used for loop below. Since the BodyDef, fDef, Shape and Body can be safely reused, this will optimize our game a little more
        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        //create ground bodies/fixtures
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class))
        {
            //create rigid body
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX()+rect.getWidth()/2)/ MainClass.PTM,(rect.getY()+rect.getHeight()/2)/MainClass.PTM);
            body=world.createBody(bDef);

            //create the shape of this body
            shape.setAsBox(rect.getWidth()/2/MainClass.PTM, rect.getHeight()/2/MainClass.PTM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MainClass.DEFAULT_BIT;
            body.createFixture(fdef);
        }

        //create pipe bodies/fixtures
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class))
        {
            //create rigid body
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set((rect.getX()+rect.getWidth()/2)/ MainClass.PTM,(rect.getY()+rect.getHeight()/2)/MainClass.PTM);
            body=world.createBody(bDef);

            //create the shape of this body
            shape.setAsBox(rect.getWidth()/2/MainClass.PTM, rect.getHeight()/2/MainClass.PTM);
            fdef.shape = shape;
            fdef.filter.categoryBits = MainClass.OBJECT_BIT;
            body.createFixture(fdef);
        }

        //create brick bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class))
        {
            new Brick(screen,object);
        }

        //create coin bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class))
        {

            new Coin(screen,object);
        }

        //create all goombas
        goombas = new Array<Goomba>();
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class))
        {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            goombas.add(new Goomba(screen,rect.getX()/MainClass.PTM,rect.getY()/MainClass.PTM));
        }
    }

    public Array<Goomba> getGoombas() {
        return goombas;
    }

}
