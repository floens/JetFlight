package org.floens.jetflight.level;

import java.util.Random;

import org.floens.jetflight.entity.EntityBird;
import org.floens.jetflight.entity.EntityParachute;
import org.floens.jetflight.entity.EntityUfo;
import org.floens.jetflight.util.Logger;

public class LevelGen {
    private final Random random = new Random();
    private final Tiles tiles;
    private final Level level;
    private int maxY;
    
    public LevelGen(Tiles tiles, Level level) {
    	this.tiles = tiles;
    	this.level = level;
    }
    
    public void generate(int y) {
        if (y >= maxY) {
        	if (y >= 0 && y < 200) {
        		maxY = 200;
        		
        		generateCity(0, 200);
        	} else if (y >= 200 && y < 600) {
        		maxY = 600;
        		
        		generateSky(200, 600);
        	} else if (y >= 600 && y < 800) {
        		maxY = 800;
        		
        		generateSky(600, 800);
        	} else if (y >= 800) {
        		maxY += 200;
        		
        		generateSpace(maxY - 200, maxY);
        	}
        }
    }
    
    private void generateCity(int startY, int endY) {
    	Logger.d("Generating city");
    	
    	int offset = 3;
    	int height = 3;
    	for (int y = startY; y < endY; y += height) {
    		if (y > 20 && random.nextInt(1) == 0) {
    			offset += random.nextInt(3) - 1;
    		}
    		
    		int gap = 4 + random.nextInt(2);
    		if (y > 400 - 50) {
    			gap += 1;
    		}
    		if (y > 400 - 20) {
    			gap += 1;
    		}
    		
    		if (offset < 1) offset = 1;
    		if (offset > 6) offset = 6;
    		
    		for (int x = 0; x < 10; x++) {
    			if (x < offset || x >= offset + gap) {
    				for (int yy = 0; yy < height; yy++) {
    					setWall(x, y + yy);
    				}
    			}
        	}
    	}
    	
    	makeHearts(startY, endY, 0.02f);
    }
    
    private void generateSky(int startY, int endY) {
    	Logger.d("Generating sky");
    	
    	int counter = 20;
    	for (int y = startY; y < endY; y += 1) {
    		counter--;
    		if (counter < 0) {
    			counter = 8 + random.nextInt(8);
    			
    			if (random.nextInt(10) == 0) {
    				spawnParachute(y);    				
    			} else {
    				spawnBird(y);
    			}
    		}
    	}
    	
    	makeHearts(startY, endY, 0.02f);
    }
    
    private void generateSpace(int startY, int endY) {
    	Logger.d("Generating space");
    	
    	int counter = 20;
    	for (int y = startY; y < endY; y += 1) {
    		counter--;
    		if (counter < 0) {
    			counter = 8 + random.nextInt(8);
    			
    			if (random.nextInt(10) == 0) {
    				spawnBird(y); 				
    			} else {
    				spawnUfo(y);   
    			}
    		}
    	}
    	
    	makeHearts(startY, endY, 0.01f);
    }
    
    private void spawnBird(int y) {
    	EntityBird bird = new EntityBird(level);
    	bird.setPosition(random.nextFloat() * 10f, y);
    	bird.add();
    }
    
    private void spawnParachute(int y) {
    	EntityParachute p = new EntityParachute(level);
    	p.setPosition(random.nextFloat() * 8f, y);
    	p.add();
    }
    
    private void spawnUfo(int y) {
    	EntityUfo p = new EntityUfo(level);
    	p.setPosition(random.nextFloat() * 8f, y);
    	p.add();
    }
    
    private void setWall(int x, int y) {
    	if (random.nextInt(40) == 0) {
    		switch (random.nextInt(3)) {
    		case 0:
    			set(x, y, Tile.WALL1);
    			break;
    		case 1:
    			set(x, y, Tile.WALL2);
    			break;
    		case 2:
    			set(x, y, Tile.WALL3);
    			break;
    		}
    	} else {
    		set(x, y, Tile.WALL);
    	}
    }
    
    private void makeHearts(int minY, int maxY, float density) {
    	for (int i = 0, j = (int)(density * (maxY - minY)); i < j; i++) {
    		int x = random.nextInt(10);
    		int y = minY + random.nextInt(maxY - minY);
    		
    		if (get(x, y) == Tile.AIR && !tileInArea(Tile.HEART, x, y, 10)) {
    			set(x, y, Tile.HEART);
    		}
    	}
    }
    
    private boolean tileInArea(Tile tile, int ax, int ay, int area) {
    	for (int x = Math.max(ax - area, 0); x < Math.min(ax + area, 10); x++) {
    		for (int y = ay - area; y < ay + area; y++) {
    			if (get(x, y) == tile) {
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }
    
    private Tile get(int x, int y) {
    	return tiles.getTile(x, y);
    }
    
    private void set(int x, int y, Tile tile) {
    	tiles.setTile(x, y, tile);
    }
}





