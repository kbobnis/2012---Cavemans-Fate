package com.kprojekt.utils.wrappers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * @author Philon
 */
public abstract class InputWrapper implements InputProcessor
{
	private int movedX;
	private int movedY;

	private int lastTouchX;
	private int lastTouchY;

	@Override
	public final boolean keyTyped( char character )
	{
		return false;
	}

	@Override
	public final boolean touchDown( int x, int y, int pointer, int button )
	{
		this.lastTouchX = x;
		this.lastTouchY = y;
		this.movedX = 0;
		this.movedY = 0;
		boolean res = this.touchDown( x, y );
		return res;
	}

	public abstract boolean touchDown( int x, int y );

	@Override
	public final boolean touchUp( int x, int y, int pointer, int button )
	{
		boolean res = this.touchUp( x, y );
		return res;
	}

	public abstract boolean touchUp( int x, int y );

	@Override
	public final boolean touchDragged( int x, int y, int pointer )
	{
		this.movedX += x - lastTouchX;
		this.movedY += y - lastTouchY;
		this.lastTouchX = x;
		this.lastTouchY = y;

		boolean res = this.dragged( this.lastTouchX, this.lastTouchY, this.movedX, this.movedY );
		return res;
	}

	public abstract boolean dragged( int x, int y, int howX, int howY );

	@Override
	public final boolean touchMoved( int x, int y )
	{
		return false;
	}

	@Override
	public final boolean scrolled( int amount )
	{
		return false;
	}

	protected void resetMoved()
	{
		this.movedX = 0;
		this.movedY = 0;
	}

	@Override
	public boolean keyDown( int keycode )
	{
		switch( keycode )
		{
			case Input.Keys.MENU:
				System.out.println( "Menu pressed" );
				Gdx.input.vibrate( 50 );
				break;
			case Input.Keys.BACK:
				System.out.println( "Back pressed" );
				Gdx.input.vibrate( 200 );
				this.backPressed();
				break;
			case Input.Keys.ESCAPE:
				this.backPressed();
				break;
		}
		return false;
	}

	public void backPressed()
	{
		System.out.println( "back pressed" );
	}

	@Override
	public boolean keyUp( int keycode )
	{
		// TODO @Krzysiek Auto-generated method stub
		return false;
	}

}
