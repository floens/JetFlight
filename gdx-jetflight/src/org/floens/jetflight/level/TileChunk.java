package org.floens.jetflight.level;

import org.floens.jetflight.util.Logger;

public class TileChunk {
	private final int width;
	private final int height;
	
	private final int[] tiles;
	
	public TileChunk(int width, int height, int special) {
		this.width = width;
		this.height = height;
		
		tiles = new int[width * height];
		
		Logger.d("Chunk created: " + special);
	}
	
	public void setTileId(int x, int y, int id) {
		if (x < 0 || x >= width || y < 0 || y >= height) return;
		
		tiles[width * y + x] = id;
	}
	
	public int getTileId(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height) return -1;
		
		return tiles[width * y + x];
	}
}
