package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication; 
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import Game.GameDemo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width=GameDemo.WIDTH;
		config.height=GameDemo.HEIGHT;
		config.title=GameDemo.TITLE;
		config.resizable=false;
		new LwjglApplication(new GameDemo(), config);
	}
}
