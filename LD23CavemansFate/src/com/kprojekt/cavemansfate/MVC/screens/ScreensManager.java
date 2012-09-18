package com.kprojekt.cavemansfate.MVC.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.kprojekt.cavemansfate.CavemansFate;
import com.kprojekt.cavemansfate.MVC.screens.elements.ScreenElement;
import com.kprojekt.utils.Manager;

/**
 * @author Philon 
 */
public class ScreensManager extends Manager
{
	private TextScreenModel model;
	private TextScreenView view;
	private TextScreenController controller;

	public ScreensManager( List<ScreenElement> elements )
	{
		init( elements );
	}

	private void init( List<ScreenElement> elements )
	{
		this.model = new TextScreenModel( elements );
		this.view = new TextScreenView( this.model );
		this.controller = new TextScreenController( this.model, this.view );
	}

	public ScreensManager( ScreenElement[] before )
	{
		List<ScreenElement> elements = new ArrayList<ScreenElement>();
		for( ScreenElement el : before )
		{
			elements.add( el );
		}
		init( elements );
	}

	public boolean isDismissed()
	{
		return this.model.isDismissed();
	}

	@Override
	public void render( float delta, int x, int y )
	{
		Gdx.input.setInputProcessor( this.controller );
		CavemansFate.spriteBatch.begin();
		this.view.render( delta );
		CavemansFate.spriteBatch.end();
	}

	public void dismiss( boolean b )
	{
		this.model.dismiss( b );
	}

	public InputProcessor getInputProcessor()
	{
		return this.controller;
	}

	@Override
	public void touchDown( int x, int y )
	{
		// TODO @Krzysiek Auto-generated method stub

	}

	@Override
	public void touchUp( int x, int y )
	{
		// TODO @Krzysiek Auto-generated method stub

	}

	@Override
	public void dragged( int x, int y, int howX, int howY )
	{
		// TODO @Krzysiek Auto-generated method stub

	}

}
