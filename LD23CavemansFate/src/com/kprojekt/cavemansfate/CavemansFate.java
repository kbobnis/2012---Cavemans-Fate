package com.kprojekt.cavemansfate;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kprojekt.cavemansfate.MVC.cave.CaveManager;
import com.kprojekt.cavemansfate.MVC.levelSelect.LevelSelectManager;
import com.kprojekt.cavemansfate.core.Core;
import com.kprojekt.utils.fixes.MyFont;

/**
 * @author Philon
 */
public class CavemansFate extends Game implements Screen
{
	public static SpriteBatch spriteBatch;

	public static MyFont font;

	public static float fontScale;

	private static float tilesPerWidth = 6f;
	private static float lettersPerWidth = 25f;
	private static float tileWidth = 20f;

	public static float tileScale;

	private final String iso3Lang;

	public CavemansFate( String iso3Lang )
	{
		this.iso3Lang = iso3Lang;
	}

	@Override
	public void create()
	{
		tileScale = Gdx.graphics.getWidth() / tileWidth / tilesPerWidth;

		System.out.println( "Create" );
		CavemansFate.font = new MyFont( Gdx.files.internal( "arial_polish.fnt" ), false );

		String testText = "";
		for( int i = 0; i < CavemansFate.lettersPerWidth; i++ )
		{
			testText += "a";
		}
		float width = CavemansFate.font.getBounds( testText ).width;
		CavemansFate.fontScale = Gdx.graphics.getWidth() / width;

		CavemansFate.spriteBatch = new SpriteBatch();

		// shiiiiit i spent 2 hours looking why this shit is breaking the whole
		// application! it was because i executed before font scale was set!

		String loadingText = "Loading caveman";
		if( this.iso3Lang.equals( "pol" ) )
			loadingText = "Wczytywanie gry";

		setScreen( new SplashScreen( loadingText, this, iso3Lang ) );

	}

	public void render( float delta )
	{
		Core.render( delta );
	}

	@Override
	public void show()
	{
		System.out.println( "Show" );
	}

	@Override
	public void hide()
	{
		System.out.println( "Hide" );
	}

	@Override
	public void resize( int width, int height )
	{
		System.out.println( "Resize" );
	}

	@Override
	public void pause()
	{
		System.out.println( "Pause" );
		this.dispose();
	}

	@Override
	public void resume()
	{
		System.out.println( "Resume" );
	}

}
