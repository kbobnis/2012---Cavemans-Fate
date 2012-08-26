package com.kprojekt.cavemansfate.MVC.screens;

import java.util.List;

import com.kprojekt.cavemansfate.MVC.screens.elements.ScreenElement;

/**
 * @author Philon 
 */
public class TextScreenModel
{

	private List<ScreenElement> elements;
	private boolean dismissed;

	public TextScreenModel( List<ScreenElement> elements2 )
	{
		this.elements = elements2;
	}

	public TextScreenModel( ScreenElement[] before )
	{

	}

	public List<ScreenElement> getElements()
	{
		return this.elements;
	}

	public void dismiss( boolean b )
	{
		this.dismissed = b;
	}

	public boolean isDismissed()
	{
		return this.dismissed;
	}

}
