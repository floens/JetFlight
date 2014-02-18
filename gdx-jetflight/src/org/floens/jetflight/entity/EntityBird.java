package org.floens.jetflight.entity;

import org.floens.jetflight.graphics.Assets;
import org.floens.jetflight.level.Level;
import org.floens.jetflight.screen.Screen;

import com.badlogic.gdx.graphics.Texture;

public class EntityBird extends Entity {
	public Texture[] birdAsset;
	
	protected float speed;
	
	private float animationFrame;
	
	public EntityBird(Level level) {
		super(level);
		
		width = 1.8f;
		height = 1.4f;
		
		switch(random.nextInt(4)) {
		case 0:
			birdAsset = Assets.bird;
			break;
		case 1:
			birdAsset = Assets.crow;
			break;
		case 2:
			birdAsset = Assets.pigeon;
			break;
		case 3:
			birdAsset = Assets.seagull;
			break;
		}
		
		isGhost = true;
		
		speed = 0.05f + random.nextFloat() * 0.1f;
		
		if (random.nextInt(2) == 0) {
			speed = -speed;
		}
	}
	
	@Override
	public void tick() {
		if (x < -1 && speed < 0f) {
			speed = -speed;
		}
		
		if (x > 11 && speed > 0f) {
			speed = -speed;
		}
		
		xa = speed;
		
		super.tick();
		
		animationFrame += 0.1f;
		while (animationFrame >= 2f) {
			animationFrame -= 2f;
		}
	}
	
	@Override
	public void render(Screen screen, float x, float y) {
		screen.draw(birdAsset[(int)(animationFrame)], x, y, 22, 18, 0, 0, 22, 18, speed > 0f, false);
	}
	
	@Override
	protected void onCollide(Entity e) {
		if (e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)e;
			
			player.strike(1, this);
		}
	}
}





