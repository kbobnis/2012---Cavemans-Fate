package com.kprojekt.cavemansfate.MVC.cave.controller;

import java.util.ArrayList;
import java.util.List;

import com.kprojekt.utils.wrappers.InputWrapper;

/**
 * @author Philon 
 */
public class MainInputHandler extends InputWrapper
{
	private List<InputWrapper> controllers = new ArrayList<InputWrapper>();

	@Override
	public boolean touchDown( int x, int y )
	{
		for( int i = controllers.size() - 1; i >= 0; i-- )
		{
			InputWrapper tmp = controllers.get( i );
			if( tmp.touchDown( x, y ) )
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean touchUp( int x, int y )
	{
		for( int i = controllers.size() - 1; i >= 0; i-- )
		{
			InputWrapper tmp = controllers.get( i );
			if( tmp.touchUp( x, y ) )
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean dragged( int howX, int howY )
	{
		for( int i = controllers.size() - 1; i >= 0; i-- )
		{
			InputWrapper tmp = controllers.get( i );
			if( tmp.dragged( howX, howY ) )
			{
				return true;
			}
		}
		return false;
	}

	public void addInputHandler( InputWrapper caveController )
	{
		this.controllers.add( caveController );
	}

}
