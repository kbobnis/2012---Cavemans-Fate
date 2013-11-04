package com.kprojekt.cavemansfate.MVC.cave.actions;

import com.kprojekt.cavemansfate.MVC.cave.model.CavemanModel;
import com.kprojekt.cavemansfate.MVC.cave.model.CavemanState.SIDES;
import com.kprojekt.utils.fixes.MyTiledMap;

/**
 * @author Krzysiek Bobnis
 * @since 14:46:48 3 lis 2013
 */
public class MoveAction extends CavemanAction
{
	public static String name = "move";

	@Override
	public boolean doAction( CavemanModel caveman, MyTiledMap map, SIDES side )
	{
		caveman.move( side );
		if( caveman.canSwim( SIDES.CENTER ) && caveman.hasTilePickedUp() )
		{
			caveman.putTile( SIDES.negate( side ) );
		}
		return true;
	}

	@Override
	public String getName()
	{
		return MoveAction.name;
	}

}
