package com.kprojekt.cavemansfate.gui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.kprojekt.cavemansfate.MVC.MVCsManager;
import com.kprojekt.utils.Manager;

/**
 * 
 */
public class Container extends Manager
{

	private List<Container> elements = new ArrayList<Container>();
	private int height;
	private int width;

	public Container( int height )
	{
		super();
		this.height = height;
		this.width = Gdx.graphics.getWidth();
	}

	@Override
	public void render( float delta, int x, int y )
	{
		MVCsManager.spriteBatch.begin();
		for( Container el : this.elements )
		{
			el.render( delta, this.getX( el ), this.getY( el ) );
		}
		MVCsManager.spriteBatch.end();
	}

	private int getHeight()
	{
		return this.height;
	}

	public void add( Container el )
	{
		this.elements.add( el );
	}

	@Override
	public void touchDown( int x, int y )
	{
		for( Container container : this.elements )
		{
			int relativeX = x - this.getX( container );
			int relativeY = y - this.getY( container );
			if( container.takeOver( relativeX, relativeY ) )
			{
				container.touchDown( relativeX, relativeY );
			}
		}
	}

	@Override
	public void touchUp( int x, int y )
	{
		for( Container container : this.elements )
		{
			int relativeX = x - this.getX( container );
			int relativeY = y - this.getY( container );
			if( container.takeOver( relativeX, relativeY ) )
			{
				container.touchUp( relativeX, relativeY );
			}
		}
	}

	@Override
	public void dragged( int x, int y, int howX, int howY )
	{
		for( Container container : this.elements )
		{
			int relativeX = x - this.getX( container );
			int relativeY = y - this.getY( container );
			if( container.takeOver( relativeX, relativeY ) )
			{
				container.dragged( relativeX, relativeY, howX, howY );
			}
		}
	}

	private int getY( Container container )
	{
		int y = 0;
		for( Container el : this.elements )
		{
			if( el == container )
				break;
			y += el.getHeight();
		}
		return y;
	}

	private int getX( Container container )
	{
		return 0;
	}

	private boolean takeOver( int x, int y )
	{
		return x > 0 && x < this.width && y > 0 && y < this.height;
	}

}
