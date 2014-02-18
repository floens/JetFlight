package org.floens.jetflight.level;

import org.floens.jetflight.graphics.Assets;
import org.floens.jetflight.screen.Screen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class TileWall extends Tile {
	int normalX = 0;
	int normalY = 0;
	
    @Override
    public void render(Screen screen, float drawX, float drawY, Level level, int tileX, int tileY) {
    	if (tileY == 0) {
    		draw(screen, drawX, drawY, -1);
    		return;
    	}
    	
    	boolean u = shouldAttach(level.tiles, tileX, tileY + 1);
    	boolean r = shouldAttach(level.tiles, tileX + 1, tileY);
    	boolean b = shouldAttach(level.tiles, tileX, tileY - 1);
    	boolean l = shouldAttach(level.tiles, tileX - 1, tileY);
    	
    	if (!u && !r && b && l) {
    		draw(screen, drawX, drawY, 0);
    	} else if (l && u && !r && !b) {
    		draw(screen, drawX, drawY, 3);
    	} else if (u && r && !b && !l) {
    		draw(screen, drawX, drawY, 2);
    	} else if (b && r && !l && !u) {
    		draw(screen, drawX, drawY, 1);
    	} else {
    		draw(screen, drawX, drawY, -1);
    	}
    }
    
    private boolean shouldAttach(Tiles tiles, int tx, int ty) {
    	Tile t = tiles.getTile(tx, ty);
    	
    	return t == Tile.WALL || t == null || t == WALL1 || t == WALL2 || t == WALL3; 
    }
    
    private void draw(Screen screen, float drawX, float drawY, int i) {
    	float rotation = 0f;
    	if (i == 1) {
    		rotation = 90f;
    	} else if (i == 2) {
    		rotation = 180f;
    	} else if (i == 3) {
    		rotation = 270f;
    	}
    	
    	TextureRegion region = Assets.tiles[normalY][normalX];
    	
    	if (i >= 0) {
    		region = Assets.tiles[1][3];
    	}
    	
    	screen.draw(region, drawX, drawY, 6f, 6f, 12f, 12f, 1f, 1f, rotation);
    }
}
