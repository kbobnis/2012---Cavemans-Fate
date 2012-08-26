package com.kprojekt.cavemansfate.MVC.cave.actions;

import com.kprojekt.cavemansfate.MVC.cave.model.CavemanModel;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanState.SIDES;
import com.kprojekt.utils.fixes.MyTiledMap;

/**
 * 
 */
public class NothingToDigg extends CavemanAction
{

	@Override
	public boolean doAction( CavemanModel caveman, MyTiledMap map, SIDES side )
	{
		return false;
	}

	@Override
	public String getName()
	{
		return null;
	}

}
