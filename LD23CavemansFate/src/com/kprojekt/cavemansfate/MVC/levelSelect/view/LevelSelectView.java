package com.kprojekt.cavemansfate.MVC.levelSelect.view;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.kprojekt.cavemansfate.MVC.MVCsManager;
import com.kprojekt.cavemansfate.MVC.levelSelect.LevelSelectModel;
import com.kprojekt.cavemansfate.MVC.levelSelect.MenuItemModel;
import com.kprojekt.cavemansfate.core.Core;

/**
 * @author PHilon
 */
public class LevelSelectView
{
	private List<MenuItemView> menuItems = new ArrayList<MenuItemView>();
	private int startingOffset = 10;

	public LevelSelectView( LevelSelectModel levelSelectModel )
	{
		//prepare menuItems
		List<MenuItemModel> levels = levelSelectModel.getMenuItemModels();
		int i = 0;
		for( MenuItemModel level : levels )
		{
			this.menuItems.add( new MenuItemView( level, i++, Gdx.graphics.getHeight() / 8, this.startingOffset ) );
		}
	}

	public void render( float delta )
	{
		MVCsManager.spriteBatch.begin();
		MVCsManager.font.setScale( MVCsManager.fontScale );

		String rightText = Core.lang.get( "levelSelect.info" );

		int wrapWidth = Gdx.graphics.getWidth() / 3;
		MVCsManager.font.drawWrapped( MVCsManager.spriteBatch, rightText, Gdx.graphics.getWidth() - wrapWidth,
				Gdx.graphics.getHeight() - 10, wrapWidth );

		for( MenuItemView menuItem : this.menuItems )
		{
			menuItem.render( delta );
		}
		MVCsManager.font.setScale( 1 );
		MVCsManager.spriteBatch.end();
	}

	public MenuItemModel getMenuItem( int x, int y )
	{
		for( MenuItemView menuItem : this.menuItems )
		{
			if( menuItem.pointInside( x, y ) )
				return menuItem.getModel();
		}
		return null;
	}

	public void dragMenuItems( int howY )
	{
		//dont move if there is nothing left
		MenuItemView upperMenuItem = this.getUpperMenuItem();
		if( upperMenuItem.getActualY() + howY > startingOffset )
		{
			return;
		}
		MenuItemView loweMenuItem = this.getLowerMenuItem();
		if( loweMenuItem.getActualY() + howY + loweMenuItem.getHeight() < Gdx.graphics.getHeight() )
		{
			return;
		}

		for( MenuItemView menuItem : this.menuItems )
		{
			menuItem.drag( howY );
		}

	}

	private MenuItemView getLowerMenuItem()
	{
		int maxY = Integer.MIN_VALUE;
		MenuItemView maxMenuItem = null;
		for( MenuItemView menuItem : this.menuItems )
		{
			int tmpY = menuItem.getActualY();
			if( tmpY > maxY )
			{
				maxY = tmpY;
				maxMenuItem = menuItem;
			}
		}
		return maxMenuItem;
	}

	private MenuItemView getUpperMenuItem()
	{
		int minY = Integer.MAX_VALUE;
		MenuItemView minMenuItem = null;
		for( MenuItemView menuItem : this.menuItems )
		{
			int tmpY = menuItem.getActualY();
			if( tmpY < minY )
			{
				minY = tmpY;
				minMenuItem = menuItem;
			}
		}
		return minMenuItem;
	}
}
