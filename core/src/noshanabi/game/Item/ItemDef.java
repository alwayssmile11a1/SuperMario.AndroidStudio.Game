package noshanabi.game.Item;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by 2SMILE2 on 20/09/2017.
 */

public class ItemDef {
    public Vector2 position;
    public Class<?> type;

    public ItemDef(Vector2 position,Class<?> type)
    {
        this.position=position;
        this.type=type;
    }
}

