package com.kprojekt.cavemansfate.MVC.screens.elements;

import com.badlogic.gdx.Gdx;
import com.kprojekt.cavemansfate.MVC.MVCsManager;

/**
 * @author Philon
 */
public class StringElement implements ScreenElement
{
	private final String string;
	private final static int buffer = Gdx.graphics.getWidth() / 10;
	private final int height;

	public StringElement( String string )
	{
		this.string = string;
		MVCsManager.font.setScale( MVCsManager.fontScale );
		this.height = (int)MVCsManager.font.getWrappedBounds( string, Gdx.graphics.getWidth() - buffer * 2 ).height;
		MVCsManager.font.setScale( 1 );
	}

	@Override
	public void render( int y )
	{
		MVCsManager.font.setScale( MVCsManager.fontScale );
		MVCsManager.font.drawWrapped( MVCsManager.spriteBatch, this.string, buffer, Gdx.graphics.getHeight() - y,
				Gdx.graphics.getWidth() - buffer * 2 );
		MVCsManager.font.setScale( 1 );
	}

	@Override
	public int getHeight()
	{
		return height;
	}

}
