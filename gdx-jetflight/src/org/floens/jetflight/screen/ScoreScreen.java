package org.floens.jetflight.screen;

import org.floens.jetflight.graphics.TextSheet;

public class ScoreScreen extends Screen {
    private final int score;
    
    public ScoreScreen(int score) {
        this.score = score;
    }
    
    @Override
    public void render() {
        drawBackground();
        
        spriteBatch.begin();
        
        text.draw(this, "Your score:", width / 2, height / 2 + 10, 0xffffffff, TextSheet.CENTER);
        text.draw(this, Integer.toString(score), width / 2, height / 2 - 10, 0xffffffff, TextSheet.CENTER);
        
        spriteBatch.end();
    }

    @Override
    public void tick() {
        
    }
}
