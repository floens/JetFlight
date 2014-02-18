package org.floens.jetflight.particle;

import org.floens.jetflight.entity.Entity;
import org.floens.jetflight.level.Level;

public class Particle extends Entity {
    public int life;
    
    public Particle(Level level) {
        super(level);
        
        isGhost = true;
    }
    
    @Override
    public void tick() {
        super.tick();
        
        life--;
        if (life < 0) {
            remove();
        }
    }
}
