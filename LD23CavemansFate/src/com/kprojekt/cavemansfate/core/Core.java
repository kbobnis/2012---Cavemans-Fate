package com.kprojekt.cavemansfate.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.kprojekt.utils.Manager;

/**
 * Singleton class for all singletons, easy to mock
 * @author Philon
 */
public class Core
{
	public static Lang lang;
	public static Levels levels;
	public static Manager actualManager;
	private static MainInputProcessor mainInputProcessor;

	public static void initLang( String xmlPath, String iso3Lang )
	{
		Core.lang = new Lang( xmlPath, iso3Lang );

	}

	public static void init( String xmlPath, String iso3Lang )
	{
		Core.levels = new Levels( xmlPath );
		Core.mainInputProcessor = new MainInputProcessor();
	}

	public static void render( float delta )
	{

		Core.mainInputProcessor.setDelegate( Core.actualManager );
		Gdx.input.setInputProcessor( Core.mainInputProcessor );

		Gdx.gl.glClearColor( 0.0f, 0f, 0f, 0 );
		Gdx.gl.glClear( GL10.GL_COLOR_BUFFER_BIT );

		Core.actualManager.render( delta, 0, 0 );
	}
}
