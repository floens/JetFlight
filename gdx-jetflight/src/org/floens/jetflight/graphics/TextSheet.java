package org.floens.jetflight.graphics;

import java.nio.ByteBuffer;
import java.util.HashMap;

import org.floens.jetflight.screen.Screen;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class TextSheet {
    public static final int LEFT = 0;
    public static final int CENTER = 1;
    public static final int RIGHT = 2;
    
    private static int[] charWidths = {
            4, 2, 5, 6, 6, 6, 6, 2, 4, 4,
            6, 6, 3, 6, 2, 6, 6, 6, 6, 6,
            6, 6, 6, 6, 6, 6, 2, 3, 6, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
            6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
            6, 6, 6, 6, 6, 6, 6, 5, 6, 6,
            6, 6, 6, 6, 3, 6, 6, 5, 6, 6,
            5, 5, 5, 2, 5, 5, 2, 6, 5, 6,
            5, 5, 5, 6, 4, 5, 6, 6, 6, 6,
            5, 5, 2, 5, 6
        };
    
    private final FileHandle image;
    private final HashMap<Integer, Texture> colored = new HashMap<Integer, Texture>(); 
    
    public TextSheet(FileHandle image) {
        this.image = image;
    }

    public void draw(Screen screen, String text, int x, int y, int color) {
        draw(screen, text, x, y, color, LEFT, true, false);
    }
    
    public void draw(Screen screen, String text, int x, int y, int color, int align) {
        draw(screen, text, x, y, color, align, true, false);
    }
    
    public void draw(Screen screen, String text, int x, int y, int color, int align, boolean shadow) {
        draw(screen, text, x, y, color, align, shadow, false);
    }
        
    public void draw(Screen screen, String text, int x, int y, int color, int align, boolean shadow, boolean outline) {
        if (outline) {
            drawText(screen, text, x + 1, y, 0x000000ff, align);
            drawText(screen, text, x - 1, y, 0x000000ff, align);
            drawText(screen, text, x, y + 1, 0x000000ff, align);
            drawText(screen, text, x, y - 1, 0x000000ff, align);
        } else if (shadow) {
            drawText(screen, text, x + 1, y - 1, 0x00000088, align);
        }
        
        drawText(screen, text, x, y, color, align);
    }
    
    private void drawText(Screen screen, String text, int x, int y, int color, int align) {
        int drawX = x;
        int drawY = y;

        if (align == CENTER || align == RIGHT) {
            int totalWidth = 0;

            for (int i = 0; i < text.length(); i++) {
                int code = text.charAt(i) - 32;
                if (code >= 0 && code <= 94) {
                    totalWidth += charWidths[code];
                } else {
                    totalWidth += 6;
                }
            }
            
            if (align == CENTER) {
                drawX -= totalWidth / 2;                
            } else {
                drawX -= totalWidth;
            }
        }
        
        Texture img = colored.get(color);
        if (img == null) {
            img = createTexture(color);
            colored.put(color, img);
        }

        for (int i = 0; i < text.length(); i++) {
            int code = text.charAt(i) - 32;
            
            if (code < 0 || code > 94) {
                code = 31; // ?
            }
            
            int tx = code % 10;
            int ty = code / 10;

            if (code != 0) {
                screen.draw(img, drawX, drawY, 6, 8, tx * 6, ty * 8, 6, 8);
            }

            drawX += charWidths[code];
        }
    }
    
    private Texture createTexture(int color) {
        Pixmap pixmap = new Pixmap(image);
        
        /*if (pixmap.getFormat() != Format.RGBA8888) {
            Pixmap tmp = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), Format.RGBA8888);
            tmp.drawPixmap(pixmap, 0, 0);
            pixmap.dispose();
            pixmap = tmp;
        }*/
        
        int colorR = (color >> 24) & 0xff;
        int colorG = (color >> 16) & 0xff;
        int colorB = (color >> 8) & 0xff;
        int colorA = (color) & 0xff;
        
        ByteBuffer buffer = pixmap.getPixels();
        for (int i = 0; i < pixmap.getWidth() * pixmap.getHeight() * 4; i += 4) {
            int r = buffer.get(i);
            int g = buffer.get(i + 1);
            int b = buffer.get(i + 2);
//            int a = buffer.get(i + 3);
            
            if (r == 0 && g == 0 && b == 0) {
                buffer.put(i, (byte) colorR);
                buffer.put(i + 1, (byte) colorG);
                buffer.put(i + 2, (byte) colorB);
                buffer.put(i + 3, (byte) colorA);
            } else {
                buffer.put(i, (byte) 0x00);
                buffer.put(i + 1, (byte) 0x00);
                buffer.put(i + 2, (byte) 0x00);
                buffer.put(i + 3, (byte) 0x00);    
            }
        }
        
        Texture texture = new Texture(pixmap);
        
        return texture;
    }
}





