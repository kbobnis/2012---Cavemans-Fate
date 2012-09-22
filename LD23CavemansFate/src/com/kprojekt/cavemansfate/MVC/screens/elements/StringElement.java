package com.kprojekt.cavemansfate.MVC.screens.elements;

import com.badlogic.gdx.Gdx;
import com.kprojekt.cavemansfate.CavemansFate;

/**
 * @author Philon
 */
public class StringElement implements ScreenElement
{
	private final String string;
	private static int buffer = -1;
	private int height;

	public StringElement( String string )
	{
		this.string = string;
	}

	@Override
	public void render( int y )
	{
		if (StringElement.buffer == -1)
		{
			StringElement.buffer = Gdx.graphics.getWidth() / 10;
			CavemansFate.font.setScale( CavemansFate.fontScale );
			this.height = (int)CavemansFate.font.getWrappedBounds( string, Gdx.graphics.getWidth() - buffer * 2 ).height;
			CavemansFate.font.setScale( 1 );			
		}
		CavemansFate.font.setScale( CavemansFate.fontScale );
		CavemansFate.font.drawWrapped( CavemansFate.spriteBatch, this.string, buffer, Gdx.graphics.getHeight() - y,
				Gdx.graphics.getWidth() - buffer * 2 );
		CavemansFate.font.setScale( 1 );
	}

	@Override
	public int getHeight()
	{
		return height;
	}

}
