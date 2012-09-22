package com.kprojekt.cavemansfate.core;

import com.kprojekt.utils.Manager;
import com.kprojekt.utils.wrappers.InputWrapper;

/**
 * 
 */
public class MainInputProcessor extends InputWrapper
{
	private Manager actualManager;

	@Override
	public boolean touchDown( int x, int y )
	{
		this.actualManager.touchDown( x, y );
		return false;
	}

	@Override
	public final boolean touchUp( int x, int y )
	{
		this.actualManager.touchUp( x, y );
		return false;
	}

	@Override
	public final boolean dragged( int x, int y, int howX, int howY )
	{
		this.actualManager.dragged( x, y, howX, howY );
		return false;
	}

	public final void setDelegate( Manager actualManager )
	{
		this.actualManager = actualManager;
	}

	@Override
	public boolean backPressed()
	{
		this.actualManager.backPressed();
		return false;
	}


}
