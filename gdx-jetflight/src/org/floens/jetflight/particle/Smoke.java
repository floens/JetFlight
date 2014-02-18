package org.floens.jetflight.particle;

import org.floens.jetflight.graphics.Assets;
import org.floens.jetflight.level.Level;
import org.floens.jetflight.screen.Screen;

import com.badlogic.gdx.utils.Pool.Poolable;

public class Smoke extends Particle implements Poolable {
    private int stage = 3;
    
    public Smoke(Level level) {
        super(level);
        reset();
    }
    
    @Override
    public void reset() {
        life = 5 + level.random.nextInt(20);
    }
    
    @Override
    public void render(Screen screen, float x, float y) {
        if (level.random.nextInt(3) == 0) {
            stage--;
            if (stage < 0) stage = 0;
        }
        
        /*screen.shapeRenderer.begin(ShapeType.Filled);
        
        int color = stage == 3 ? 0xFFA86Bff : stage == 2 ? 0xFF7C30ff : stage == 1 ? 0x910B12ff : 0x4C4C4Cff;
        Color c = new Color();
        Color.rgba8888ToColor(c, color);
        
        screen.shapeRenderer.setColor(c);
        screen.shapeRenderer.rect(x, y, 2f, 2f);
        
        screen.shapeRenderer.end();*/
        
        screen.draw(Assets.smoke[3 - stage], x, y, 0f, 0f, 1f, 1f, 2f, 2f, 0f);
    }

    @Override
    public void remove() {
        super.remove();
        level.smokePool.free(this);
    }
}
