package com.kprojekt.cavemansfate.MVC.levelSelect;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.kprojekt.cavemansfate.MVC.cave.CaveManager;

/**
 * @author PHilon 
 */
public class LevelSelectModel
{
	private final static String saveFileName = "cavemansFatesaveFile";
	private List<MenuItemModel> menuItemModels = new ArrayList<MenuItemModel>();
	private MenuItemModel activeLevel;

	public LevelSelectModel( CaveManager[] levels2 )
	{
		for( CaveManager level : levels2 )
		{
			this.menuItemModels.add( new MenuItemModel( level ) );

		}
		this.menuItemModels.get( 0 ).unlock();
		this.loadSaveGamesFromDisc();
	}

	public void unlockNextLevelAndGotoIt()
	{
		Iterator<MenuItemModel> itr = this.menuItemModels.iterator();
		while( itr.hasNext() )
		{
			MenuItemModel tmp = itr.next();
			if( tmp == this.activeLevel )
			{
				if( !itr.hasNext() )
				{
					this.activeLevel = null;
					return;
				}
				MenuItemModel next = itr.next();
				next.unlock();
				this.activeLevel = next;
				return;
			}
		}
	}

	public List<MenuItemModel> getMenuItemModels()
	{
		return this.menuItemModels;
	}

	public void setActiveLevel( MenuItemModel selected )
	{
		this.activeLevel = selected;
	}

	public MenuItemModel getActiveLevel()
	{
		return this.activeLevel;
	}

	public void saveStateToDisc()
	{
		Preferences prefs = Gdx.app.getPreferences( LevelSelectModel.saveFileName );

		for( int i = 0; i < this.menuItemModels.size() - 1; i++ )
		{
			MenuItemModel tmp = this.menuItemModels.get( i );
			MenuItemModel tmpNext = this.menuItemModels.get( i + 1 );
			if( tmp.isUnlocked() )
			{
				prefs.putBoolean( tmp.getLevelEntryFromFile(), true );
				//prefs.putBoolean( tmpNext.getLevelEntryFromFile(), true );
			}
		}

		prefs.flush();
	}

	private void loadSaveGamesFromDisc()
	{
		Preferences prefs = Gdx.app.getPreferences( LevelSelectModel.saveFileName );
		Iterator<MenuItemModel> itr = this.menuItemModels.iterator();
		while( itr.hasNext() )
		{
			MenuItemModel next = itr.next();

			String levelName = next.getLevelEntryFromFile();
			boolean isUnlocked = prefs.getBoolean( levelName );
			if( isUnlocked )
			{
				next.unlock();
			}
		}

	}

}
