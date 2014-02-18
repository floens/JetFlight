package org.floens.jetflight;

import org.floens.jetflight.util.Logger;
import org.floens.jetflight.util.Prefs;

import android.os.Bundle;
import android.view.WindowManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class MainActivity extends AndroidApplication implements ActionResolver {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        
        initialize(new JetFlight(this), cfg);
        
        Gdx.app.getInput().setCatchBackKey(true);
    }

	@Override
	public void submitScore(int score) {
		Logger.i("Submitting score: " + score);
		
		int maxScore = Prefs.prefs.getInteger("maxScore", 0);
		if (score > maxScore) {
			Prefs.prefs.putInteger("maxScore", score);
			Prefs.prefs.flush();
		}
	}
}