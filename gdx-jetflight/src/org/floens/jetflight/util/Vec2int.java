package org.floens.jetflight.util;

import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Vec2int implements Poolable {
	public static final Pool<Vec2int> pool = new Pool<Vec2int>() {
	    @Override
	    protected Vec2int newObject() {
	        return new Vec2int();
	    }
	};
	
	public int x;
    public int y;
    
    public Vec2int() {
    }
	
	@Override
	public void reset() {
	}
	
    public Vec2int set(int x, int y) {
    	this.x = x;
    	this.y = y;
    	
    	return this;
    }
}
