package org.floens.jetflight.util;

import org.floens.jetflight.JetFlight;

import com.badlogic.gdx.Gdx;

public class Logger {
    private static String TAG = "";
    
    public static void setTag(String tag) {
        TAG = tag;
    }
    
    public static void d(String message) {
    	if (JetFlight.DEBUG_MODE) {
    		Gdx.app.debug(TAG, message);
    	}
    }
    
    public static void i(String message) {
    	if (JetFlight.DEBUG_MODE) {
    		Gdx.app.log(TAG, message);
    	}
    }
    
    public static void e(String message) {
    	Gdx.app.error(TAG, message);
    }
}
