package com.kprojekt.cavemansfate;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.kprojekt.cavemansfate.MVC.MVCsManager;
import com.kprojekt.cavemansfate.core.Core;

/**
 * @author Philon 
 */
public class CavemansFate extends Game implements Screen
{
	private MVCsManager mvcsManager;

	@Override
	public void create()
	{
		System.out.println( "Create" );
		this.mvcsManager = new MVCsManager();
		setScreen( this );
	}

	@Override
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
