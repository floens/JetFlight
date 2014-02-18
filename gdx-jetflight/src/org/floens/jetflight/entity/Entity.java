package org.floens.jetflight.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.floens.jetflight.collision.AABB;
import org.floens.jetflight.level.Level;
import org.floens.jetflight.level.Tile;
import org.floens.jetflight.screen.Screen;
import org.floens.jetflight.util.Vec2int;

public class Entity {
    public float x;
    public float y;
    public float width = 1f;
    public float height = 1f;
    public boolean removed = false;
    
    /**
     * Can this entity call onCollide on other entities
     */
    public boolean isSolid = false;
    
    /**
     * This entity won't collide with anything, and still move
     */
    public boolean isGhost = false;
    
    /**
     * This entity is immovable
     */
    public boolean isStatic = false;
    
    protected final Level level;
    protected float xa;
    protected float ya;
    protected boolean isCollidedX;
    protected boolean isCollidedY;
    /**
     * Random is only available if the level is not null
     */
    protected Random random;
    
    private final AABB bb;
    
    /**
     * Level can be null
     * @param level
     */
    public Entity(Level level) {
        this.level = level;
        if (level != null) {
        	this.random = level.random;        	
        }
        
        bb = AABB.pool.obtain().set(x, y, x + width, y + height);
    }
    
    public void add() {
        if (level != null) {
            level.addEntity(this);
        }
    }
    
    public void remove() {
        removed = true;
    }
    
    public void tick() {
        if (!isStatic) {
            move(xa, ya);
        }
        
        if (level != null && isSolid) {
        	doCollideCheck();
        }
    }
    
    public void render(Screen screen, float x, float y) {
	}

	public void setPosition(float x, float y) {
	    setPosition(x, y, xa, ya);
	}

	public void setPosition(float x, float y, float nxa, float nya) {
	    bb.set(x, y, x + width, y + height);
	    this.x = x;
	    this.y = y;
	    this.xa = nxa;
	    this.ya = nya;
	}

	public void strike(int damage, Entity source) {
        onStrike(damage, source);
    }
    
    /**
     * Entity went out of the bottom of the screen
     */
    public void onOutOfScreen() {
        remove();
    }
    
    public AABB getBB() {
        return bb;
    }
    
    /**
	 * Override this if you want to catch damages, don't call directly: call strike()
	 * @param amount
	 * @param source
	 */
	protected void onStrike(int amount, Entity source) {
	}

	/**
	 * Called when colliding with another entity
	 * @param e The other entity
	 */
	protected void onCollide(Entity e) {
	}

	private void move(float mx, float my) {
	    float xo = xa;
	    float yo = ya;
	    
	    if (isGhost || level == null) {
	        bb.offset(mx, my);
	    } else {
	        List<Vec2int> collidedTiles = new ArrayList<Vec2int>();
	        
	        AABB checkBox = bb.expand(xa, ya);
	        
	        List<AABB> boundingBoxes = new ArrayList<AABB>();
	        
	        for (int bx = (int)checkBox.minX; bx <= ((int)checkBox.maxX) + 1; bx++) {
	            for (int by = (int)checkBox.minY; by <= ((int)checkBox.maxY) + 1; by++) {
	                Tile tile = level.tiles.getTile(bx, by);
	                if (tile != null) {
	                	if (tile.solid) {
	                		boundingBoxes.add(AABB.pool.obtain().set(bx, by, bx + 1f, by + 1f));
	                	}
	                	
	                    collidedTiles.add(Vec2int.pool.obtain().set(bx, by));
	                }
	            }
	        }
	        
	        for (AABB xaBox : boundingBoxes) {
	            xa = xaBox.solveX(xa, bb);
	        }
	        bb.offset(xa, 0);
	
	        for (AABB yaBox : boundingBoxes) {
	            ya = yaBox.solveY(ya, bb);
	        }
	        bb.offset(0, ya);
	        
	        for (Vec2int e : collidedTiles) {
	            Tile tile = level.tiles.getTile(e.x, e.y);
	            if (tile != null) {
	                tile.onCollide(this, level, e.x, e.y);
	            }
	        }
	    }
	
	    isCollidedX = xo != xa;
	    isCollidedY = yo != ya;
	
	    if (isCollidedX) xa = 0;
	    if (isCollidedY) ya = 0;
	
	    x = bb.minX;
	    y = bb.minY;
	}

	private void doCollideCheck() {
		List<Entity> entities = level.getEntities();
		
		for (Entity e : entities) {
			if (e.getBB().intersects(bb)) {
				e.onCollide(this);
			}
		}
	}
}





