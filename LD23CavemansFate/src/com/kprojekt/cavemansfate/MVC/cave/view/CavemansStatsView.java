package com.kprojekt.cavemansfate.MVC.cave.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector3;
import com.kprojekt.cavemansfate.CavemansFate;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanModel;
import com.kprojekt.cavemansfate.MVC.cave.model.Weapon;

/**
 * @author Philon
 */
public class CavemansStatsView
{
	private final CavemanModel caveman;
	private final AtlasRegion itemShed;

	private String ITEM_SHED_NAME = "border";
	private final TextureAtlas mapAtlas;
	public Vector3 circleOffset = new Vector3( 0, 0, 0 );

	public CavemansStatsView( CavemanModel caveman, int tileW, int tileH, TextureAtlas spriteAtlas,
			TextureAtlas mapAtlas )
	{
		this.caveman = caveman;
		this.mapAtlas = mapAtlas;
		this.itemShed = spriteAtlas.findRegion( this.ITEM_SHED_NAME );
	}

	public void renderInventory()
	{
		Color color = CavemansFate.spriteBatch.getColor();
		CavemansFate.spriteBatch.setColor( 1, 1, 1, 0.3f );
		//the last tile minus 5 pixels will be stats
		//y start
		int y = Gdx.graphics.getHeight() - 5 - (int)(itemShed.getRegionHeight() * CavemansFate.tileScale);
		int x = Gdx.graphics.getWidth() / 2 - (int)(itemShed.getRegionWidth() * CavemansFate.tileScale) / 2;
		CavemansFate.spriteBatch.draw( itemShed, x, y, (int)(itemShed.getRegionWidth() * CavemansFate.tileScale),
				(int)(itemShed.getRegionHeight() * CavemansFate.tileScale) );
		CavemansFate.spriteBatch.setColor( color );

		int tmpX = (int)(x + 5 * CavemansFate.tileScale);
		int tmpY = (int)(y + 5 * CavemansFate.tileScale);
		for( Weapon weapon : this.caveman.getWeapons() )
		{
			AtlasRegion imageRegion = this.mapAtlas.findRegion( weapon.getName() );
			if( imageRegion == null )
			{
				throw new RuntimeException( "There has to be a tile in the map called " + weapon.getName() );
			}

			CavemansFate.spriteBatch.draw( imageRegion, tmpX, tmpY,
					(int)(imageRegion.getRegionWidth() * CavemansFate.tileScale),
					(int)(imageRegion.getRegionHeight() * CavemansFate.tileScale) );
			tmpX += (imageRegion.getRegionWidth() + 1) * CavemansFate.tileScale;
		}

	}
}
