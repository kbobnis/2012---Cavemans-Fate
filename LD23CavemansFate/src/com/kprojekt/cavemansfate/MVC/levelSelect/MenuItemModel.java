package com.kprojekt.cavemansfate.MVC.levelSelect;

import com.kprojekt.cavemansfate.MVC.cave.CaveManager;

/**
 * @author Philon
 */
public class MenuItemModel
{

	private final CaveManager level;
	private boolean isUnlocked;

	public MenuItemModel( CaveManager level )
	{
		this.level = level;
		this.isUnlocked = false;
	}

	public void unlock()
	{
		this.isUnlocked = true;
	}

	public CaveManager getCaveManager()
	{
		return this.level;
	}

	public boolean isUnlocked()
	{
		return this.isUnlocked;
	}

	public String getLevelEntryFromFile()
	{
		return this.getCaveManager().getPath() + ".isUnlocked";
	}

}
