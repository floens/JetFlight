package org.floens.jetflight.screen;

import org.floens.jetflight.JetFlight;
import org.floens.jetflight.graphics.Assets;
import org.floens.jetflight.graphics.TextSheet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;

public abstract class Screen {
    public JetFlight jetFlight;
    public SpriteBatch spriteBatch;
    public ShapeRenderer shapeRenderer;
    public int width;
    public int height;
    public TextSheet text;
    public Color backgroundColor = new Color();
    
    public final void init(JetFlight jetFlight) {
        this.jetFlight = jetFlight;
        text = jetFlight.text;
        
        spriteBatch = new SpriteBatch(100);
        shapeRenderer = new ShapeRenderer(100);
        
        Color.rgba8888ToColor(backgroundColor, 0x87D5FFff);
    }
    
    public final void resize(int x, int y) {
        int deviceWidth = Gdx.graphics.getWidth();
        int deviceHeight = Gdx.graphics.getHeight();
        
        Matrix4 projection = new Matrix4();
        
        // Our virtual screen size is 120 x Y
        // Y is variable, but takes in to account the correct aspect ratio
        width = 120;
        height = (int)(deviceHeight * (120d / deviceWidth));
        
        projection.setToOrtho(0, width, 0, height, -1, 1);
        
        spriteBatch.setProjectionMatrix(projection);
        shapeRenderer.setProjectionMatrix(projection);
    }
    
    public void postInit() {
    }
    
    public void removed() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
    }
    
    public void drawBackground() {
    	drawBackground(0f);
    }
    
    public void drawBackground(float y) {
    	if (y > 600f) {
    		float delta = 1f - Math.min((y - 600f) / 400f, 1f);
    		backgroundColor.set(135f * delta / 255f, 213f * delta / 255f, delta, 1f);
    	}
    	
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(backgroundColor);
        shapeRenderer.rect(0f, 0f, width, height);
        shapeRenderer.end();
        
        spriteBatch.begin();
        
        if (y > 600f) {
        	float delta = Math.min((y - 600f) / 400f, 1f);
        	for (int yy = 0; yy < height; yy += 120) {
	        	spriteBatch.setColor(1f, 1f, 1f, delta);
	    		spriteBatch.draw(Assets.stars, 0f, yy);
	    		spriteBatch.setColor(1f, 1f, 1f, 1f);
        	}
        }
        
        float offset = 0f;
        if (y > 0f) {
        	offset = y * 0.4f;
        }
        
        draw(Assets.cityBackground, 0f, -offset);
        
        spriteBatch.end();
    }
    
    public void drawTitle(String string) {
        text.draw(this, string, width / 2, height - 20, 0xffffffff, TextSheet.CENTER, false, true);
    }
    
    public void draw(Texture texture, float x, float y) {
    	if (x + texture.getWidth() < 0f || y + texture.getHeight() < 0f || x > this.width || y > this.height) return;
        spriteBatch.draw(texture, x, y, texture.getWidth(), texture.getHeight());
    }
    
    public void draw(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight) {
        draw(texture, x, y, width, height, srcX, srcY, srcWidth, srcHeight, false, false);
    }
    
    public void draw(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
    	if (x + width < 0f || y + height < 0f || x > this.width || y > this.height) return;
        spriteBatch.draw(texture, x, y, width, height, srcX, srcY, srcWidth, srcHeight, flipX, flipY);
    }
    
    public void draw(TextureRegion region, float x, float y) {
    	if (x + region.getRegionWidth() < 0f || y + region.getRegionHeight() < 0f || x > this.width || y > this.height) return;
        spriteBatch.draw(region, x, y);
    }
    
    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
    	if (x + width < 0f || y + height < 0f || x > this.width || y > this.height) return;
        spriteBatch.draw(region, x, y, originX, originY, width, height, scaleX, scaleY, rotation);
    }
    
    public abstract void render();
    public abstract void tick();
    public void pause() {};
    public void resume() {};
}
