package com.kprojekt.cavemansfate.MVC.screens.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.kprojekt.cavemansfate.CavemansFate;

/**
 * @author Philon
 */
public class ImageElement implements ScreenElement
{

	private final TextureRegion playerRegion;
	private int width = -1;
	private int height;
	private int middleX;

	public ImageElement( TextureRegion playerRegion )
	{
		this.playerRegion = playerRegion;
		this.width = (int)(20 * CavemansFate.tileScale);
		this.height = (int)(20 * CavemansFate.tileScale);
		this.middleX = (int)(Gdx.graphics.getWidth() / 2f - this.width / 2f);

	}

	@Override
	public void render( int y )
	{
		CavemansFate.spriteBatch.draw( this.playerRegion, this.middleX, Gdx.graphics.getHeight() - this.height - y,
				width, height );
	}

	@Override
	public int getHeight()
	{
		return this.height;
	}

}
