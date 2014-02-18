package org.floens.jetflight.entity;

import org.floens.jetflight.graphics.Assets;
import org.floens.jetflight.level.Level;
import org.floens.jetflight.screen.Screen;

import com.badlogic.gdx.graphics.Texture;

public class EntityUfo extends EntityBird {
	private Texture ufoTexture;
	
	public EntityUfo(Level level) {
		super(level);
		
		ufoTexture = Assets.ufo[0];
		switch(random.nextInt(3)) {
		case 1:
			ufoTexture = Assets.ufo[1];
			break;
		case 2:
			ufoTexture = Assets.ufo[2];
			break;
		}
	}
	
	@Override
	public void render(Screen screen, float x, float y) {
		screen.draw(ufoTexture, x, y, 23, 14, 0, 0, 23, 14, speed > 0f, false);
	}
}
