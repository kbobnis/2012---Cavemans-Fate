package com.kprojekt.cavemansfate.MVC.cave.actions;

import com.kprojekt.cavemansfate.MVC.cave.model.CaveModel;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanModel;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanState.SIDES;
import com.kprojekt.utils.fixes.MyTiledMap;

/**
 * @author Philon
 */
public abstract class CavemanAction
{
	public final boolean doAction( CaveModel cave, SIDES side )
	{
		return this.doAction( cave.getCaveman(), cave.getCaveman().getMap(), side );
	}

	public abstract boolean doAction( CavemanModel caveman, MyTiledMap map, SIDES side );

	public abstract String getName();
}
