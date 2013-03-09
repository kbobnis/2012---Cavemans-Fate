package com.kprojekt.cavemansfate.MVC.screens;

import com.badlogic.gdx.Gdx;
import com.kprojekt.cavemansfate.CavemansFate;
import com.kprojekt.cavemansfate.MVC.screens.elements.ScreenElement;
import com.kprojekt.cavemansfate.MVC.screens.elements.StringElement;
import com.kprojekt.cavemansfate.core.Core;

/**
 * @author Philon 
 */
public class TextScreenView
{
	private static int startingOffset;
	private static int bufor;
	private TextScreenModel model;
	private StringElement downElement = new StringElement( Core.lang.get( "textScreen.touchHereToContinue" ) );
	private int downElementPos = (int)(Gdx.graphics.getHeight() * 9f / 10f);

	public TextScreenView( TextScreenModel screenModel )
	{
		this.model = screenModel;
		startingOffset = Gdx.graphics.getWidth() / 20;
		bufor = Gdx.graphics.getWidth() / 50;

	}

	public void render( float delta )
	{
		CavemansFate.font.setScale( CavemansFate.fontScale );
		int y = TextScreenView.startingOffset;
		for( ScreenElement element : this.model.getElements() )
		{
			element.render( y );
			y += element.getHeight() + TextScreenView.bufor;
		}

		CavemansFate.font.setScale( 1f );
		float width = CavemansFate.font.getBounds( Core.lang.get( "textScreen.touchHereToContinue" ) ).width
				+ StringElement.buffer * 8;
		float scale = Gdx.graphics.getWidth() / width;

		this.downElement.setScale( scale );
		this.downElement.render( this.downElementPos );
		CavemansFate.font.setScale( 1 );
	}

	public boolean isDownElementTouched( int y )
	{
		return true; //y > downElementPos;
	}
}
