package org.floens.jetflight;

import org.floens.jetflight.graphics.Assets;
import org.floens.jetflight.graphics.TextSheet;
import org.floens.jetflight.screen.Screen;
import org.floens.jetflight.screen.TitleScreen;
import org.floens.jetflight.util.Prefs;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

public class JetFlight implements ApplicationListener {
	public static final boolean DEBUG_MODE = false;
	
	public ActionResolver actionResolver;
	public TextSheet text;
	
	private Screen screen;
    private float tickTodo;
    private int screenSwitch = 0;
    private boolean justResumed = false;
    private boolean playingMusic = false;
    
    public JetFlight(ActionResolver ar) {
    	actionResolver = ar;
	}
	
	@Override
	public void create() {
		Assets.load();
		
		text = new TextSheet(Assets.text);
		
		setScreen(new TitleScreen());
		
		playingMusic = Prefs.prefs.getBoolean("playingMusic", true);
		
		if (playingMusic) {
			Assets.gameLoop.play();
		}
	}
	
	public void setScreen(Screen newScreen) {
        if (screen != null) screen.removed();
        screen = newScreen;
        screen.init(this);
        screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screen.postInit();
        
        screenSwitch = 30;
    }
	
	@Override
	public void dispose() {
	}
	
	@Override
    public void render() {
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // justResumed and raw delta time because otherwise the game would play catch-up when it resumed from a long sleep.
        tickTodo += Gdx.graphics.getRawDeltaTime(); 
        if (justResumed) {
            tickTodo = 0f;
            justResumed = false;
        }
        
        while (tickTodo > 1.0f / 60.0f) {
            doTick();
            tickTodo -= 1.0f / 60.0f;
        }
        
        doRender();
        
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

	@Override
	public void resize(int width, int height) {
	    screen.resize(width, height);
	}

	@Override
	public void pause() {
	    screen.pause();
	}

	@Override
	public void resume() {
	    screen.resume();
	    justResumed = true;
	}
	
	private void doTick() {
		screenSwitch--;
		
		screen.tick();
	}
	
	private void doRender() {
		screen.render();
	}
}





