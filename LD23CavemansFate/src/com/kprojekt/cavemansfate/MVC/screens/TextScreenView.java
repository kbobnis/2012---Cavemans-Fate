package com.kprojekt.cavemansfate.MVC.screens;

import com.badlogic.gdx.Gdx;
import com.kprojekt.cavemansfate.MVC.MVCsManager;
import com.kprojekt.cavemansfate.MVC.screens.elements.ScreenElement;
import com.kprojekt.cavemansfate.MVC.screens.elements.StringElement;
import com.kprojekt.cavemansfate.core.Core;

/**
 * @author Philon 
 */
public class TextScreenView
{
	private static int startingOffset = Gdx.graphics.getWidth() / 10;
	private static int bufor = Gdx.graphics.getWidth() / 50;
	private TextScreenModel model;
	private StringElement downElement;
	private int downElementPos = (int)(Gdx.graphics.getHeight() / 10f * 9f);

	public TextScreenView( TextScreenModel screenModel )
	{
		this.model = screenModel;
		this.downElement = new StringElement( Core.lang.get( "textScreen.touchHereToContinue" ) );
	}

	public void render( float delta )
	{
		MVCsManager.font.setScale( MVCsManager.fontScale );
		int y = TextScreenView.startingOffset;
		for( ScreenElement element : this.model.getElements() )
		{
			element.render( y );
			y += element.getHeight() + TextScreenView.bufor;
		}
		this.downElement.render( this.downElementPos );
		MVCsManager.font.setScale( 1 );
	}

	public boolean isDownElementTouched( int y )
	{
		return y > downElementPos;
	}
}
