package org.floens.jetflight.level;

import java.util.ArrayList;
import java.util.List;


public class Tiles {
	private static final int CHUNK_HEIGHT = 100;
	
    private final LevelGen levelGen;
    private final int width;
    private final List<TileChunk> chunks = new ArrayList<TileChunk>();
    
    public Tiles(int width, Level level) {
        this.width = width;
        this.levelGen = new LevelGen(this, level);
    }
    
    public int getWidth() {
        return width;
    }
    
    public void setTile(int x, int y, Tile tile) {
        if (!tilePositionValid(x, y)) return;
        
        TileChunk chunk = getChunk(y);
        chunk.setTileId(x, y % CHUNK_HEIGHT, tile.id);
    }
    
    public Tile getTile(int x, int y) {
    	if (!tilePositionValid(x, y)) return null;
    	
        TileChunk chunk = getChunk(y);
        
        levelGen.generate(y);
        
        int id = chunk.getTileId(x, y % CHUNK_HEIGHT);
        if (id >= 0 && id < Tile.byId.length) {
            return Tile.byId[id];
        } else {
            return null;
        }
    }
    
    private boolean tilePositionValid(int x, int y) {
    	return x >= 0 && x < width && y >= 0;
    }
    
    private TileChunk getChunk(int y) {
    	if (y < 0) throw new UnsupportedOperationException("Y must be more or equal to 0");
    	
    	int index = y / CHUNK_HEIGHT;
    	
    	while (chunks.size() - 1 < index) {
    		TileChunk chunk = new TileChunk(width, CHUNK_HEIGHT, index);
        	chunks.add(chunk);
    	}
    	
    	return chunks.get(index);
    }
}





