package org.floens.jetflight;

import org.floens.jetflight.util.Logger;
import org.floens.jetflight.util.Prefs;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class JetFlightDesktop implements ActionResolver {
	public JetFlightDesktop() {
		final int scale = 3;
        
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "JetFlight";
        cfg.width = 120 * scale;
        cfg.height = 200 * scale;
        cfg.useGL20 = true;
        new LwjglApplication(new JetFlight(this), cfg);
        
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
	}

    @Override
	public void submitScore(int score) {
    	Logger.i("Submitting score: " + score);
    	
    	Prefs.prefs.putInteger("maxScore", score);
		Prefs.prefs.flush();
	}

	public static void main(String[] argv) {
		new JetFlightDesktop();
	}
}
