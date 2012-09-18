package com.kprojekt.cavemansfate.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.kprojekt.cavemansfate.CavemansFate;

/**
 * 
 */
public class NewButton extends Container
{

	private static Color defaultColor = Color.WHITE;
	private final String name;
	private ButtonEvent buttonEvent;
	private Color color = NewButton.defaultColor;

	public NewButton( String string )
	{
		super( (int)CavemansFate.font.getXHeight() * 2 );
		name = string;
	}

	@Override
	public void render( float delta, int x, int y )
	{
		CavemansFate.font.setColor( this.color );
		CavemansFate.font.draw( CavemansFate.spriteBatch, this.name, x, Gdx.graphics.getHeight() - y );
	}

	public void addEvent( ButtonEvent buttonEvent )
	{
		this.buttonEvent = buttonEvent;
	}

	@Override
	public void touchDown( int x, int y )
	{
		this.color = Color.YELLOW;
	}

	@Override
	public void touchUp( int x, int y )
	{
		this.color = NewButton.defaultColor;
		if( this.buttonEvent != null )
		{
			this.buttonEvent.doAction();
		}
	}
}
