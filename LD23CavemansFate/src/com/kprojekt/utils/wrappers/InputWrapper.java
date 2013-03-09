package com.kprojekt.utils.wrappers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 * @author Philon
 */
public abstract class InputWrapper implements InputProcessor
{
	protected int movedX;
	protected int movedY;

	protected int lastTouchX;
	protected int lastTouchY;

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
		System.out.println( "Touch draged: x: " + x + ", y: " + y );
		this.movedX += x - lastTouchX;
		this.movedY += y - lastTouchY;
		this.lastTouchX = x;
		this.lastTouchY = y;

		boolean res = this.dragged( x, y, this.movedX, this.movedY );
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
				//Gdx.input.vibrate( 50 );
				break;
			case Input.Keys.BACK:
			case Input.Keys.ESCAPE:
				//Gdx.input.vibrate( 200 );
				this.backPressed();
				break;
		}
		return false;
	}

	public boolean backPressed()
	{
		System.out.println( "back pressed" );
		return false;
	}

	@Override
	public boolean keyUp( int keycode )
	{
		// TODO @Krzysiek Auto-generated method stub
		return false;
	}

}
