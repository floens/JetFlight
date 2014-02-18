package org.floens.jetflight.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.floens.jetflight.entity.Entity;
import org.floens.jetflight.entity.EntityPlayer;
import org.floens.jetflight.particle.Smoke;
import org.floens.jetflight.screen.GameScreen;

import com.badlogic.gdx.utils.Pool;

public class Level {
	public GameScreen game;
	public final Tiles tiles;
	public Random random = new Random();
	
	public final Pool<Smoke> smokePool = new Pool<Smoke>() {
	    @Override
	    protected Smoke newObject() {
	        return new Smoke(Level.this);
	    }
	};
    
    private final List<Entity> entities = new ArrayList<Entity>();
    private EntityPlayer player;
    
    /**
     * How far the camera has gone upwards.
     */
    private float offsetY;
    
    private final LevelRenderer levelRenderer;
    
    public Level(GameScreen game) {
    	this.game = game;
        
    	tiles = new Tiles(10, this);
    	levelRenderer = new LevelRenderer(this, tiles, game);
    	
        generate();
    }
    
    public void tick() {
    	// Tick entities
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).tick();
        }
        
        float h = game.height / levelRenderer.getTileSize();
        
        // Only move the screen up when the player goes up.
        float y = player.y - h + 12f;
        if (y > offsetY) offsetY = y;
        
//        if (offsetY > levelRenderer.getLevelHeight() - h) offsetY = levelRenderer.getLevelHeight() - h;
        
        // Remove entities out of the screen
        for (Entity e : entities) {
            if (e.y + e.height < getOffsetY()) {
                e.onOutOfScreen();
            }
        }
        
        // Async remove removed entities from the list
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e.removed) {
                entities.remove(i); 
                i--;
            }
        }
    }
    
    public void render() {
        levelRenderer.render();
    }

    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public List<Entity> getEntities() {
        return entities;
    }
    
    public EntityPlayer getPlayer() {
        return player;
    }
    
    /**
     * Bottom of the screen in tile size
     * @return offsetY
     */
    public float getOffsetY() {
        return offsetY;
    }
    
    private void generate() {
        EntityPlayer e = new EntityPlayer(this);
        e.setPosition(4.1f, 3f);
        e.add();
        
        player = e;
    }
}





