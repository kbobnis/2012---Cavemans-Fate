package com.kprojekt.cavemansfate.MVC.cave.menu;

import java.util.ArrayList;
import java.util.List;

import com.kprojekt.utils.wrappers.InputWrapper;

/**
 * @author Philon 
 */
public class Menu extends InputWrapper
{

	private List<Button> buttons = new ArrayList<Button>();

	public void render()
	{
		for( Button button : this.buttons )
		{
			button.render();
		}
	}

	public void addButton( Button button )
	{
		this.buttons.add( button );
	}

	@Override
	public boolean touchDown( int x, int y )
	{
		return false;
	}

	@Override
	public boolean touchUp( int x, int y )
	{
		for( Button button : this.buttons )
		{
			if( button.touchUp( x, y ) )
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean dragged( int howX, int howY )
	{
		return false;
	}
}
