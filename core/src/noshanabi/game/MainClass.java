package noshanabi.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import noshanabi.game.Screens.PlayScreen;

//Hello hello
public class MainClass extends Game {

	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PTM = 100;

	public static final short NOTHING_BIT = 0;
	public static final short DEFAULT_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static final short ENEMY_HEAD_BIT = 128;
	public static final short ITEM_BIT = 256;
	public static final short MARIO_HEAD_BIT = 512;

	public static AssetManager audioManager;

	public SpriteBatch batch;


	@Override
	public void create () {
		batch = new SpriteBatch();

		audioManager = new AssetManager();
		audioManager.load("audio/music/mario_music.ogg", Music.class);
		audioManager.load("audio/sounds/coin.wav",Sound.class);
		audioManager.load("audio/sounds/bump.wav",Sound.class);
		audioManager.load("audio/sounds/breakblock.wav",Sound.class);
		audioManager.load("audio/sounds/powerup.wav",Sound.class);
		audioManager.load("audio/sounds/powerdown.wav",Sound.class);
		audioManager.load("audio/sounds/stomp.wav",Sound.class);
		audioManager.load("audio/sounds/mariodie.wav",Sound.class);
		audioManager.finishLoading();
		setScreen(new PlayScreen(this));

	}

	@Override
	public void render () {
		super.render();
		audioManager.update();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		audioManager.dispose();
	}
}
