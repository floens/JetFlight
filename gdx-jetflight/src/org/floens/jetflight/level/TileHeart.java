package org.floens.jetflight.level;

import org.floens.jetflight.entity.Entity;
import org.floens.jetflight.entity.EntityPlayer;
import org.floens.jetflight.graphics.Assets;
import org.floens.jetflight.screen.Screen;

public class TileHeart extends Tile {
	@Override
	public void render(Screen screen, float drawX, float drawY, Level level, int tileX, int tileY) {
        screen.draw(Assets.tiles[0][1], drawX, drawY);
    }
	
	@Override
	public void onCollide(Entity entity, Level level, int x, int y) {
		if (entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer)entity;
			
			player.addHealth(1);
			level.tiles.setTile(x, y, Tile.AIR);
		}
	}
}
