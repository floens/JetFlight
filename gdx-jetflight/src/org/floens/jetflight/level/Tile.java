package org.floens.jetflight.level;

import org.floens.jetflight.entity.Entity;
import org.floens.jetflight.screen.Screen;

public class Tile {
    private static int idCounter = 0;
    
    public static Tile[] byId = new Tile[100];
    
    public static Tile AIR = new Tile().setSolid(false);
    public static Tile WALL = new TileWall();
    public static Tile WALL1 = new TileWall1();
    public static Tile WALL2 = new TileWall2();
    public static Tile WALL3 = new TileWall3();
    public static Tile HEART = new TileHeart().setSolid(false);
    
    public int id;
    public boolean solid = true;
    
    public Tile() {
        int id = idCounter++;
        
        byId[id] = this;
        
        this.id = id;
    }
    
    public Tile setSolid(boolean i) {
        solid = i;
        return this;
    }
    
    public void onCollide(Entity entity, Level level, int x, int y) {
        
    }
    
    public void render(Screen screen, float drawX, float drawY, Level level, int tileX, int tileY) {
        
    }
}
