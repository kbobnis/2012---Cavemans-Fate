package com.kprojekt.cavemansfate.MVC.levelSelect.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.kprojekt.cavemansfate.MVC.MVCsManager;
import com.kprojekt.cavemansfate.MVC.levelSelect.MenuItemModel;

/**
 * @author PHilon 
 */
public class MenuItemView
{
	private MenuItemModel level;
	private int actualY;
	private final int menuItemHeight;

	public MenuItemView( MenuItemModel level2, int i, int menuItemHeight2, int startingOffset )
	{
		this.level = level2;
		this.menuItemHeight = menuItemHeight2;
		this.actualY = (menuItemHeight * i + startingOffset);
	}

	public void render( float delta )
	{
		MVCsManager.font.setColor( level.isUnlocked() ? Color.GREEN : Color.RED );

		MVCsManager.font.draw( MVCsManager.spriteBatch, level.getCaveManager().getModel().getName(), 10,
				Gdx.graphics.getHeight() - (this.actualY + this.menuItemHeight / 2) );
	}

	public boolean pointInside( int x, int y )
	{
		return y > actualY && y < actualY + this.menuItemHeight;
	}

	public void drag( int howY )
	{
		this.actualY += howY;
	}

	public int getActualY()
	{
		return this.actualY;
	}

	public int getHeight()
	{
		return this.menuItemHeight;
	}

	public MenuItemModel getModel()
	{
		return this.level;
	}

}
