package org.floens.jetflight.screen;

import org.floens.jetflight.graphics.Assets;
import org.floens.jetflight.graphics.Button;
import org.floens.jetflight.util.Prefs;

public class SettingsScreen extends Screen {
	private Button musicButton;
	private Button calibrateButton;
    private Button backButton;
    
    @Override
    public void postInit() {
    	musicButton = new Button(this, width / 2 - 30, height / 2 + 20, 60, 30) {
            @Override
            public void onPress() {
                toggleMusic(false);
            }
        };
        
        toggleMusic(true);
        
    	calibrateButton = new Button(this, width / 2 - 30, height / 2 - 15, 60, 30) {
            @Override
            public void onPress() {
                jetFlight.setScreen(new CalibrateScreen());
            }
        };
        calibrateButton.setText("Calibrate");
    	
        backButton = new Button(this, width / 2 - 30, height / 2 - 50, 60, 30) {
            @Override
            public void onPress() {
                jetFlight.setScreen(new TitleScreen());
            }
        };
        backButton.setText("Back");
    }
    
    public void toggleMusic(boolean first) {
    	boolean musicEnabled = Prefs.prefs.getBoolean("playingMusic", true);
    	
    	if (!first) {
    		musicEnabled = !musicEnabled;
    	}
    	
    	musicButton.setText("Music " + (musicEnabled ? "on" : "off"));
    	
    	Prefs.prefs.putBoolean("playingMusic", musicEnabled);
    	Prefs.prefs.flush();
    	
    	if (!first) {
    		if (musicEnabled && !Assets.gameLoop.isPlaying()) {
        		Assets.gameLoop.play();
        	} else {
        		Assets.gameLoop.pause();
        	}
    	}
    }
    
    @Override
    public void render() {
        drawBackground();
        
        spriteBatch.begin();
        
        drawTitle("Settings");
        
        musicButton.render();
        calibrateButton.render();
        backButton.render();
        
        spriteBatch.end();
    }

    @Override
    public void tick() {
    	musicButton.tick();
    	calibrateButton.tick();
        backButton.tick();
    }
}
