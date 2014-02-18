package org.floens.jetflight.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static Music gameLoop;
	
    public static FileHandle text;
    public static Texture star;
    public static TextureRegion[][] tiles;
    public static TextureRegion player;
    public static Texture button;
    public static Texture buttonPressed;
    public static Texture button40;
    public static Texture buttonPressed40;
    public static Texture score;
    public static TextureRegion[] playerFire;
    public static TextureRegion jetPack;
    public static TextureRegion[] smoke;
    public static Texture cityBackground;
    public static Texture[] bird;
    public static Texture[] crow;
    public static Texture[] pigeon;
    public static Texture[] seagull;
    public static Texture[] parachute;
    public static Texture[] ufo;
    public static Texture stars;
    
    public static void load() {
    	gameLoop = loadMusic("sound/game_loop.mp3");
    	gameLoop.setLooping(true);
    	
        text = Gdx.files.internal("text.png");
        star = load("star.png");
        tiles = split("tiles.png", 12, 12);
        player = loadRegion("player_cutout.png", 0, 0, 16, 22);
        button = load("button.png");
        buttonPressed = load("button_pressed.png");
        button40 = load("button40.png");
        buttonPressed40 = load("button_pressed40.png");
        score = load("score.png");
        
        playerFire = new TextureRegion[3];
        playerFire[0] = loadRegion("player_cutout.png", 0, 48, 8, 12);
        playerFire[1] = loadRegion("player_cutout.png", 8, 48, 8, 12);
        playerFire[2] = loadRegion("player_cutout.png", 16, 48, 8, 12);
        
        jetPack = loadRegion("player_cutout.png", 0, 32, 22, 8);
        
        smoke = new TextureRegion[4];
        smoke[0] = loadRegion("smoke.png", 0, 0, 1, 1);
        smoke[1] = loadRegion("smoke.png", 1, 0, 1, 1);
        smoke[2] = loadRegion("smoke.png", 2, 0, 1, 1);
        smoke[3] = loadRegion("smoke.png", 3, 0, 1, 1);
        
        cityBackground = load("city_background.png");
        
        bird = new Texture[2];
        bird[0] = load("birds/bird1.png");
        bird[1] = load("birds/bird2.png");
        
        crow = new Texture[2];
        crow[0] = load("birds/crow1.png");
        crow[1] = load("birds/crow2.png");
        
        pigeon = new Texture[2];
        pigeon[0] = load("birds/pigeon1.png");
        pigeon[1] = load("birds/pigeon2.png");
        
        seagull = new Texture[2];
        seagull[0] = load("birds/seagull1.png");
        seagull[1] = load("birds/seagull2.png");
        
        parachute = new Texture[2];
        parachute[0] = load("parachute/parachute1.png");
        parachute[1] = load("parachute/parachute2.png");
        
        ufo = new Texture[3];
        ufo[0] = load("ufo/ufo1.png");
        ufo[1] = load("ufo/ufo2.png");
        ufo[2] = load("ufo/ufo3.png");
        
        stars = load("stars.png");
    }
    
    private static Music loadMusic(String name) {
    	return Gdx.audio.newMusic(Gdx.files.internal(name));
    }
    
    private static TextureRegion[][] split(String name, int width, int height) {
    	Texture texture = new Texture(Gdx.files.internal(name));
    	return TextureRegion.split(texture, width, height);
    }
    
    private static TextureRegion loadRegion(String name, int x, int y, int width, int height) {
        Texture texture = new Texture(Gdx.files.internal(name));
        return new TextureRegion(texture, x, y, width, height);
    }
    
    private static Texture load(String name) {
        return new Texture(Gdx.files.internal(name));
    }
}
