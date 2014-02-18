package org.floens.jetflight.entity;

import java.util.Random;

import org.floens.jetflight.graphics.Assets;
import org.floens.jetflight.level.Level;
import org.floens.jetflight.particle.Smoke;
import org.floens.jetflight.screen.Screen;
import org.floens.jetflight.util.Prefs;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class EntityPlayer extends Entity {
    private final float rotationThreshold = 0.005f;
    
    private int damageTimeout = -1;
    private float fireState = 0f;
    
    private boolean outOfScreen = false;
    
    private int highestAltitude;
    private int score;
    private final int maxHealth = 3;
    private int health = maxHealth;
    
    public EntityPlayer(Level level) {
        super(level);
        
        width = 1.3f;
        height = 2f;
        isSolid = true;
        
        setPosition(x, y);
    }
    
    @Override
    public void tick() {
        fireState += 0.5f;
        if (damageTimeout >= 0) damageTimeout--;
        
        if (health <= 0) {
            isGhost = true;
        }
        
        // Smoothen out the input: it counts for an amount of the old xa.
        xa += (getInputRotation() - xa) * 0.2f;
        
        if (health > 0) {
             ya += 0.02f;
        }
        // if (ya > 0.2f) ya = 0.2f; ORIGINAL
        if (ya > 0.175f) ya = 0.175f;
        
        ya -= 0.01f;
        
        if (isCollidedY) {
            strike(1, null);
        }
        
        if (x + xa < 0f) {
            setPosition(0f - xa, y);
        }
        if (x + xa > 10f - width) {
            setPosition(10f - width - xa, y);
        }
        
        super.tick();
        
        if ((int)y > highestAltitude) {
            int added = (int)y - highestAltitude;
            addScore(added);
            highestAltitude += added;
        }
        
        if (level != null) {
            emitSmoke();
        }
    }
    
    @Override
    public void render(Screen screen, float x, float y) {
        if (damageTimeout % 16 < 8) {
            int w = Assets.player.getRegionWidth();
            int h = Assets.player.getRegionHeight();
            
            float rotation = xa * -100f;
            
            screen.draw(Assets.player, x, y, w / 2, h / 2, w, h, 1, 1, rotation);
            
//            int state = (int) ((1d + Math.sin(fireState)) * 1.5d);
            
            int state = (int)(fireState) % 4;
            
            if (state < 0) state = 0;
            if (state > 2) state = 1;
            
            screen.draw(Assets.jetPack, x - 3, y + 4, 11, 7, 22, 8, 1, 1, rotation);
            
            screen.draw(Assets.playerFire[state], x + 4, y - 8, 4, 19, 8, 12, 1, 1, rotation);
        }
    }

    public int getScore() {
        return score;
    }
    
    public int getHealth() {
        return health;
    }
    
    public boolean getOutOfScreen() {
        return outOfScreen;
    }

    public void addScore(int score) {
        this.score += score;
    }
    
    public void addHealth(int health) {
    	this.health += health;
    	this.health = Math.min(this.health, this.maxHealth);
    }
    
    @Override
    public void onOutOfScreen() {
        outOfScreen = true;
        remove();
    }

    @Override
    public void onStrike(int amount, Entity source) {
        if (damageTimeout < 0) {
            damageTimeout = 60;
            health--;
            
            ya = -0.2f;
        }
    }
    
    public float getInputRotation() {
    	float rawRotation = 0f;
    	
        if (Gdx.app.getType() == ApplicationType.Android) {
        	float added = Prefs.prefs.getFloat("calibratedX", 0f);
        	rawRotation = (Gdx.input.getAccelerometerX() - added) * -0.1f;
        } else if (Gdx.app.getType() == ApplicationType.Desktop) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            	rawRotation = -0.3f;
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            	rawRotation = 0.3f;
            }
        }
        
        float rotation = 0f;
        
        // Don't be too sensitive on the rotation: it only counts if it's above a threshold
        if (rawRotation > rotationThreshold) {
            rotation = rawRotation - rotationThreshold;
        } else if (rawRotation < -rotationThreshold) {
            rotation = rawRotation + rotationThreshold;
        }
        
        return rotation;
    }
    
    private void emitSmoke() {
        Random random = level.random;
        
        for (int i = 0, j = 5 + random.nextInt(5); i < j; i++) {
            Smoke smoke = level.smokePool.obtain();
            smoke.setPosition(
                    x + width / 2f + (random.nextFloat() - random.nextFloat()) * 0.3f, 
                    y - 0.5f       + (random.nextFloat() - random.nextFloat()) * 0.4f,
                    (random.nextFloat() - random.nextFloat()) * 0.04f,
                    0f
                );
            smoke.add();
        }
    }
}





