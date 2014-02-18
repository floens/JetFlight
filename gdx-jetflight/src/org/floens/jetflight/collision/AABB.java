package org.floens.jetflight.collision;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;


public class AABB extends Collidable implements Poolable {
	public static final Pool<AABB> pool = new Pool<AABB>() {
	    @Override
	    protected AABB newObject() {
	        return new AABB();
	    }
	};
	
    public float minX;
    public float minY;
    public float maxX;
    public float maxY;
    
    public AABB() {
        super();
    }
    
    @Override
	public void reset() {
	}

	public AABB set(float minX, float minY, float maxX, float maxY) {
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
        
        return this;
    }
    
    /**
     * Returns true if this AABB intersects with the other
     * @param other AABB
     */
    public boolean intersects(AABB other) {
        if (this.maxX <= other.minX || this.maxY <= other.minY) return false;
        if (this.minX >= other.maxX || this.minY >= other.maxY) return false;
        
        return true;
    }
    
    /**
     * Move this AABB
     * @param x
     * @param y
     */
    public void offset(float x, float y) {
        this.minX += x;
        this.maxX += x;
        this.minY += y;
        this.maxY += y;
    }
    
    /**
     * Expand and return a new AABB
     * @param x
     * @param y
     * @return AABB expanded from this
     */
    public AABB expand(float x, float y) {
        float minX = this.minX;
        float minY = this.minY;
        float maxX = this.maxX;
        float maxY = this.maxY;

        if (x < 0) minX += x;
        if (x > 0) maxX += x;
        if (y < 0) minY += y;
        if (y > 0) maxY += y;
        
        return AABB.pool.obtain().set(minX, minY, maxX, maxY);
    }
    
    /**
     * Solve this AABB with the other AABB on the x-axis
     * @param x
     * @param bb
     * @return distance
     */
    public float solveX(float x, AABB bb) {
        if (bb.maxY <= this.minY || bb.minY >= this.maxY) {
            return x;
        }
        if (x > 0 && bb.maxX <= this.minX) {
            float amount = this.minX - bb.maxX;
            if (amount < x) { 
                x = amount;
            }
        }
        if (x < 0 && bb.minX >= this.maxX) {
            float amount = this.maxX - bb.minX;
            if (amount > x) {
                x = amount;
            }
        }
        return x;
    }
    
    /**
     * Solve this AABB with the other AABB on the y-axis
     * @param y
     * @param bb
     * @return distance
     */
    public float solveY(float y, AABB bb) {
        if (bb.maxX <= this.minX || bb.minX >= this.maxX) {
            return y;
        }
        if (y > 0 && bb.maxY <= this.minY) {
            float amount = this.minY - bb.maxY;
            if (amount < y) {
                y = amount;
            }
        }
        if (y < 0 && bb.minY >= this.maxY) {
            float amount = this.maxY - bb.minY;
            if (amount > y) {
                y = amount;
            }
        }
        return y;
    }
}
