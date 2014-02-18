package org.floens.jetflight.screen;

import org.floens.jetflight.graphics.Assets;
import org.floens.jetflight.graphics.Button;
import org.floens.jetflight.graphics.TextSheet;
import org.floens.jetflight.level.Level;
import org.floens.jetflight.util.Prefs;

public class GameScreen extends Screen {
    public Level level;
    
    private boolean showingScore = false;
    private int score = 0;
    private float scoreIntro = 0f;
    private Button retryButton;
    private Button backButton;
    
    @Override
    public void postInit() {
        retryButton = new Button(this, width / 2 + 5, height / 2 - 15, 40, 30) {
            @Override
            public void onPress() {
                jetFlight.setScreen(new GameScreen());
            }
        };
        retryButton.setText("Retry");
        
        backButton = new Button(this, width / 2 - 45, height / 2 - 50, 40, 30) {
            @Override
            public void onPress() {
                jetFlight.setScreen(new TitleScreen());
            }
        };
        backButton.setText("Home");
        
        level = new Level(this);
    }
    
    @Override
    public void render() {
        drawBackground(level.getPlayer().y);
        
        spriteBatch.begin();
        
        level.render();
        
        int score = level.getPlayer().getScore();
        text.draw(this, Integer.toString(score) + " meter", 2, height - 10, 0x2FEA25ff, TextSheet.LEFT, false, true);
        
        int health = level.getPlayer().getHealth();
        text.draw(this, Integer.toString(health) + " health", width - 2, height - 10, 0xFF1511ff, TextSheet.RIGHT, false, true);
        
        if (showingScore) {
            drawScore();
        }
        
        spriteBatch.end();
    }
    
    @Override
    public void tick() {
        level.tick();
        
        if (level.getPlayer().getOutOfScreen()) {
            showScore(level.getPlayer().getScore());
        }
        
        if (showingScore) {
            retryButton.tick();
            backButton.tick();
        }
    }
    
    private void showScore(int score) {
        if (showingScore) return;
        showingScore = true;
        this.score = score;
        
        jetFlight.actionResolver.submitScore(score);
    }

    private void drawScore() {
        scoreIntro += 0.01f;
        if (scoreIntro > 1f) scoreIntro = 1f;
        
        int offsetY = (int) (height / 2f + ((float)Math.pow(1f - scoreIntro, 5f) * height));
        
        draw(Assets.score, width / 2 - 45, offsetY - 20);
        text.draw(this, "Game over!", width / 2, offsetY + 50, 0xffffffff, TextSheet.CENTER, false, true);
        
        text.draw(this, "Score:", width / 2 - 35, offsetY + 25, 0x222222ff, TextSheet.LEFT, false);
        text.draw(this, Integer.toString(score) + " meter", width / 2 - 35, offsetY + 15, 0x222222ff, TextSheet.LEFT, false);
        
        int highScore = Prefs.prefs.getInteger("maxScore", 0);
        text.draw(this, "Highscore:", width / 2 - 35, offsetY, 0x222222ff, TextSheet.LEFT, false);
        text.draw(this, Integer.toString(highScore) + " meter", width / 2 - 35, offsetY - 10, 0x222222ff, TextSheet.LEFT, false);
        
        retryButton.y = offsetY - 55;
        retryButton.render();
        backButton.y = offsetY - 55;
        backButton.render();
    }
}





