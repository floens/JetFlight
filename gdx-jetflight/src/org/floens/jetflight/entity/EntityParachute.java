package org.floens.jetflight.entity;

import org.floens.jetflight.graphics.Assets;
import org.floens.jetflight.level.Level;
import org.floens.jetflight.screen.Screen;

import com.badlogic.gdx.graphics.Texture;

public class EntityParachute extends Entity {
	private final Texture asset;
	
	public EntityParachute(Level level) {
		super(level);
		
		width = 3f;
		height = 2f;
		
		asset = random.nextBoolean() ? Assets.parachute[0] : Assets.parachute[1];
		
		xa = 0.02f + random.nextFloat() * 0.05f;
		
		ya = -0.02f + random.nextFloat() * -0.05f;
	}
	
	@Override
	public void tick() {
		if (x > 11f && xa > 0f) {
			xa = -xa;
		}
		
		if (x < -1f && xa < 0f) {
			xa = -xa;
		}
		
		super.tick();
	}
	
	@Override
	public void render(Screen screen, float x, float y) {
		screen.draw(asset, x, y);
	}
	
	@Override
	protected void onCollide(Entity e) {
		if (e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)e;
			player.strike(1, this);
		}
	}
}
