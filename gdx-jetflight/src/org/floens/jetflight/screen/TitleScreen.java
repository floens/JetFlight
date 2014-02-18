package org.floens.jetflight.screen;

import org.floens.jetflight.entity.EntityPlayer;
import org.floens.jetflight.graphics.Button;

public class TitleScreen extends Screen {
    private Button playButton;
    private Button settingsButton;
    
    private EntityPlayer player;
    private float playerRotation;
    
    @Override
    public void postInit() {
        playButton = new Button(this, width / 2 - 30, height / 2 - 15, 60, 30) {
            @Override
            public void onPress() {
                jetFlight.setScreen(new GameScreen());
            }
        };
        playButton.setText("Play!");
        
        settingsButton = new Button(this, width / 2 - 30, height / 2 - 50, 60, 30) {
            @Override
            public void onPress() {
                jetFlight.setScreen(new SettingsScreen());
            }
        };
        settingsButton.setText("Settings");
        
        player = new EntityPlayer(null);
        player.isStatic = true;
    }
    
    @Override
    public void render() {
        drawBackground();
        
        spriteBatch.begin();
        
        playButton.render();
        settingsButton.render();
        
        player.setPosition(width / 2 - 8, height / 2 + 30, ((float) Math.sin(playerRotation)) * 0.2f, 0f);
        player.render(this, player.x, player.y);
        
        spriteBatch.end();
    }

    @Override
    public void tick() {
        playButton.tick();
        settingsButton.tick();
        playerRotation += 0.02f;
        player.tick();
    }
}
