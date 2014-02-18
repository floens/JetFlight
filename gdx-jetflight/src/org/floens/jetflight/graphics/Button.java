package org.floens.jetflight.graphics;

import org.floens.jetflight.screen.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.graphics.Texture;

public abstract class Button {
    public int x;
    public int y;
    public int width;
    public int height;
    
    private final Screen screen;
    private boolean wasDown = false;
    private boolean wasDownHere = false;
    private boolean hasText = false;
    private String text;
    
    public Button(Screen screen, int x, int y, int width, int height) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public void setText(String text) {
        hasText = true;
        this.text = text;
    }

    public void render() {
        Texture texture;
        if (width < 60) {
            texture = getDown() ? Assets.buttonPressed40 : Assets.button40;
        } else {
            texture = getDown() ? Assets.buttonPressed : Assets.button;
        }
        
        screen.draw(texture, x, y);
        
        if (hasText) {
            screen.text.draw(screen, text, x + width / 2, y + height / 2 - 4 - (getDown() ? 1 : 0), 0xffffffff, TextSheet.CENTER);
        }
    }

    public void tick() {
        boolean down = Gdx.input.isButtonPressed(Buttons.LEFT);
        
        if (!wasDown && down) {
            wasDown = true;
            if (isTouchInside()) wasDownHere = true;
        } else if (wasDown && !down) {
            if (wasDownHere && isTouchInside()) {
                onPress();
            }
            
            wasDown = false;
            wasDownHere = false;
        }
    }
    
    /**
     * @return true if this button is held down
     */
    public boolean getDown() {
        return wasDownHere && isTouchInside();
    }
    
    private boolean isTouchInside() {
        int tx = Gdx.input.getX();
        int ty = Gdx.input.getY();
        int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
        
        int ix = (int)(tx * ((float)screen.width / (float)w));
        int iy = (int)((h - ty) * ((float)screen.height / (float)h));
        
        return ix > x && ix < x + width && iy > y && iy < y + height;
    }
    
    /**
     * The button is pressed
     */
    public abstract void onPress();
}
