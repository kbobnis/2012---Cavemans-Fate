package com.kprojekt.cavemansfate;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.kprojekt.cavemansfate.MVC.cave.CaveManager;
import com.kprojekt.cavemansfate.MVC.levelSelect.LevelSelectManager;
import com.kprojekt.cavemansfate.core.Core;

public class SplashScreen implements Screen
{
	private final String loadingText;
	private final CavemansFate cavemansFate;
	private String iso3Lang;
	private boolean secondTime = false;

	public SplashScreen( String loadingText, CavemansFate cavemansFate, String iso3Lang )
	{
		this.loadingText = loadingText;
		this.cavemansFate = cavemansFate;
		this.iso3Lang = iso3Lang;

	}

	@Override
	public void render( float delta )
	{
		Gdx.gl.glClearColor( 0.0f, 0f, 0f, 0 );
		Gdx.gl.glClear( GL10.GL_COLOR_BUFFER_BIT );
		CavemansFate.spriteBatch.begin();
		CavemansFate.font.setColor( Color.WHITE );
		CavemansFate.font.setScale( Gdx.graphics.getWidth()
				/ (CavemansFate.font.getBounds( this.loadingText ).width + Gdx.graphics.getWidth() / 5f) );
		CavemansFate.font.drawWrapped( CavemansFate.spriteBatch, this.loadingText, Gdx.graphics.getWidth() / 20f,
				Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() );
		CavemansFate.spriteBatch.end();

		if( secondTime )
		{

			Core.initLang( "model.xml", this.iso3Lang );
			Core.init( "model.xml", this.iso3Lang );
			try
			{
				CaveManager[] all = Core.levels.getAll().toArray( new CaveManager[] {} );
				final LevelSelectManager levelSelectManager = new LevelSelectManager( all );
				for( CaveManager caveManager : all )
				{
					caveManager.addCaveManagerListener( levelSelectManager.getController() );
				}

				Core.levels.getResetLevelAction().addListener( levelSelectManager.getController() );
				Core.levels.addBackPressedListener( levelSelectManager.getController() );
				Core.actualManager = levelSelectManager;

			}
			catch( Exception e )
			{
				e.printStackTrace();
				Gdx.app.exit();
			}
			cavemansFate.setScreen( cavemansFate );
		}
		this.secondTime = true;

	}

	@Override
	public void resize( int width, int height )
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void show()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void hide()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void pause()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void resume()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub

	}

}
