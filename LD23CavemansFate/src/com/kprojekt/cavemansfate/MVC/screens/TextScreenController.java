package com.kprojekt.cavemansfate.MVC.screens;

import com.kprojekt.utils.wrappers.InputWrapper;

/**
 * @author Philon 
 */
public class TextScreenController extends InputWrapper
{
	private TextScreenModel model;
	private final TextScreenView view;

	public TextScreenController( TextScreenModel model, TextScreenView view )
	{
		this.model = model;
		this.view = view;
	}

	@Override
	public boolean touchDown( int x, int y )
	{
		if( view.isDownElementTouched( y ) )
		{
			this.model.dismiss( true );
			return true;
		}
		return true;
	}

	@Override
	public boolean touchUp( int x, int y )
	{
		// TODO @Krzysiek Auto-generated method stub
		return true;
	}

	@Override
	public boolean dragged( int x, int y, int howX, int howY )
	{
		// TODO @Krzysiek Auto-generated method stub
		return true;
	}

	@Override
	public boolean backPressed()
	{
		return false;
		// TODO Auto-generated method stub
		
	}

}
