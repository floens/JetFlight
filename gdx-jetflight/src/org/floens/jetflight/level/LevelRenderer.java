package org.floens.jetflight.level;

import java.util.List;

import org.floens.jetflight.entity.Entity;
import org.floens.jetflight.screen.GameScreen;

public class LevelRenderer {
    private final Level level;
    private final Tiles tiles;
    private final GameScreen screen;
    
    public LevelRenderer(Level level, Tiles tiles, GameScreen screen) {
        this.level = level;
        this.tiles = tiles;
        this.screen = screen;
    }
    
    public void render() {
        renderTiles();
        renderEntities();
    }
    
    /*public float getLevelHeight() {
        return tiles.getHeight();
    }*/
    
    public int getTileSize() {
        return screen.width / tiles.getWidth();
    }
    
    private void renderTiles() {
        int tileSize = screen.width / tiles.getWidth();
        float width = tiles.getWidth();
        float height = screen.height / tileSize;
        
        float offsetY = level.getOffsetY();
        float startX = 0f;
        float startY = (int) offsetY;
        float screenWidth = width;
        float screenHeight = height + 3;
        
        for (float x = startX; x < startX + screenWidth; x++) {
            for (float y = startY; y < startY + screenHeight; y++) {
                float drawX = x * tileSize;
                float drawY = (y - offsetY) * tileSize;
                
                Tile tile = tiles.getTile((int)x, (int)y);
                if (tile != null) {
                    tile.render(screen, drawX, drawY, level, (int)x, (int)y);
                }
            }
        }
    }
    
    private void renderEntities() {
        List<Entity> entities = level.getEntities();
        
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            renderEntity(e);
        }
    }
    
    private void renderEntity(Entity e) {
        int tileSize = screen.width / tiles.getWidth();
        float drawX = e.x * tileSize;
        float drawY = (e.y - level.getOffsetY()) * tileSize;
        
        e.render(screen, drawX, drawY);
    }
}





