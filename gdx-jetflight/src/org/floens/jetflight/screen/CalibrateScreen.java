package org.floens.jetflight.screen;

import org.floens.jetflight.entity.EntityPlayer;
import org.floens.jetflight.graphics.Button;
import org.floens.jetflight.graphics.TextSheet;
import org.floens.jetflight.util.Prefs;

import com.badlogic.gdx.Gdx;

public class CalibrateScreen extends Screen {
	private Button backButton;
	private Button calibrateButton;
	private EntityPlayer player;
	
    @Override
    public void postInit() {
        backButton = new Button(this, width / 2 - 30, height / 2 - 50, 60, 30) {
            @Override
            public void onPress() {
                jetFlight.setScreen(new SettingsScreen());
            }
        };
        backButton.setText("Back");
        
        calibrateButton = new Button(this, width / 2 - 30, height / 2 - 15, 60, 30) {
            @Override
            public void onPress() {
                Prefs.prefs.putFloat("calibratedX", Gdx.input.getAccelerometerX());
                Prefs.prefs.flush();
            }
        };
        calibrateButton.setText("Set as 0");
        
        player = new EntityPlayer(null);
        player.isStatic = true;
    }
    
	@Override
	public void render() {
		drawBackground();
        
        spriteBatch.begin();
        
        drawTitle("Calibrate sensor");
        
        player.setPosition(width / 2 - 8, height / 2 + 45);
        player.render(this, player.x, player.y);
        
        float rotation = player.getInputRotation();
        String s = String.valueOf(rotation);
        if (s.length() > 6) s = s.substring(0, 5);
        
        text.draw(this, s, width / 2, height / 2 + 25, 0xffffffff, TextSheet.CENTER, false, true);
        
        backButton.render();
        calibrateButton.render();
        
        spriteBatch.end();
	}

	@Override
	public void tick() {
		backButton.tick();
		calibrateButton.tick();
		player.tick();
	}
}
