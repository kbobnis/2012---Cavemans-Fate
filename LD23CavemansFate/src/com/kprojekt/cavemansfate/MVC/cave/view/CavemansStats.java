package com.kprojekt.cavemansfate.MVC.cave.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.kprojekt.cavemansfate.CavemansFate;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanModel;
import com.kprojekt.cavemansfate.MVC.cave.model.Weapon;

/**
 * @author Philon
 */
public class CavemansStats
{
	private final CavemanModel caveman;
	private final AtlasRegion itemShed;
	private final float zoom;

	private String ITEM_SHED_NAME = "border";
	private final TextureAtlas mapAtlas;

	public CavemansStats( CavemanModel caveman, int tileW, int tileH, TextureAtlas spriteAtlas, float zoom,
			TextureAtlas mapAtlas )
	{
		this.caveman = caveman;
		this.mapAtlas = mapAtlas;
		this.itemShed = spriteAtlas.findRegion( this.ITEM_SHED_NAME );
		this.zoom = zoom;
	}

	public void render()
	{
		Color color = CavemansFate.spriteBatch.getColor();
		CavemansFate.spriteBatch.setColor( 1, 1, 1, 0.3f );
		//the last tile minus 5 pixels will be stats
		//y start
		int y = Gdx.graphics.getHeight() - 5 - (int)(itemShed.getRegionHeight() * zoom);
		int x = Gdx.graphics.getWidth() / 2 - (int)(itemShed.getRegionWidth() * zoom) / 2;
		CavemansFate.spriteBatch.draw( itemShed, x, y, (int)(itemShed.getRegionWidth() * zoom),
				(int)(itemShed.getRegionHeight() * zoom) );
		CavemansFate.spriteBatch.setColor( color );

		int tmpX = (int)(x + 5 * zoom);
		int tmpY = (int)(y + 5 * zoom);
		for( Weapon weapon : this.caveman.getWeapons() )
		{
			AtlasRegion imageRegion = this.mapAtlas.findRegion( weapon.getName() );
			if( imageRegion == null )
			{
				throw new RuntimeException( "There has to be a tile in the map called " + weapon.getName() );
			}

			CavemansFate.spriteBatch.draw( imageRegion, tmpX, tmpY, (int)(imageRegion.getRegionWidth() * zoom),
					(int)(imageRegion.getRegionHeight() * zoom) );
			tmpX += (imageRegion.getRegionWidth() + 1) * zoom;
		}

	}
}
