package com.kprojekt.cavemansfate;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.backends.jogl.JoglApplicationConfiguration;

/**
 * @author Philon
 */
public class CavemansFateDesktop
{
	public static void main( String[] argv )
	{
		JoglApplicationConfiguration config = new JoglApplicationConfiguration();
		config.useGL20 = true;
		config.title = "Cavemans Fate";
		config.width = 480;
		config.height = 800;
		new JoglApplication( new CavemansFate("pol"), config );
	}
}
